package xin.stxkfzx.noshy.vo.challenge;

import xin.stxkfzx.noshy.vo.UserVO;
import xin.stxkfzx.noshy.vo.video.VideoDetailVO;
import xin.stxkfzx.noshy.vo.video.VideoVO;

/**
 * @author fmy
 * @date 2018-09-18 21:20
 */
public class RankDetailVO {

    private UserVO userInfo;
    private Integer likes;
    private String videoId;
    private Integer rankId;
    private VideoDetailVO videoInfo;

    @Override
    public String toString() {
        return "RankDetailVO{" +
                "userInfo=" + userInfo +
                ", likes=" + likes +
                ", videoId='" + videoId + '\'' +
                ", rankId=" + rankId +
                ", videoInfo=" + videoInfo +
                ", hasLiked=" + hasLiked +
                '}';
    }


    public VideoDetailVO getVideoInfo() {
        return videoInfo;
    }

    public void setVideoInfo(VideoDetailVO videoInfo) {
        this.videoInfo = videoInfo;
    }

    /**当前用户是否已经点赞*/
    private Boolean hasLiked;

    public Integer getRankId() {
        return rankId;
    }

    public void setRankId(Integer rankId) {
        this.rankId = rankId;
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


    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
