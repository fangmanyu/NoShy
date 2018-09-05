package xin.stxkfzx.noshy.dto;

import xin.stxkfzx.noshy.domain.Comment;

import java.util.List;

/**
 * 封装评论 DTO
 *
 * @author fmy
 * @date 2018-09-05 13:48
 */
public class CommentDTO {

    private Boolean success;
    private String message;
    private List<Comment> commentList;

    public CommentDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public CommentDTO(Boolean success, String message, List<Comment> commentList) {

        this.success = success;
        this.message = message;
        this.commentList = commentList;
    }

    @Override
    public String toString() {
        return "CommentDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", commentList=" + commentList +
                '}';
    }

    public Boolean getSuccess() {
        return success;
    }

    public void setSuccess(Boolean success) {
        this.success = success;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public List<Comment> getCommentList() {
        return commentList;
    }

    public void setCommentList(List<Comment> commentList) {
        this.commentList = commentList;
    }
}