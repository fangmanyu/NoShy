package xin.stxkfzx.noshy.vo.challenge;

/**
 * 挑战信息 VO
 *
 * @author fmy
 * @date 2018-11-01 22:28
 */
public class ChallengeVO {
    private Integer challengeId;
    private String challengeName;
    private Integer joinNum;

    @Override
    public String toString() {
        return "ChallengeVO{" +
                "challengeId=" + challengeId +
                ", challengeName='" + challengeName + '\'' +
                ", joinNum=" + joinNum +
                '}';
    }

    public Integer getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public Integer getJoinNum() {
        return joinNum;
    }

    public void setJoinNum(Integer joinNum) {
        this.joinNum = joinNum;
    }
}
