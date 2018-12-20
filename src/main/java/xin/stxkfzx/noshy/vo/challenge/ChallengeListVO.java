package xin.stxkfzx.noshy.vo.challenge;

import java.util.List;

/**
 * 挑战列表封装
 *
 * @author fmy
 * @date 2018-11-6 22:26:27
 */
public class ChallengeListVO {

    List<ChallengeVO> challengeVOList;
    Integer count;

    @Override
    public String toString() {
        return "ChallengeListVO{" +
                "challengeVOList=" + challengeVOList +
                ", count=" + count +
                '}';
    }

    public List<ChallengeVO> getChallengeVOList() {
        return challengeVOList;
    }

    public void setChallengeVOList(List<ChallengeVO> challengeVOList) {
        this.challengeVOList = challengeVOList;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
    }
}
