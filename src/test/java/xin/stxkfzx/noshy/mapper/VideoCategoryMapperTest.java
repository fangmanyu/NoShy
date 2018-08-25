package xin.stxkfzx.noshy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.VideoCategory;

import java.util.List;

/**
 * @author fmy
 * @date 2018-08-24 12:01
 */
public class VideoCategoryMapperTest extends BaseTest {

    @Autowired
    private  VideoCategoryMapper videoCategoryMapper;

    @Test
    public void selectChildrenCategory() {
        List<VideoCategory> videoCategoryList = videoCategoryMapper.selectChildrenCategory(878006187L);
        System.out.println(videoCategoryList);
    }
}