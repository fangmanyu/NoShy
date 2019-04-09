package xin.stxkfzx.noshy.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import xin.stxkfzx.noshy.domain.BrowseInformation;
import xin.stxkfzx.noshy.domain.Challenge;
import xin.stxkfzx.noshy.domain.Post;
import xin.stxkfzx.noshy.domain.Video;
import xin.stxkfzx.noshy.dto.BrowseDTO;
import xin.stxkfzx.noshy.exception.BrowseException;
import xin.stxkfzx.noshy.mapper.BrowseInformationMapper;
import xin.stxkfzx.noshy.mapper.ChallengeMapper;
import xin.stxkfzx.noshy.mapper.PostMapper;
import xin.stxkfzx.noshy.mapper.VideoMapper;
import xin.stxkfzx.noshy.service.BrowseService;

import java.util.Optional;

/**
 * @author fmy
 * @date 2018-11-01 14:29
 */
@Service
public class BrowseServiceImpl implements BrowseService {
    private static final Logger log = LogManager.getLogger(BrowseServiceImpl.class);
    private final PostMapper postMapper;
    private final VideoMapper videoMapper;
    private final ChallengeMapper challengeMapper;
    private final BrowseInformationMapper browseInformationMapper;


    @Transactional(isolation = Isolation.READ_COMMITTED, rollbackFor = BrowseException.class)
    @Override
    public BrowseDTO updateBrowseInfo(int browseType, String belongId, String typeName) throws BrowseException {
        Integer browseId = getBrowseIdByBrowseTypeAndBelongId(browseType, belongId);

        BrowseInformation record = getUpdateInfo(typeName, browseId);
        try {
            browseInformationMapper.updateByPrimaryKeySelective(record);
        } catch (Exception e) {
            throw new BrowseException(e);
        }

        return new BrowseDTO(true, "操作成功", record);
    }

    private BrowseInformation getUpdateInfo(String typeName, Integer browseId) {
        BrowseInformation record = browseInformationMapper.selectByPrimaryKey(browseId);
        log.debug("更新类型: {}", typeName);
        switch (typeName) {
            case BrowseInformation.PAGEVIEWS:
                record.setPageviews(record.getPageviews() + 1);
                break;
            case BrowseInformation.LIKES:
                record.setLikes(record.getLikes() + 1);
                break;
            case BrowseInformation.SHARES:
                record.setShares(record.getShares() + 1);
                break;
            default:
                throw new IllegalArgumentException("typeName: " + typeName + ", 类型错误");
        }
        return record;
    }

    private Integer getBrowseIdByBrowseTypeAndBelongId(int browseType, String belongId) throws BrowseException {
        log.debug("选择类型: {}, id: {}", browseType, belongId);
        switch (browseType) {
            case BrowseInformation.POST:
                Post post = postMapper.selectByPrimaryKey(Integer.valueOf(belongId));
                return Optional.ofNullable(post).map(Post::getBrowseId).orElseThrow(() -> new BrowseException("没有找到Id为:" + belongId + " 的Post"));
            case BrowseInformation.VIDEO:
                Video video = videoMapper.selectByPrimaryKey(belongId);
                return Optional.ofNullable(video).map(Video::getBrowseId).orElseThrow(() -> new BrowseException("没有找到Id为:" + belongId + " 的Video"));
            case BrowseInformation.CHALLENGE:
                Challenge challenge = challengeMapper.selectByPrimaryKey(Integer.valueOf(belongId));
                return Optional.ofNullable(challenge).map(Challenge::getBrowseId).orElseThrow(() -> new BrowseException("没有找到Id为:" + belongId + " 的Challenge"));
            default:
                throw new IllegalArgumentException("browseType: " + browseType + ", 类型错误");
        }
    }

    @Autowired
    public BrowseServiceImpl(PostMapper postMapper, VideoMapper videoMapper, ChallengeMapper challengeMapper, BrowseInformationMapper browseInformationMapper) {
        this.postMapper = postMapper;
        this.videoMapper = videoMapper;
        this.challengeMapper = challengeMapper;
        this.browseInformationMapper = browseInformationMapper;
    }
}
