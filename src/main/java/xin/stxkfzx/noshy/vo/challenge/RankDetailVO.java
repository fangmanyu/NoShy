package xin.stxkfzx.noshy.vo.challenge;

import xin.stxkfzx.noshy.vo.UserVO;

/**
 * @author fmy
 * @date 2018-09-18 21:20
 */
public class RankDetailVO {

    private UserVO userInfo;
    private String headPortraitAddr;
    private Integer likes;
    private String videoId;
    private Integer rankId;

    /**当前用户是否已经点赞*/
    private Boolean hasLiked;

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
    }

    @Override
    public String toString() {
        return "RankDetailVO{" +
                "userInfo=" + userInfo +
                ", headPortraitAddr='" + headPortraitAddr + '\'' +
                ", likes=" + likes +
                ", videoId='" + videoId + '\'' +
                ", rankId=" + rankId +
                ", hasLiked=" + hasLiked +
                '}';
    }

    public Boolean getHasLiked() {
        return hasLiked;
    }

    public void setHasLiked(Boolean hasLiked) {
        this.hasLiked = hasLiked;
    }

    public String getVideoId() {
        return videoId;
    }

    public void setVideoId(String videoId) {
        this.videoId = videoId;
    }

    public UserVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserVO userInfo) {
        this.userInfo = userInfo;
    }

    public String getHeadPortraitAddr() {
        return headPortraitAddr;
    }

    public void setHeadPortraitAddr(String headPortraitAddr) {
        this.headPortraitAddr = headPortraitAddr;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
