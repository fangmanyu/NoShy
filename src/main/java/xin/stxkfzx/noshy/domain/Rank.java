package xin.stxkfzx.noshy.domain;

import java.util.Date;

public class Rank {

    public static final int LIKE = 0;
    public static final int PAGEVIEW = 1;
    public static final int ATTENTION = 2;
    public static final int CHALLENGE = 3;

    private Integer rankId;



    private Integer type;

    private Integer status;

    private Integer likes;

    private Date createTime;

    private Date lastEditTime;

    private Integer userId;

    private Integer challengeId;
    private String videoId;

    @Override
    public String toString() {
        return "Rank{" +
                "rankId=" + rankId +
                ", type=" + type +
                ", status=" + status +
                ", likes=" + likes +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", userId=" + userId +
                ", challengeId=" + challengeId +
                ", videoId='" + videoId + '\'' +
                '}';
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getLastEditTime() {
        return lastEditTime;
    }

    public void setLastEditTime(Date lastEditTime) {
        this.lastEditTime = lastEditTime;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getChallengeId() {
        return challengeId;
    }

    public void setChallengeId(Integer challengeId) {
        this.challengeId = challengeId;
    }

}