package xin.stxkfzx.noshy.vo.challenge;

import xin.stxkfzx.noshy.vo.UserVO;

/**
 * @author fmy
 * @date 2018-09-18 21:20
 */
public class RankDetailVO {

    private UserVO userInfo;
    private String headPortraitAddr;
    private Integer grade;
    private Integer likes;
    private String videoId;

    @Override
    public String toString() {
        return "RankDetailVO{" +
                "userInfo=" + userInfo +
                ", headPortraitAddr='" + headPortraitAddr + '\'' +
                ", grade=" + grade +
                ", likes=" + likes +
                ", videoId='" + videoId + '\'' +
                '}';
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

    public Integer getGrade() {
        return grade;
    }

    public void setGrade(Integer grade) {
        this.grade = grade;
    }

    public Integer getLikes() {
        return likes;
    }

    public void setLikes(Integer likes) {
        this.likes = likes;
    }
}
