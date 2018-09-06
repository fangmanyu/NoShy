package xin.stxkfzx.noshy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.Comment;
import xin.stxkfzx.noshy.dto.CommentDTO;
import xin.stxkfzx.noshy.exception.CommentServiceException;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-09-05 14:53
 */
public class CommentServiceTest extends BaseTest {

    @Autowired
    private CommentService commentService;

    @Test
    public void addComment() {
        Comment comment = new Comment();
        comment.setCommentLikes(15);
        comment.setBrowseId(1);
        comment.setCommentContent("测试添加评论");
        try {
            CommentDTO commentDTO = commentService.addComment(comment);
            assertTrue(commentDTO.getSuccess());
        } catch (CommentServiceException e) {
            e.printStackTrace();
        }

    }

    @Test
    public void removerComment() {

        try {
            CommentDTO commentDTO = commentService.removerComment(1);
            assertTrue(commentDTO.getSuccess());
        } catch (CommentServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listCommentByBrowseId() throws JsonProcessingException {

        CommentDTO dto = commentService.listCommentByBrowseId(1);
        assertTrue(dto.getSuccess());

        List<Comment> commentList = dto.getCommentList();
        String json = new ObjectMapper().writeValueAsString(commentList);
        System.out.println(json);
    }

    @Test
    public void getComment() {
        CommentDTO dto = commentService.getComment(1);
        Comment comment = dto.getComment();
        System.out.println(comment);
    }
}