package xin.stxkfzx.noshy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.PostInformation;

import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-07-29 12:38
 */
public class PostInformationMapperTest extends BaseTest {
    @Autowired
    private PostInformationMapper postInformationMapper;

    @Test
    public void deleteByPostId() {
        int i = postInformationMapper.deleteByPostId(2);
        assertEquals(7, i);
    }

    @Test
    public void selectPostInformationList() {
        List<PostInformation> postInformations = postInformationMapper.selectPostInformationList(2, 2, 4);
        for (PostInformation temp :
                postInformations) {
            System.out.println(temp.getInfoContent());
        }
        int count = postInformationMapper.countByPostId(2);
        System.out.println(postInformations.size());
        System.out.println(count);
        System.out.println(postInformations);
    }


}