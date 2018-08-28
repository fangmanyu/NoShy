package xin.stxkfzx.noshy.domain;

public class Comment {
    private Integer commentId;

    private Integer browseId;

    /** 父类评论编号*/
    private Integer parentId;

    private String commentContent;

    private Long userId;

    /** 评论点赞量*/
    private Integer commentLikes;

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
        StringBuilder sb = new StringBuilder();
        sb.append(getClass().getSimpleName());
        sb.append(" [");
        sb.append("Hash = ").append(hashCode());
        sb.append(", commentId=").append(commentId);
        sb.append(", browseId=").append(browseId);
        sb.append(", parentId=").append(parentId);
        sb.append(", commentContent=").append(commentContent);
        sb.append(", userId=").append(userId);
        sb.append(", commentLikes=").append(commentLikes);
        sb.append("]");
        return sb.toString();
    }
}