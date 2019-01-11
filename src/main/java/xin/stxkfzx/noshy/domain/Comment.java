package xin.stxkfzx.noshy.domain;

import java.io.Serializable;
import java.util.List;

public class Comment implements Serializable {
    private static final long serialVersionUID = -1294871909104031092L;
    private Integer commentId;

    private Integer browseId;

    /** 父类评论编号*/
    private Integer parentId;

    private String commentContent;

    private Long userId;

    /** 评论点赞量*/
    private Integer commentLikes;

    /** 子类评论列表*/
    private List<Comment> childrenCommentList;


    public List<Comment> getChildrenCommentList() {
        return childrenCommentList;
    }

    public void setChildrenCommentList(List<Comment> childrenCommentList) {
        this.childrenCommentList = childrenCommentList;
    }

    public Integer getCommentId() {
        return commentId;
    }

    public void setCommentId(Integer commentId) {
        this.commentId = commentId;
    }

    public Integer getBrowseId() {
        return browseId;
    }

    public void setBrowseId(Integer browseId) {
        this.browseId = browseId;
    }

    public Integer getParentId() {
        return parentId;
    }

    public void setParentId(Integer parentId) {
        this.parentId = parentId;
    }

    public String getCommentContent() {
        return commentContent;
    }

    public void setCommentContent(String commentContent) {
        this.commentContent = commentContent;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public Integer getCommentLikes() {
        return commentLikes;
    }

    public void setCommentLikes(Integer commentLikes) {
        this.commentLikes = commentLikes;
    }

    @Override
    public String toString() {
        return "Comment{" +
                "commentId=" + commentId +
                ", browseId=" + browseId +
                ", parentId=" + parentId +
                ", commentContent='" + commentContent + '\'' +
                ", userId=" + userId +
                ", commentLikes=" + commentLikes +
                ", childrenCommentList=" + childrenCommentList +
                '}';
    }
}