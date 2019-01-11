package xin.stxkfzx.noshy.vo.challenge;

/**
 * 根据条件获取挑战 参数封装
 *
 * @author fmy
 * @date 2018-11-06 22:11
 */
public class ListChallengeQuery {
    private String challengeName;

    @Override
    public String toString() {
        return "ListChallengeQuery{" +
                "challengeName='" + challengeName + '\'' +
                '}';
    }

    public String getChallengeName() {
        return challengeName;
    }

    public void setChallengeName(String challengeName) {
        this.challengeName = challengeName;
    }
}
