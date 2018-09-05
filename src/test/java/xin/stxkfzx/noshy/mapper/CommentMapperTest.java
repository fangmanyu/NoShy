package xin.stxkfzx.noshy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.Comment;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-09-05 14:31
 */
public class CommentMapperTest extends BaseTest {
    @Autowired
    private CommentMapper commentMapper;

    @Test
    public void findByParentId() {
        List<Comment> commentList = commentMapper.findByParentId(null);
        System.out.println(commentList.size());
        commentList = commentMapper.findByParentId(1);
        System.out.println(commentList.size());
    }

    @Test
    public void findByBrowseAndParentId() {
        List<Comment> list = commentMapper.findByBrowseAndParentId(1, null);
        System.out.println(list);

    }
}