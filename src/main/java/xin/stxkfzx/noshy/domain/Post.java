package xin.stxkfzx.noshy.domain;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;
import java.util.List;

@ApiModel
public class Post {

    /**
     * 私人权限，仅自己可见
     */
    public static final int PRIVATE = 2;

    /**
     * 学校权限，同一学校可见
     */
    public static final int STUDENT = 1;

    /**
     * 公开权限，所有人可见
     */
    public static final int PUBLIC = 0;

    /**
     * 置顶状态
     */
    public static final int STICK = 4;

    /**
     * 加精状态
     */
    public static final int BEST = 3;

    /**
     * 热门状态
     */
    public static final int HOT = 2;

    /**
     * 帖子可见状态
     */
    public static final int DISPLAY = 1;

    /**
     * 帖子不可见状态(审核状态)
     */
    public static final int UN_DISPLAY = 0;

    /**
     * 删除状态(从数据库中删除)
     */
    public static final int DELETE = -1;

    @ApiModelProperty(hidden = true)
    private Integer postId;
    @ApiModelProperty(hidden = true)
    private Date createTime;
    @ApiModelProperty(hidden = true)
    private Date lastEditTime;

    @ApiModelProperty(value = "帖子状态", notes = "状态值:0-4,分别对应帖子不可见状态(审核状态),帖子可见状态,热门状态,加精状态,置顶状态")
    private Integer status;
    @ApiModelProperty(hidden = true)
    private Integer parentId;
    @ApiModelProperty(hidden = true)
    private Integer pageView;

    @ApiModelProperty(value = "帖子权限", notes = "状态值:0-2,分别对应公开权限,学校权限,私人权限")
    private Integer authority;

    @ApiModelProperty(value = "创建者ID")
    private Integer userId;

    @ApiModelProperty(value = "帖子分类")
    private Integer postCategory;
    @ApiModelProperty(value = "帖子标题")
    private String title;
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private List<PostInformation> postInformationList;
    /**
     * 发起者的发帖内容
     */
    @ApiModelProperty(hidden = true)
    @JsonIgnore
    private PostInformation postInformation;

    @ApiModelProperty(value = "帖子描述")
    private String description;


    @Override
    public String toString() {
        return "Post{" +
                "postId=" + postId +
                ", createTime=" + createTime +
                ", lastEditTime=" + lastEditTime +
                ", status=" + status +
                ", parentId=" + parentId +
                ", pageView=" + pageView +
                ", authority=" + authority +
                ", userId=" + userId +
                ", postCategory=" + postCategory +
                ", title='" + title + '\'' +
                ", postInformationList=" + postInformationList +
                ", postInformation=" + postInformation +
                ", description='" + description + '\'' +
                '}';
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public PostInformation getPostInformation() {
        return postInformation;
    }

    public void setPostInformation(PostInformation postInformation) {
        this.postInformation = postInformation;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }


    public List<PostInformation> getPostInformationList() {
        return postInformationList;
    }

    public void setPostInformationList(List<PostInformation> postInformationList) {
        this.postInformationList = postInformationList;
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getPostCategory() {
        return postCategory;
    }

    public void setPostCategory(Integer postCategory) {
        this.postCategory = postCategory;
    }

}