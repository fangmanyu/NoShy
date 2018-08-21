package xin.stxkfzx.noshy.service;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.Post;
import xin.stxkfzx.noshy.domain.PostInformation;
import xin.stxkfzx.noshy.dto.PostDTO;

import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-07-29 10:30
 */
public class PostServiceTest extends BaseTest {

    @Autowired
    private PostService postService;

    @Test
    public void createPost() {
        Post post = new Post();
        PostInformation creator = new PostInformation();
        creator.setCreateTime(new Date());
        creator.setInfoContent("发布一个帖子");

        post.setAuthority(Post.PUBLIC);
        post.setCreateTime(new Date());
        post.setLastEditTime(new Date());
        post.setStatus(Post.DISPLAY);
        post.setPostInformation(creator);

        PostDTO postDTO = postService.createPost(post);
        assertTrue(postDTO.getSuccess());
    }

    @Test
    public void removePost() {
        PostDTO postDTO = postService.removePost(2);
        assertTrue(postDTO.getSuccess());
    }

    @Test
    public void addPostInformation() {
        PostInformation information = new PostInformation();
        information.setInfoContent("测试添加内容");
        information.setCreateTime(new Date());

        PostDTO postDTO = postService.addPostInformation(information);
        assertTrue(postDTO.getSuccess());
    }

    @Test
    public void modifyPostStatus() {
        PostDTO postDTO = postService.modifyPostStatus(3, -1);
        assertTrue(postDTO.getSuccess());
    }

    @Test
    public void getPost() {
        PostDTO post = postService.getPost(2, 2, 5);
        assertTrue(post.getSuccess());
        assertEquals(2, post.getPostInformationList().size());
        assertEquals(7, post.getCount().intValue());
    }

    @Test
    public void listPost() {
        Post postCondition = new Post();

        PostDTO postList = postService.listPost(null, 0, 10);
        assertTrue(postList.getSuccess());
        assertEquals(postList.getCount().intValue(), postList.getPostList().size());


        postList = postService.listPost(postCondition, 0, 10);
        assertTrue(postList.getSuccess());
        assertEquals(postList.getCount().intValue(), postList.getPostList().size());

        postCondition.setTitle("2");
        postList = postService.listPost(postCondition, 0, 10);
        assertTrue(postList.getSuccess());
        assertEquals(postList.getCount().intValue(), postList.getPostList().size());

    }

    @Test
    public void addPageViewNum() {
        PostDTO postDTO = postService.addPageViewNum(-2);
        assertTrue(postDTO.getSuccess());
    }
}
