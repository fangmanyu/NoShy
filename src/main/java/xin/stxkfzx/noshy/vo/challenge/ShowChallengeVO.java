package xin.stxkfzx.noshy.vo.challenge;

/**
 * 展示挑战参数封装
 *
 * @author fmy
 * @date 2018-09-20 10:47
 */
public class ShowChallengeVO {

    private Integer challengeId;
    private String challengeName;
    private String challengeImage;

    @Override
    public String toString() {
        return "ShowChallengeVO{" +
                "challengeId=" + challengeId +
                ", challengeName='" + challengeName + '\'' +
                ", challengeImage='" + challengeImage + '\'' +
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

    public String getChallengeImage() {
        return challengeImage;
    }

    public void setChallengeImage(String challengeImage) {
        this.challengeImage = challengeImage;
    }
}
