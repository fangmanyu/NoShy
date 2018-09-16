package xin.stxkfzx.noshy.dto;

import xin.stxkfzx.noshy.domain.Post;
import xin.stxkfzx.noshy.domain.PostInformation;

import java.util.List;

/**
 * 封装帖子信息的DTO
 *
 * @author fmy
 * @date 2018-07-28 18:19
 */
public class PostDTO {
    private Boolean success;
    private String message;
    private Post post;
    private List<Post> postList;
    private Integer count;
    private List<PostInformation> postInformationList;
    private Integer infoId;

    @Override
    public String toString() {
        return "PostDTO{" +
                "success=" + success +
                ", message='" + message + '\'' +
                ", post=" + post +
                ", postList=" + postList +
                ", count=" + count +
                ", postInformationList=" + postInformationList +
                ", infoId=" + infoId +
                '}';
    }

    public List<PostInformation> getPostInformationList() {
        return postInformationList;
    }

    public void setPostInformationList(List<PostInformation> postInformationList) {
        this.postInformationList = postInformationList;
    }

    public PostDTO() {
    }

    public PostDTO(Boolean success, String message, Post post) {
        this.success = success;
        this.message = message;
        this.post = post;
    }

    public PostDTO(Boolean success, String message) {
        this.success = success;
        this.message = message;
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public Post getPost() {
        return post;
    }

    public void setPost(Post post) {
        this.post = post;
    }

    public List<Post> getPostList() {
        return postList;
    }

    public void setPostList(List<Post> postList) {
        this.postList = postList;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

    public Integer getInfoId() {
        return infoId;
    }
}
