package xin.stxkfzx.noshy.vo.video;

import xin.stxkfzx.noshy.vo.UserVO;
import xin.stxkfzx.noshy.vo.challenge.ChallengeVO;

import java.util.Date;
import java.util.List;

/**
 * 视频信息 Vo
 *
 * @author fmy
 * @date 2018-11-02 16:41
 */
public class VideoVO {
    private String videoId;
    private String title;
    private Long videoCategory;
    private String description;
    private String imageUrl;
    private Date createTime;
    private Date lastEditTime;

    private String playUrl;
    private List<ChallengeVO> joinChallengeList;
    private UserVO userInfo;

    @Override
    public String toString() {
        return "VideoVO{" +
                "videoId='" + videoId + '\'' +
                ", title='" + title + '\'' +
                ", videoCategory=" + videoCategory +
                ", description='" + description + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", playUrl='" + playUrl + '\'' +
                ", joinChallengeList=" + joinChallengeList +
                ", userInfo=" + userInfo +
                '}';
    }

    public UserVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserVO userInfo) {
        this.userInfo = userInfo;
    }

    public List<ChallengeVO> getJoinChallengeList() {
        return joinChallengeList;
    }

    public void setJoinChallengeList(List<ChallengeVO> joinChallengeList) {
        this.joinChallengeList = joinChallengeList;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Long getVideoCategory() {
        return videoCategory;
    }

    public void setVideoCategory(Long videoCategory) {
        this.videoCategory = videoCategory;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public void setImageUrl(String imageUrl) {
        this.imageUrl = imageUrl;
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

    public String getPlayUrl() {
        return playUrl;
    }

    public void setPlayUrl(String playUrl) {
        this.playUrl = playUrl;
    }

}
