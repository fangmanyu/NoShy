package xin.stxkfzx.noshy.vo.post;

import xin.stxkfzx.noshy.vo.UserVO;

import java.util.Date;

/**
 * 评论封装
 *
 * @author fmy
 * @date 2018-11-08 16:19
 */
public class PostVO {
    private Integer postId;
    private Date createTime;
    private Date lastEditTime;
    private Integer status;
    private Integer parentId;
    private Integer pageView;
    private Integer authority;
    private Integer postCategory;
    private String title;
    private String description;

    private UserVO userInfo;

    @Override
    public String toString() {
        return "PostVO{" +
                "postId=" + postId +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", status=" + status +
                ", parentId=" + parentId +
                ", pageView=" + pageView +
                ", authority=" + authority +
                ", postCategory=" + postCategory +
                ", title='" + title + '\'' +
                ", description='" + description + '\'' +
                ", userInfo=" + userInfo +
                '}';
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
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

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public Integer getPageView() {
        return pageView;
    }

    public void setPageView(Integer pageView) {
        this.pageView = pageView;
    }

    public Integer getAuthority() {
        return authority;
    }

    public void setAuthority(Integer authority) {
        this.authority = authority;
    }

    public Integer getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(Integer postCategory) {
        this.postCategory = postCategory;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public UserVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserVO userInfo) {
        this.userInfo = userInfo;
    }
}
