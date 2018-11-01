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
public class PostDTO extends BaseDTO {
    private Post post;
    private List<Post> postList;
    private Integer count;
    private List<PostInformation> postInformationList;
    private Integer infoId;
    private Integer postId;

    public List<PostInformation> getPostInformationList() {
        return postInformationList;
    }

    public void setPostInformationList(List<PostInformation> postInformationList) {
        this.postInformationList = postInformationList;
    }

    public PostDTO() {
    }

    public PostDTO(Boolean success, String message, Post post) {
        super(success, message);
        this.post = post;
    }

    public PostDTO(Boolean success, String message) {
        super(success, message);
    }

    public Integer getCount() {
        return count;
    }

    public void setCount(Integer count) {
        this.count = count;
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

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public Integer getPostId() {
        return postId;
    }

    @Override
    public String toString() {
        return "PostDTO{" +
                "post=" + post +
                ", postList=" + postList +
                ", count=" + count +
                ", postInformationList=" + postInformationList +
                ", infoId=" + infoId +
                ", postId=" + postId +
                "} " + super.toString();
    }
}
