package xin.stxkfzx.noshy.mapper;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.Video;

import java.io.File;
import java.util.Date;
import java.util.List;

import static org.junit.Assert.*;

/**
 * @author fmy
 * @date 2018-07-24 15:44
 */
public class VideoMapperTest extends BaseTest {

    @Autowired
    private VideoMapper videoMapper;

    @Test
    public void insert() {
        Video video = new Video("123", new File("E:\\download\\慕课网java仿抖音实战\\第1章 千呼万唤史出来，微信小程序的简要学习\\1.5.mp4"));
        video.setVideoId("111111");
        video.setStatus(Video.Normal);
        int i = videoMapper.insert(video);
        assertEquals(1, i);
    }

    @Test
    public void selectByPrimaryKey() {
        Video video = videoMapper.selectByPrimaryKey("1a4d701f2d2545cf8749b2bd4f59e5b7");
        System.out.println(video);
    }

    @Test
    public void countAndSelectByVideoCondition() {
        Video videoCondition = new Video();
        List<Video> videoList = videoMapper.selectByVideoCondition(videoCondition, 0, 10, 2, false);
        int count = videoMapper.countByVideoCondition(videoCondition, 2, false);
        System.out.println(videoList.size());
        System.out.println(count);

        videoMapper.selectByVideoCondition(null, 0, 10, null, true);
        count = videoMapper.countByVideoCondition(null, null, true);
        System.out.println(videoList.size());
        System.out.println(count);

        videoCondition.setTitle("视频");
        videoList = videoMapper.selectByVideoCondition(videoCondition, 0, 2, 1, true);
        count = videoMapper.countByVideoCondition(videoCondition, 1, true);
        System.out.println(videoList);
        System.out.println(videoList.size());
        System.out.println(count);

        videoCondition.setVideoCategory(878006187L);
        videoList = videoMapper.selectByVideoCondition(videoCondition, 0, 10, 1, false);
        count = videoMapper.countByVideoCondition(videoCondition, 1, false);
        System.out.println(videoList.size());
        System.out.println(count);

    }

    @Test
    public void updateVideoStatus() {
        int i = videoMapper.updateVideoStatus("86e20e600a3647f8abf453d11360f114", Video.Normal);
        assertEquals(0, i);
    }

    @Test
    public void selectByCategoryId() {
        List<Video> videos = videoMapper.selectByCategoryId(810963431L);
        System.out.println(videos);
    }

    @Test
    public void updateByPrimaryKeySelective() {
        Video video = new Video();
        video.setVideoId("962dbcf0eb424cbe93d62f8da5ffec80");
        video.setLastEditTime(new Date());
        video.setTitle("视频标题");
        int i = videoMapper.updateByPrimaryKeySelective(video);
        assertEquals(1, i);
    }
}