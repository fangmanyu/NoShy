package xin.stxkfzx.noshy.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.Before;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.annotation.Commit;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;
import xin.stxkfzx.noshy.BaseTest;
import xin.stxkfzx.noshy.domain.Video;
import xin.stxkfzx.noshy.domain.VideoCategory;
import xin.stxkfzx.noshy.domain.VideoTag;
import xin.stxkfzx.noshy.dto.VideoDTO;
import xin.stxkfzx.noshy.exception.VideoServiceException;
import xin.stxkfzx.noshy.vo.ImageHolder;
import xin.stxkfzx.noshy.vo.video.CallbackVO;

import java.io.*;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
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
        video = new Video("sdakjlsdh", new File("E:\\download\\12月20日-微服务系列 Spring Boot项目实战.mp4"));
        video.setVideoCategory(810963431L);
        video.setDescription("测试获取凭证");
        // video.setVideoId("887111f293834bcb91b3acfa63b825ee");

        List<VideoTag> videoTagList = new ArrayList<>(5);
        videoTagList.add(new VideoTag("313不错"));
        videoTagList.add(new VideoTag("甚3231好"));
        videoTagList.add(new VideoTag("123"));
        videoTagList.add(new VideoTag("11"));
        videoTagList.add(new VideoTag("测1231试"));
        video.setTags(videoTagList);
    }


    @Commit
    @Test
    public void uploadVideo() throws FileNotFoundException {
        File file = new File("D:\\图片\\动漫\\5c7413e1c674e1d88f94b99d2531aa43.jpg");
        ImageHolder image = new ImageHolder(file.getName(), new FileInputStream(file));
        VideoDTO videoDTO = videoService.uploadVideo(video, image);
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
        VideoDTO videoDTO = null;
        try {
            videoDTO = videoService.deleteVideoByVideoId("c6a958b06fac43d0900cd5ed568d8507");
            System.out.println(videoDTO.getMessage());
            assertTrue(videoDTO.getSuccess());
        } catch (VideoServiceException e) {
            e.printStackTrace();
        }
    }

    @Commit
    @Test
    public void addCategory() {
        try {
            videoService.addCategory("悄悄关注", -1L);
        } catch (Exception e) {
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

    /**
     * 从本地导入视频信息
     *
     * @throws IOException
     */
    @Commit
    @Test
    public void localImport() throws IOException {
        File file = new File("C:\\Users\\59261\\Desktop\\noshy");
        File[] files = file.listFiles();
        for (File item :
                files) {
            Video video = new Video();
            video.setTitle(item.getName());
            // video.setUserId(27L);
            video.setVideoCategory(810963431L);
            ImageHolder image = null;
            // System.out.println(item.getName());
            File[] listFiles = item.listFiles();
            for (File temp : listFiles) {
                if (temp.toString().contains(".txt")) {
                    BufferedReader reader = new BufferedReader(new InputStreamReader(new FileInputStream(temp), "gbk"));
                    String buff = null;
                    StringBuilder sb = new StringBuilder();
                    while ((buff = reader.readLine()) != null) {
                        sb.append(buff);
                    }
                    video.setDescription(sb.toString());
                    // System.out.println(sb.toString());
                    reader.close();
                } else if (temp.toString().contains(".mp4")) {
                    video.setVideoInputStream(new FileInputStream(temp));
                    video.setName(temp.getName());
                } else {
                    // video.setImageUrl(temp.getAbsolutePath());
                    image = new ImageHolder(temp.getName(), new FileInputStream(temp));
                }
            }


            VideoDTO videoDTO = videoService.uploadVideo(video, image);
            System.out.println(videoDTO.getMessage());
            // System.out.println(video);
            System.out.println("********************");
        }
    }

    @Commit
    @Test
    public void createUploadVideo() throws FileNotFoundException {
        File file = new File("D:\\图片\\动漫\\5c7413e1c674e1d88f94b99d2531aa43.jpg");
        ImageHolder imageHolder = new ImageHolder(file.getName(), new FileInputStream(file));

        VideoDTO dto = videoService.createUploadVideo(video, imageHolder);
        System.out.println(dto.getVideoId());
        System.out.println(dto.getUploadAuth());
        System.out.println(dto.getUploadAddress());
    }

    @Test
    public void refreshUploadVideo() {
        String videoId = "5f38f41237874d7fa2a5bec86841d3ae";
        VideoDTO dto = videoService.refreshUploadVideo(videoId);
        System.out.println(dto.getUploadAuth());
        System.out.println(dto.getUploadAddress());
    }

    @Test
    public void name() throws IOException {
        String json = "{\"Status\":\"success\",\"IsAudio\":false,\"VideoId\":\"1359cfac2e1c44c5bea27f9958999f84\",\"EventType\":\"StreamTranscodeComplete\",\"Size\":150212,\"Definition\":\"LD\",\"Fps\":\"25\",\"Duration\":10.0,\"Bitrate\":\"120\",\"Encrypt\":false,\"FileUrl\":\"http://video.stxkfzx.xin/1359cfac2e1c44c5bea27f9958999f84/fde8baa73cf04d6592e44371164634ec-46e486a30d10b47ddab9a2fe2a892e2b-ld.m3u8\",\"Format\":\"m3u8\",\"EventTime\":\"2018-10-08T09:55:51Z\",\"Height\":540,\"Width\":960,\"JobId\":\"05794af1ffee414fa1ea2210720bff09\"}";
        CallbackVO callbackVO = new ObjectMapper().readValue(json, CallbackVO.class);
        System.out.println(callbackVO);
    }
}