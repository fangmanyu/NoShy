package xin.stxkfzx.noshy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.Post;

import java.util.List;

import static org.junit.Assert.assertEquals;

/**
 * @author fmy
 * @date 2018-07-29 20:59
 */
public class PostMapperTest extends BaseTest {

    @Autowired
    private PostMapper postMapper;

    @Test
    public void selectByPrimaryKey() {
        Post post = postMapper.selectByPrimaryKey(4);
        System.out.println(post);
    }

    @Test
    public void getPostStatus() {
        Integer postStatus = postMapper.queryPostStatus(6);
        // assertEquals(1, postStatus);
        System.out.println(postStatus);
    }

    @Test
    public void queryByPostCondition() {
        Post postCondition = new Post();

        List<Post> postList = postMapper.queryByPostCondition(null, 0, 10);
        int i = postMapper.countByPostCondition(null);
        assertEquals(i, postList.size());

        postList = postMapper.queryByPostCondition(postCondition, 0, 10);
        i = postMapper.countByPostCondition(postCondition);
        assertEquals(i, postList.size());

        postCondition.setTitle("2");
        postList = postMapper.queryByPostCondition(postCondition, 0, 10);
        i = postMapper.countByPostCondition(postCondition);
        assertEquals(i, postList.size());
    }
}