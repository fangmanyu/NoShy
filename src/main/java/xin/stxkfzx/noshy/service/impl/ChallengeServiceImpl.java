package xin.stxkfzx.noshy.service.impl;

import org.apache.commons.lang3.BooleanUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
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
    private final UserChallengeVideoLikesMapper userChallengeVideoLikesMapper;

    @Transactional(rollbackFor = ChallengeServiceException.class)
    @Override
    public ChallengeDTO addChallenge(Challenge challenge, String videoId, ImageHolder image) throws ChallengeServiceException {
        Video video = videoMapper.selectByPrimaryKey(videoId);
        if (video == null) {
            return new ChallengeDTO(false, "video不能为空");
        }

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
            String imageBasePath = PathUtil.getImageBasePath(videoId, BrowseInformation.CHALLENGE);
            String addr = null;
            try {
                addr = ImageUtil.generateThumbnail(image, imageBasePath);
            } catch (Exception e) {
                throw new ChallengeServiceException("插入封面图片错误");
            }

            return addr;
        }).orElseGet(video::getImageUrl);

        // 添加挑战
        Optional.ofNullable(challenge.getChallengeName()).orElseGet(() -> {
            String title = video.getTitle();
            challenge.setChallengeName(title);
            return title;
        });
        challenge.setCreateTime(new Date());
        challenge.setStatus(statusTransposition(video.getStatus()));
        challenge.setBrowseId(browseInformation.getBrowseId());
        challenge.setImageUrl(imageAddr);
        challenge.setUserId(video.getUserId().intValue());
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
        relation.setStatus(statusTransposition(videoId));
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
        rank.setUserId(challenge.getUserId());
        rank.setChallengeId(challenge.getChallengeId());
        rank.setCreateTime(new Date());
        rank.setLastEditTime(new Date());
        rank.setType(Rank.CHALLENGE);
        rank.setStatus(statusTransposition(videoId));
        rank.setLikes(0);
        rank.setUserId(video.getUserId().intValue());
        rank.setVideoId(video.getVideoId());
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
        relation.setStatus(statusTransposition(video.getStatus()));
        try {
            relationMapper.insert(relation);
        } catch (Exception e) {
            throw new ChallengeServiceException("添加挑战视频失败:" + e.getMessage());
        }
        log.debug("添加挑战-视频信息: {}", relation);

        // 添加排名
        Rank rank = new Rank();
        rank.setUserId(video.getUserId().intValue());
        rank.setChallengeId(challenge.getChallengeId());
        rank.setCreateTime(new Date());
        rank.setLastEditTime(new Date());
        rank.setType(Rank.CHALLENGE);
        rank.setStatus(Challenge.CHALLENGEING);
        rank.setLikes(0);
        rank.setVideoId(videoId);
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
        Challenge challenge = challengeMapper.findOneByChallengeIdAndStatusGreaterThanOrEqualTo(challengeId, Challenge.CHALLENGEING);
        // 根据点赞量进行排名
        ChallengeDTO challengeDTO = Optional.ofNullable(challenge).map(item -> {
            List<Rank> rankList = rankMapper.findByChallengeIdAndTypeAndStatusOrderByLikesDesc(item.getChallengeId(), Rank.CHALLENGE, Challenge.CHALLENGEING);
            ChallengeDTO dto = new ChallengeDTO(true, "查询成功");
            dto.setRankList(rankList);
            dto.setChallenge(challenge);
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
        Integer count = challengeMapper.countByStatusGreaterThanOrEqualTo(Challenge.CHALLENGEING);
        List<Challenge> challengeList = challengeMapper.findByStatusGreaterThanOrEqualTo(Challenge.CHALLENGEING, rowIndex, pageSize);
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

        return Optional.ofNullable(challenge).map(item -> {
            String imageUrl = item.getImageUrl();
            return Optional.ofNullable(imageUrl).orElseGet(() -> {
                String videoId = challengeMapper.findOwnerVideoId(item.getChallengeId(), item.getUserId());
                return Optional.ofNullable(videoId).map(id -> {
                    Video video = videoMapper.selectByPrimaryKey(id);
                    return video.getImageUrl();
                }).orElse(null);
            });

        }).orElse(null);
    }

    @Transactional(rollbackFor = ChallengeServiceException.class)
    @Override
    public ChallengeDTO updateChallengeStatus(String videoId) throws ChallengeServiceException {
        Video video = videoMapper.selectByPrimaryKey(videoId);

        return Optional.ofNullable(video).map(v -> {
            Integer userId = v.getUserId().intValue();
            String status = v.getStatus();
            int updatedStatus = statusTransposition(status);

            List<Integer> challengeIdList = relationMapper.findChallengeIdByVideoId(videoId);
            if (challengeIdList != null && !challengeIdList.isEmpty()) {
                try {
                    relationMapper.updateStatusByVideoId(updatedStatus, videoId);
                } catch (Exception e) {
                    throw new ChallengeServiceException("更新挑战-视频关系状态错误: " + e.getLocalizedMessage());
                }

                challengeIdList.forEach(item -> {
                    try {
                        challengeMapper.updateStatusByChallengeIdAndUserId(updatedStatus, item, userId);
                    } catch (Exception e) {
                        throw new ChallengeServiceException("更新挑战状态错误: " + e.getLocalizedMessage());
                    }

                });
                try {
                    rankMapper.updateStatusByVideoId(updatedStatus, videoId);
                } catch (Exception e) {
                    throw new ChallengeServiceException("更新排名状态错误: " + e.getLocalizedMessage());
                }

                return new ChallengeDTO(true, "更新成功");

            } else {
                return new ChallengeDTO(false, "该视频没有加入挑战");
            }

        }).orElse(new ChallengeDTO(false, "视频不存在"));
    }

    @Override
    public ChallengeDTO listChallengeIdByVideo(String videoId) {
        List<Integer> challengeIdList = relationMapper.findChallengeIdByVideoId(videoId);

        ChallengeDTO dto = new ChallengeDTO(true, "查询成功");
        dto.setChallengeIdList(challengeIdList);
        return dto;
    }

    @Override
    public boolean isLiked(Integer rankId, Integer currentUserId) {
        UserChallengeVideoLikes userChallengeVideoLikes = userChallengeVideoLikesMapper.selectByPrimaryKey(currentUserId, rankId);
        return Optional.ofNullable(userChallengeVideoLikes).map(UserChallengeVideoLikes::getStatus).map(status -> status.equals(UserChallengeVideoLikes.LIKED)).orElse(false);
    }

    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = ChallengeServiceException.class)
    @Override
    public ChallengeDTO likeIt(Integer rankId, Integer currentUserId) throws ChallengeServiceException {
        // 创建或更新UserChallengeVideoLike记录, 更新挑战视频点赞量
        Rank rank = rankMapper.selectByPrimaryKey(rankId);
        Optional.ofNullable(userChallengeVideoLikesMapper.selectByPrimaryKey(currentUserId, rankId)).map(userChallengeVideoLikes -> {
            Integer status = userChallengeVideoLikes.getStatus();

            if (status == UserChallengeVideoLikes.LIKED) {
                rank.setLikes(rank.getLikes() - 1);
                userChallengeVideoLikes.setStatus(UserChallengeVideoLikes.NOT_LiKE);
            } else if (status == UserChallengeVideoLikes.NOT_LiKE) {
                rank.setLikes(rank.getLikes() + 1);
                userChallengeVideoLikes.setStatus(UserChallengeVideoLikes.LIKED);
            } else {
                log.error("status: {} 错误", status);
                throw new ChallengeServiceException("status 异常");
            }
            userChallengeVideoLikes.setLastEditTime(new Date());
            rank.setLastEditTime(new Date());

            try {
                rankMapper.updateByPrimaryKeySelective(rank);
                userChallengeVideoLikesMapper.updateByPrimaryKeySelective(userChallengeVideoLikes);
            } catch (Exception e) {
                throw new ChallengeServiceException(e);
            }
            return rank;
        }).orElseGet(() -> {
            UserChallengeVideoLikes userChallengeVideoLikes = new UserChallengeVideoLikes();
            userChallengeVideoLikes.setStatus(UserChallengeVideoLikes.LIKED);
            userChallengeVideoLikes.setCreateTime(new Date());
            userChallengeVideoLikes.setLastEditTime(new Date());
            userChallengeVideoLikes.setRankId(rankId);
            userChallengeVideoLikes.setUserId(currentUserId);

            rank.setLikes(rank.getLikes() + 1);
            rank.setLastEditTime(new Date());

            try {
                rankMapper.updateByPrimaryKeySelective(rank);
                userChallengeVideoLikesMapper.updateByPrimaryKeySelective(userChallengeVideoLikes);
            } catch (Exception e) {
                throw new ChallengeServiceException(e);
            }

            return rank;
        });

        return new ChallengeDTO(true, "操作成功");
    }

    @Autowired
    public ChallengeServiceImpl(ChallengeMapper challengeMapper, RankMapper rankMapper,
                                ChallengeVideoRelationMapper relationMapper, BrowseInformationMapper browseInformationMapper,
                                VideoMapper videoMapper, ImageMapper imageMapper, UserChallengeVideoLikesMapper userChallengeVideoLikesMapper) {
        this.challengeMapper = challengeMapper;
        this.rankMapper = rankMapper;
        this.relationMapper = relationMapper;
        this.browseInformationMapper = browseInformationMapper;
        this.videoMapper = videoMapper;
        this.imageMapper = imageMapper;
        this.userChallengeVideoLikesMapper = userChallengeVideoLikesMapper;
    }

    private int statusTransposition(String videoStatus) {
        int status = Challenge.CREATE_CHALLENGE;
        switch (videoStatus) {
            case Video.NORMAL:
                status = Challenge.CHALLENGEING;
                break;
            case Video.UPLOADING:
            case Video.TRANSCODING:
            case Video.TRANSCODEFAIL:
                status = Challenge.CREATE_CHALLENGE;
                break;
            default:
                break;
        }
        return status;
    }
}
