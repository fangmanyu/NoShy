package xin.stxkfzx.noshy.service;

import xin.stxkfzx.noshy.domain.Challenge;
import xin.stxkfzx.noshy.dto.ChallengeDTO;
import xin.stxkfzx.noshy.exception.ChallengeServiceException;
import xin.stxkfzx.noshy.vo.ImageHolder;

/**
 * 挑战服务
 *
 * @author fmy
 * @date 2018-09-17 18:50
 */
public interface ChallengeService {

    /**
     * 创建挑战
     *
     * @param challenge challengeName(可选), challengeDescription
     * @param videoId
     * @param image
     * @return
     * @author fmy
     * @date 2018-09-17 19:13
     */
    ChallengeDTO addChallenge(Challenge challenge, String videoId, ImageHolder image) throws ChallengeServiceException;

    /**
     * 添加挑战视频
     *
     * @param videoId
     * @param challengeId
     * @return
     * @author fmy
     * @date 2018-09-17 20:00
     */
    ChallengeDTO addChallengeVideo(String videoId, Integer challengeId) throws ChallengeServiceException;

    /**
     * 获取具体挑战
     *
     * @param challengeId
     * @return
     * @author fmy
     * @date 2018-09-17 19:14
     */
    ChallengeDTO getChallengeByChallengeId(int challengeId);

    /**
     * 分页获取不同类型用户排行列表
     *
     * @param rankType
     * @param pageIndex
     * @param pageSize
     * @return
     * @author fmy
     * @date 2018-09-17 19:21
     */
    ChallengeDTO listUserRanking(int rankType, int pageIndex, int pageSize);

    /**
     * 分页获取感兴趣的挑战
     *
     * @param pageIndex
     * @param pageSize
     * @return
     * @author fmy
     * @date 2018-09-17 19:24
     */
    ChallengeDTO listInterestedChallenge(int pageIndex, int pageSize);

    /**
     * 根据条件分页获取挑战列表
     *
     * @param challengeCondition 查询条件: 创建时间,状态
     * @param pageIndex
     * @param pageSize
     * @return
     * @author fmy
     * @date 2018-09-17 19:26
     */
    ChallengeDTO listChallengeByCondition(Challenge challengeCondition, int pageIndex, int pageSize);

    /**
     * 获取挑战封面
     *
     * @param challengeId
     * @return
     * @author fmy
     * @date 2018-09-20 10:52
     */
    String getChallengeImage(int challengeId);

    /**
     * 更新指定挑战排名
     *
     * @param challengeId
     * @return
     * @throws ChallengeServiceException
     * @author fmy
     * @date 2018-09-20 13:20
     */
    ChallengeDTO updateChallengeRank(int challengeId) throws ChallengeServiceException;

    /**
     * 根据视频的状态更新挑战状态
     *
     * @param videoId
     * @return
     * @author 59261
     * @date 2018-10-06 1:58
     */
    ChallengeDTO updateChallengeStatus(String videoId) throws ChallengeServiceException;
}
