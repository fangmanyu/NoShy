package xin.stxkfzx.noshy.vo;

/**
 * @author fmy
 * @date 2018-09-20 13:35
 */
public class LikeChallengeVO {
    private String imageUrl;
    private Integer challengeId;

    @Override
    public String toString() {
        return "LikeChallengeVO{" +
                "imageUrl='" + imageUrl + '\'' +
                ", challengeId=" + challengeId +
                '}';
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
    }

    public Integer getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }
}
