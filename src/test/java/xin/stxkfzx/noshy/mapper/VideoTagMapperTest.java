package xin.stxkfzx.noshy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.VideoTag;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-07-24 14:28
 */
public class VideoTagMapperTest extends BaseTest {

    @Autowired
    private VideoTagMapper videoTagMapper;

    @Test
    public void queryByVideoId() {
        List<VideoTag> videoTagList = videoTagMapper.queryByVideoId("2df62c712f4e45239e4c42fa0b9d3080");
        assertNotNull(videoTagList);
    }

    @Test
    public void batchInsertVideoTag() {
        List<VideoTag> videoTagList = new ArrayList<>(1000);
        VideoTag videoTag;

        for (int i = 0; i < 1000; i++) {
            videoTag = new VideoTag();
            videoTag.setVideoId("1a4d701f2d2545cf8749b2bd4f59e5b7");
            videoTag.setName("测试" + i);
            videoTagList.add(videoTag);
        }

        int i = videoTagMapper.batchInsertVideoTag(videoTagList);
        assertEquals(videoTagList.size(), i);
    }

    @Test
    public void testSetVideoIdToNull() {
        int i = videoTagMapper.updateVideoIdToNull("962dbcf0eb424cbe93d62f8da5ffec80");
        assertEquals(2, i);
    }

    @Test
    public void deleteByVideoId() {
        int i = videoTagMapper.deleteByVideoId(null);
        assertEquals(0, i);

        i = videoTagMapper.deleteByVideoId("962dbcf0eb424cbe93d62f8da5ffec80");
        assertEquals(2, i);
    }
}