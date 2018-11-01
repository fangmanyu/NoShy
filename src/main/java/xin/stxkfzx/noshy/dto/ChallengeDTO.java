package xin.stxkfzx.noshy.dto;

import xin.stxkfzx.noshy.domain.Challenge;
import xin.stxkfzx.noshy.domain.Rank;

import java.util.List;

/**
 * @author fmy
 * @date 2018-09-17 19:05
 */
public class ChallengeDTO extends BaseDTO{

    private Challenge challenge;
    private List<Rank> rankList;
    private Integer count;
    private List<Challenge> challengeList;

    public ChallengeDTO(Boolean success, String message) {
        super(success, message);
    }

    @Override
    public String toString() {
        return "ChallengeDTO{" +
                "challenge=" + challenge +
                ", rankList=" + rankList +
                ", count=" + count +
                ", challengeList=" + challengeList +
                "} " + super.toString();
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
