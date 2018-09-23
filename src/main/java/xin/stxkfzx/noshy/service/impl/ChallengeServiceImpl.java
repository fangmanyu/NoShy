package xin.stxkfzx.noshy.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.stxkfzx.noshy.domain.*;
import xin.stxkfzx.noshy.dto.ChallengeDTO;
import xin.stxkfzx.noshy.exception.ChallengeServiceException;
import xin.stxkfzx.noshy.mapper.*;
import xin.stxkfzx.noshy.service.ChallengeService;
import xin.stxkfzx.noshy.util.ImageUtil;
import xin.stxkfzx.noshy.util.PageCalculator;
import xin.stxkfzx.noshy.util.PathUtil;
import xin.stxkfzx.noshy.vo.ImageHolder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author fmy
 * @date 2018-09-17 20:01
 */
@Service
public class ChallengeServiceImpl implements ChallengeService {
    private static final Logger log = LogManager.getLogger(ChallengeServiceImpl.class);

    private final ChallengeMapper challengeMapper;
    private final RankMapper rankMapper;
    private final ChallengeVideoRelationMapper relationMapper;
    private final BrowseInformationMapper browseInformationMapper;
    private final VideoMapper videoMapper;
    private final ImageMapper imageMapper;

    @Transactional(rollbackFor = ChallengeServiceException.class)
    @Override
    public ChallengeDTO addChallenge(Challenge challenge, String videoId, ImageHolder image) throws ChallengeServiceException {
        // 添加浏览信息
        BrowseInformation browseInformation = new BrowseInformation();
        browseInformation.setShares(0);
        browseInformation.setPageviews(0);
        browseInformation.setLikes(0);
        browseInformation.setBrowseType(BrowseInformation.CHALLENGE);
        try {
            browseInformationMapper.insert(browseInformation);
        } catch (Exception e) {
            log.error("添加浏览信息错误: " + e.getMessage());
            throw new ChallengeServiceException("添加浏览信息错误: " + e.getMessage());
        }
        log.debug("添加浏览信息: {}", browseInformation);

        // 添加挑战封面
        String imageAddr = Optional.ofNullable(image).map(imageHolder -> {
            String imageBasePath = PathUtil.getImageBasePath(challenge.getChallengeId().toString(), BrowseInformation.CHALLENGE);
            String addr = null;
            try {
                addr = ImageUtil.generateThumbnail(image, imageBasePath);
            } catch (Exception e) {
                throw new ChallengeServiceException("插入封面图片错误");
            }

            return addr;
        }).orElseGet(() -> {
            Video video = videoMapper.selectByPrimaryKey(videoId);
            return video.getImageUrl();
        });

        // 添加挑战
        challenge.setCreateTime(new Date());
        challenge.setStatus(Challenge.CHALLENGEING);
        challenge.setBrowseId(browseInformation.getBrowseId());
        challenge.setImageUrl(imageAddr);
        try {
            challengeMapper.insertSelective(challenge);
        } catch (Exception e) {
            log.error("添加挑战信息错误: " + e.getMessage());
            throw new ChallengeServiceException("添加挑战信息错误: " + e.getMessage());
        }
        log.debug("添加挑战信息: {}", challenge);

        // 添加挑战-视频关系
        ChallengeVideoRelation relation = new ChallengeVideoRelation();
        relation.setChallengeId(challenge.getChallengeId());
        relation.setStatus(Challenge.CHALLENGEING);
        relation.setVideoId(videoId);
        relation.setCreateTime(new Date());
        try {
            relationMapper.insert(relation);
        } catch (Exception e) {
            log.error("添加挑战-视频信息错误: " + e.getMessage());
            throw new ChallengeServiceException("添加挑战-视频信息错误:: " + e.getMessage());
        }
        log.debug("添加挑战-视频信息: {}", relation);

        // 添加排名
        Rank rank = new Rank();
        rank.setGrade(1);
        rank.setUserId(challenge.getUserId());
        rank.setChallengeId(challenge.getChallengeId());
        rank.setCreateTime(new Date());
        rank.setLastEditTime(new Date());
        rank.setType(Rank.CHALLENGE);
        rank.setStatus(Challenge.CHALLENGEING);
        rank.setLikes(0);
        try {
            rankMapper.insert(rank);
        } catch (Exception e) {
            log.error("添加排名信息错误: " + e.getMessage());
            throw new ChallengeServiceException("添加排名信息错误:: " + e.getMessage());
        }
        log.debug("添加排名信息: {}", rank);

        return new ChallengeDTO(true, "添加成功");
    }

    @Transactional(rollbackFor = ChallengeServiceException.class)
    @Override
    public ChallengeDTO addChallengeVideo(String videoId, Integer challengeId) throws ChallengeServiceException {
        Video video = videoMapper.selectByPrimaryKey(videoId);
        Challenge challenge = challengeMapper.selectByPrimaryKey(challengeId);
        if (video == null || challenge == null) {
            return new ChallengeDTO(false, "未找到该视频或挑战");
        }

        // 添加挑战-视频
        ChallengeVideoRelation relation = new ChallengeVideoRelation();
        relation.setVideoId(videoId);
        relation.setChallengeId(challengeId);
        relation.setCreateTime(new Date());
        relation.setStatus(Challenge.CHALLENGEING);
        try {
            relationMapper.insert(relation);
        } catch (Exception e) {
            throw new ChallengeServiceException("添加挑战视频失败:" + e.getMessage());
        }
        log.debug("添加挑战-视频信息: {}", relation);

        // 添加排名
        Rank rank = new Rank();
        rank.setGrade(rankMapper.countByChallengeId(challengeId) + 1);
        rank.setUserId(video.getUserId().intValue());
        rank.setChallengeId(challenge.getChallengeId());
        rank.setCreateTime(new Date());
        rank.setLastEditTime(new Date());
        rank.setType(Rank.CHALLENGE);
        rank.setStatus(Challenge.CHALLENGEING);
        rank.setLikes(0);
        try {
            rankMapper.insert(rank);
        } catch (Exception e) {
            log.error("添加排名信息错误: " + e.getMessage());
            throw new ChallengeServiceException("添加排名信息错误:: " + e.getMessage());
        }
        log.debug("添加排名信息: {}", rank);

        return new ChallengeDTO(true, "操作成功");

    }


    @Override
    public ChallengeDTO getChallengeByChallengeId(int challengeId) {
        // 获取具体的挑战信息,包括:挑战信息,排名信息
        Challenge challenge = challengeMapper.selectByPrimaryKey(challengeId);

        ChallengeDTO challengeDTO = Optional.ofNullable(challenge).map(item -> {
            List<Rank> rankList = rankMapper.findByChallengeIdAndType(item.getChallengeId(), Rank.CHALLENGE);
            ChallengeDTO dto = new ChallengeDTO(true, "查询成功");
            dto.setChallenge(item);
            dto.setRankList(rankList);
            return dto;
        }).orElse(new ChallengeDTO(false, "无该挑战"));

        return challengeDTO;
    }

    @Override
    public ChallengeDTO listUserRanking(int rankType, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public ChallengeDTO listInterestedChallenge(int pageIndex, int pageSize) {
        // FIXME: 2018/9/20 0020 临时返回所有的挑战列表,后续应该根据用户观看视频的分类次数决定用户喜欢观看的视频分类,按照分类查找挑战并推送给用户
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        Integer count = challengeMapper.count();
        List<Challenge> challengeList = challengeMapper.find(rowIndex, pageSize);
        ChallengeDTO dto = new ChallengeDTO(true, "查询成功");
        dto.setChallengeList(challengeList);
        dto.setCount(count);
        return dto;
    }

    @Override
    public ChallengeDTO listChallengeByCondition(Challenge challengeCondition, int pageIndex, int pageSize) {
        return null;
    }

    @Override
    public String getChallengeImage(int challengeId) {
        // 直接找挑战图片,有则返回,没有下一步
        // 查找创建挑战视频封面,有则返回,没有返回null
        Challenge challenge = challengeMapper.selectByPrimaryKey(challengeId);
        String image = Optional.ofNullable(challenge).map(item -> {
            String imageUrl = item.getImageUrl();
            String addr = Optional.ofNullable(imageUrl).orElseGet(() -> {
                String videoId = challengeMapper.findOwnerVideoId(item.getChallengeId(), item.getUserId());
                return Optional.ofNullable(videoId).map(id -> {
                    Video video = videoMapper.selectByPrimaryKey(id);
                    return video.getImageUrl();
                }).orElse(null);
            });
            return addr;

        }).orElse(null);

        return image;
    }

    @Override
    public ChallengeDTO updateChallengeRank(int challengeId) throws ChallengeServiceException {
        return null;
    }

    @Autowired
    public ChallengeServiceImpl(ChallengeMapper challengeMapper, RankMapper rankMapper, ChallengeVideoRelationMapper relationMapper, BrowseInformationMapper browseInformationMapper, VideoMapper videoMapper, ImageMapper imageMapper) {
        this.challengeMapper = challengeMapper;
        this.rankMapper = rankMapper;
        this.relationMapper = relationMapper;
        this.browseInformationMapper = browseInformationMapper;
        this.videoMapper = videoMapper;
        this.imageMapper = imageMapper;
    }
}
