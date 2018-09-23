package xin.stxkfzx.noshy.dto;

import xin.stxkfzx.noshy.domain.Challenge;
import xin.stxkfzx.noshy.domain.Rank;

import java.util.List;

/**
 * @author fmy
 * @date 2018-09-17 19:05
 */
public class ChallengeDTO {
    private Boolean success;
    private String message;
    private Challenge challenge;
    private List<Rank> rankList;
    private Integer count;
    private List<Challenge> challengeList;

    public ChallengeDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    @Override
    public String toString() {
        return "ChallengeDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", challenge=" + challenge +
                ", rankList=" + rankList +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Challenge getChallenge() {
        return challenge;
    }

    public void setChallenge(Challenge challenge) {
        this.challenge = challenge;
    }

    public void setRankList(List<Rank> rankList) {
        this.rankList = rankList;
    }

    public List<Rank> getRankList() {
        return rankList;
    }

    public void setCount(Integer count) {
        this.count = count;
    }

    public Integer getCount() {
        return count;
    }

    public void setChallengeList(List<Challenge> challengeList) {
        this.challengeList = challengeList;
    }

    public List<Challenge> getChallengeList() {
        return challengeList;
    }
}
