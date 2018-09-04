package xin.stxkfzx.noshy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.Video;
import xin.stxkfzx.noshy.domain.VideoCategory;
import xin.stxkfzx.noshy.domain.VideoTag;
import xin.stxkfzx.noshy.dto.VideoDTO;
import xin.stxkfzx.noshy.exception.VideoServiceException;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertTrue;

/**
 * @author fmy
 * @date 2018-07-23 14:29
 */

public class VideoServiceTest extends BaseTest {

    @Autowired
    private VideoService videoService;
    private Video video;

    @Before
    public void setUp() throws Exception {
        video = new Video("测试上传功能", new File("F:\\视频资料\\基于Spring Boot技术栈的博客系统企业级实战\\第1章 Spring Boot 简介\\1-2 -Spring Boot 是什么.mp4"));
        video.setVideoCategory(810963431L);
        video.setDescription("测试添加浏览信息");
        video.setVideoId("887111f293834bcb91b3acfa63b825ee");

        List<VideoTag> videoTagList = new ArrayList<>(5);
        videoTagList.add(new VideoTag("不错"));
        videoTagList.add(new VideoTag("甚好"));
        videoTagList.add(new VideoTag("三定ad"));
        videoTagList.add(new VideoTag("快儿科"));
        videoTagList.add(new VideoTag("测试"));
        video.setTags(videoTagList);
    }

    @Transactional
    @Rollback(value = false)
    @Test
    public void uploadVideo() {

        VideoDTO videoDTO = videoService.uploadVideo(video);
        assertTrue(videoDTO.getSuccess());
    }

    @Test
    public void getPlayUrl() {
        VideoDTO playUrl = videoService.getPlayUrl(video.getVideoId());
        assertTrue(playUrl.getSuccess());
        System.out.println(playUrl.getPlayUrl());
    }

    @Test
    public void getVideoByVideoId() {
        VideoDTO videoByVideoId = videoService.getVideoByVideoId(video.getVideoId());
        assertTrue(videoByVideoId.getSuccess());
        System.out.println(videoByVideoId.getVideo());
    }

    @Transactional
    @Rollback(value = false)
    @Test
    public void updateVideoByVideoId() {
        Video videoCondition = new Video();
        videoCondition.setVideoId("887111f293834bcb91b3acfa63b825ee");
        videoCondition.setTitle("测试更新数据");
        ArrayList<VideoTag> tags = new ArrayList<>();
        tags.add(new VideoTag("好吃不上火"));
        tags.add(new VideoTag("好吃不上火2"));
        videoCondition.setTags(tags);
        VideoDTO videoDTO = videoService.updateVideoByVideoId(videoCondition);
        assertTrue(videoDTO.getSuccess());

    }

    @Transactional
    @Rollback(value = false)
    @Test
    public void deleteVideoByVideoId() {
        VideoDTO videoDTO = videoService.deleteVideoByVideoId(video.getVideoId());
        assertTrue(videoDTO.getSuccess());
    }

    @Transactional
    @Rollback(value = false)
    @Test
    public void addCategory() {
        try {
            videoService.addCategory("客户端编程", 967943064L);
        } catch (VideoServiceException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void getVideoCategoryByAliId() {
        VideoDTO videoDTO = videoService.getVideoCategoryByAliId("878006187");
        assertTrue(videoDTO.getSuccess());
        System.out.println(videoDTO.getVideoCategory());

    }

    @Test
    public void listCategory() {
        VideoDTO videoDTO = videoService.listCategory();
        List<VideoCategory> videoCategoryList = videoDTO.getVideoCategoryList();
        try {
            String s = new ObjectMapper().writeValueAsString(videoCategoryList);
            System.out.println(s);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void listVideoByCategory() {
        VideoDTO videoDTO = null;
        try {
            videoDTO = videoService.listVideoByCategory(810963431L);
            assertTrue(videoDTO.getSuccess());
            List<Video> videoList = videoDTO.getVideoList();
            System.out.println(videoList.size());
        } catch (VideoServiceException e) {
            e.printStackTrace();
        }
    }

    @Transactional
    @Rollback(value = false)
    @Test
    public void removeCategory() {
        try {
            videoService.removeCategory(44695809L);
        } catch (VideoServiceException e) {
            e.printStackTrace();
        }
    }
}