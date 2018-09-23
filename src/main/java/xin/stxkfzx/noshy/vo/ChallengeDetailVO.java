package xin.stxkfzx.noshy.vo;

import xin.stxkfzx.noshy.vo.challenge.RankDetailVO;

import java.util.Date;
import java.util.List;

/**
 * @author fmy
 * @date 2018-09-18 21:18
 */
public class ChallengeDetailVO {
    private Integer challengeId;
    private List<RankDetailVO> rankList;
    private String challengeName;
    private String challengeDescription;
    private Date createTime;

    @Override
    public String toString() {
        return "ChallengeDetailVO{" +
                "challengeId=" + challengeId +
                ", rankList=" + rankList +
                ", challengeName='" + challengeName + '\'' +
                ", challengeDescription='" + challengeDescription + '\'' +
                ", createTime=" + createTime +
                '}';
    }

    public Integer getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

    public List<RankDetailVO> getRankList() {
        return rankList;
    }

    public void setRankList(List<RankDetailVO> rankList) {
        this.rankList = rankList;
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }

    public String getChallengeDescription() {
        return challengeDescription;
    }

    public void setChallengeDescription(String challengeDescription) {
        this.challengeDescription = challengeDescription;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}
