package xin.stxkfzx.noshy.domain;

import java.io.Serializable;
import java.util.Date;

public class Challenge implements Serializable {
    public static final int CHALLENGEING = 0;
    public static final int CHALLENGE_FINISH = 1;
    public static final int CREATE_CHALLENGE = -1;
    private static final long serialVersionUID = 8035792875831119214L;

    private Integer challengeId;

    private String challengeName;

    private String challengeDescription;

    private Integer status;

    private Date createTime;

    private Integer browseId;

    private Integer userId;

    private String imageUrl;

    @Override
    public String toString() {
        return "Challenge{" +
                "challengeId=" + challengeId +
                ", challengeName='" + challengeName + '\'' +
                ", challengeDescription='" + challengeDescription + '\'' +
                ", status=" + status +
                ", createTime=" + createTime +
                ", browseId=" + browseId +
                ", userId=" + userId +
                ", imageUrl='" + imageUrl + '\'' +
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Integer getBrowseId() {
        return browseId;
    }

    public void setBrowseId(Integer browseId) {
        this.browseId = browseId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

}