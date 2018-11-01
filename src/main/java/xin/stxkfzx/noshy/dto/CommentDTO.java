package xin.stxkfzx.noshy.dto;

import xin.stxkfzx.noshy.domain.Comment;

import java.util.List;

/**
 * 封装评论 DTO
 *
 * @author fmy
 * @date 2018-09-05 13:48
 */
public class CommentDTO extends BaseDTO{
    private List<Comment> commentList;
    private Comment comment;

    public CommentDTO(Boolean success, String message) {
        super(success, message);
    }

    public CommentDTO(Boolean success, String message, Comment comment) {
        super(success, message);
        this.comment = comment;
    }

    public CommentDTO(Boolean success, String message, List<Comment> commentList) {

        super(success, message);
        this.commentList = commentList;
    }

    public Comment getComment() {
        return comment;
    }

    public void setComment(Comment comment) {
        this.comment = comment;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "commentList=" + commentList +
                ", comment=" + comment +
                "} " + super.toString();
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}
