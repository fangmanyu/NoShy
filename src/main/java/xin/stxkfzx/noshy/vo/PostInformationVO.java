package xin.stxkfzx.noshy.vo;

import java.util.Date;

/**
 * 帖子消息 VO
 *
 * @author fmy
 * @date 2018-08-03 10:39
 */
public class PostInformationVO {
    private String infoContent;
    private String imageUrl;
    private Date createTime;
    private Integer postId;
    private UserVO user;

    @Override
    public String toString() {
        return "PostInformationVO{" +
                "infoContent='" + infoContent + '\'' +
                ", imageUrl='" + imageUrl + '\'' +
                ", createTime=" + createTime +
                ", postId=" + postId +
                ", user=" + user +
                '}';
    }

    public UserVO getUser() {
        return user;
    }

    public void setUser(UserVO user) {
        this.user = user;
    }

    public String getInfoContent() {
        return infoContent;
    }

    public void setInfoContent(String infoContent) {
        this.infoContent = infoContent;
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

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

}
