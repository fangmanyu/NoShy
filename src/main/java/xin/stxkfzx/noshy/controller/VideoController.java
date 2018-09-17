package xin.stxkfzx.noshy.controller;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import springfox.documentation.annotations.ApiIgnore;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.domain.Video;
import xin.stxkfzx.noshy.domain.VideoTag;
import xin.stxkfzx.noshy.dto.VideoDTO;
import xin.stxkfzx.noshy.exception.VideoServiceException;
import xin.stxkfzx.noshy.service.VideoService;
import xin.stxkfzx.noshy.vo.ImageHolder;
import xin.stxkfzx.noshy.vo.JSONResponse;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.*;

/**
 * 视频 Controller
 *
 * @author fmy
 * @date 2018-07-24 15:24
 */
@RestController
@RequestMapping("/video")
@Api(description = "视频相关接口")
@Validated
public class VideoController {

    /**
     * 800MB = 800 * 1024 * 1024 * 8 = 6710886400
     */
    private static final long MAX_VIDEO_FILE_SIZE = 6710886400L;
    private static final Logger log = LogManager.getLogger(VideoController.class);

    private final VideoService videoService;
    private User currentUser;

    @ApiOperation(value = "获取自己发布的视频列表")
    @GetMapping("/myVideo")
    public JSONResponse listMyVideo() {
        if (currentUser == null || currentUser.getUserId() == null) {
            return new JSONResponse(false, "用户未登录");
        }

        VideoDTO videoDTO = videoService.listMyVideo(currentUser.getUserId().intValue());
        return new JSONResponse(true, "查询成功", videoDTO.getVideoList());
    }

    @ApiOperation(value = "通过分类获取视频列表")
    @ApiImplicitParam(name = "categoryId", value = "分类Id")
    @GetMapping("/category/{categoryId}")
    public JSONResponse listVideoByCategory(@Min(0) @PathVariable Long categoryId) {
        VideoDTO videoDTO = videoService.listVideoByCategory(categoryId);
        return new JSONResponse(videoDTO.getSuccess(), videoDTO.getMessage(), videoDTO.getVideoList());
    }

    /**
     * 获取全部视频分类列表
     *
     * @param
     * @return
     * @author fmy
     * @date 2018-08-24 17:08
     */
    @ApiOperation(value = "获取全部视频分类列表")
    @GetMapping("/listVideoCategory")
    public JSONResponse listVideoCategory() {
        VideoDTO videoDTO = videoService.listCategory();
        return new JSONResponse(videoDTO.getSuccess(), videoDTO.getMessage(), videoDTO.getVideoCategoryList());
    }

    /**
     * 获取指定视频播放路径
     *
     * @param videoId
     * @return
     * @author fmy
     * @date 2018-07-26 22:34
     */
    @ApiOperation(value = "获取指定视频播放路径")
    @ApiImplicitParam(name = "videoId", value = "指定视频Id")
    @GetMapping("/{videoId}/play")
    public JSONResponse getPlayUrl(@PathVariable String videoId) {
        if (StringUtils.isEmpty(videoId)) {
            return new JSONResponse(false, "videoId 为空");
        }

        VideoDTO playUrl = videoService.getPlayUrl(videoId);
        return new JSONResponse(playUrl.getSuccess(), playUrl.getMessage(), playUrl.getPlayUrl());
    }

    /**
     * 删除视频
     *
     * @param videoId
     * @return
     * @author fmy
     * @date 2018-07-26 22:51
     */
    @ApiOperation(value = "删除视频")
    @ApiImplicitParam(name = "videoId", value = "更新视频Id")
    @DeleteMapping("/{videoId}")
    public JSONResponse removeVideo(@PathVariable String videoId) {
        if (StringUtils.isEmpty(videoId)) {
            return new JSONResponse(false, "videoId 为空");
        }

        VideoDTO videoDTO = null;
        try {
            videoDTO = videoService.deleteVideoByVideoId(videoId);
        } catch (VideoServiceException e) {
            e.printStackTrace();
        }
        return new JSONResponse(videoDTO.getSuccess(), videoDTO.getMessage());
    }


    /**
     * 更新视频信息
     *
     * @param videoId
     * @param video
     * @return
     * @author fmy
     * @date 2018-07-26 21:17
     */
    @ApiOperation(value = "更新视频信息")
    @ApiImplicitParam(name = "videoId", value = "更新视频Id")
    @PostMapping("/{videoId}")
    public JSONResponse modifyVideoInfo(@PathVariable("videoId") String videoId,
                                        @ApiParam(value = "更新视频信息") @RequestBody Video video) {
        if (StringUtils.isEmpty(videoId)) {
            return new JSONResponse(false, "videoId 为空");
        }

        if (video == null) {
            return new JSONResponse(false, "video 为空");
        }


        video.setVideoId(videoId);
        VideoDTO videoDTO = videoService.updateVideoByVideoId(video);

        return new JSONResponse(videoDTO.getSuccess(), videoDTO.getMessage());
    }

    /**
     * 查询指定视频信息
     *
     * @param videoId
     * @return
     * @author fmy
     * @date 2018-07-26 11:28
     */
    @ApiOperation(value = "查询指定视频信息")
    @ApiImplicitParam(name = "videoId", value = "视频Id")
    @GetMapping("/{videoId}")
    public JSONResponse getVideo(@PathVariable("videoId") String videoId) {
        if (StringUtils.isEmpty(videoId)) {
            return new JSONResponse(false, "videoId 为空");
        }

        VideoDTO video = videoService.getVideoByVideoId(videoId);

        JSONResponse jsonResponse = new JSONResponse(video.getSuccess(), video.getMessage());
        if (video.getSuccess()) {
            Video data = video.getVideo();
            log.debug("查询的视频信息: {}", data);
            jsonResponse.setData(data);
        }

        return jsonResponse;
    }


    /**
     * 获取视频列表
     *
     * @param videoStr
     * @param pageIndex
     * @param pageSize
     * @return
     * @author fmy
     * @date 2018-07-25 16:23
     */
    @ApiOperation(value = "获取视频列表", notes = "根据查询条件返回视频列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "videoStr", value = "查询条件.可以通过标题和内容进行查询.查询全部列表则忽略"),
            @ApiImplicitParam(name = "pageIndex", value = "分页起始位置"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小")
    })
    @GetMapping
    public JSONResponse listVideos(@RequestParam(value = "videoStr", required = false) String videoStr,
                                   @RequestParam("pageIndex") @Min(0) int pageIndex,
                                   @RequestParam("pageSize") @Min(0) int pageSize) {
        VideoDTO videoDTO;
        if (StringUtils.isEmpty(videoStr) || StringUtils.isBlank(videoStr)) {
            videoDTO = videoService.listVideo(null, pageIndex, pageSize);
        } else {
            Video video = new Video();
            video.setTitle(videoStr);
            video.setDescription(videoStr);
            videoDTO = videoService.listVideo(video, pageIndex, pageSize);
        }


        if (videoDTO.getSuccess()) {
            for (Video temp :
                    videoDTO.getVideoList()) {
                String videoId = temp.getVideoId();
                VideoDTO playUrl = videoService.getPlayUrl(videoId);

                if (playUrl.getSuccess()) {
                    temp.setPlayUrl(playUrl.getPlayUrl());
                }
            }

            Map<String, Object> modelMap = new HashMap<>(3);
            modelMap.put("videoList", videoDTO.getVideoList());
            modelMap.put("count", videoDTO.getCount());
            return new JSONResponse(true, "查询成功", modelMap);
        }

        return new JSONResponse(false, "查询失败：" + videoDTO.getMessage());
    }


    /**
     * 上传视频
     *
     * @param title
     * @param videoCategory
     * @param videoFile
     * @param description
     * @param tags
     * @return
     * @author fmy
     * @date 2018-07-25 16:23
     */
    @ApiOperation(value = "上传视频", produces = "multipart/form-data")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "title", value = "视频标题", required = true),
            @ApiImplicitParam(name = "videoCategory", value = "视频分类", required = true),
            @ApiImplicitParam(name = "description", value = "视频描述"),
            @ApiImplicitParam(name = "tags", value = "视频标签。最多16个标签，每个标签不能超过5个字，标签之间以英文状态下的逗号(,)隔开")
    })
    @PostMapping
    public JSONResponse uploadVideo(@RequestParam("title") String title,
                                    @RequestParam("videoCategory") Long videoCategory,
                                    @ApiParam(value = "视频文件流对象", required = true) @RequestParam("videoFile") MultipartFile videoFile,
                                    @RequestParam(value = "description", required = false) String description,
                                    @RequestParam(value = "tags", required = false) String tags,
                                    @RequestParam(value = "videoImage", required = false) MultipartFile videoImage) {

        // 传参检查
        /// 获取当前登录用户
        // TODO 解除下面注释。从session中获取登录用户的userId，这里方便测试，使用假数据
        currentUser = new User();
        currentUser.setUserId(26L);
        // if (currentUser == null || currentUser.getUserId() < 0) {
        //     return new JSONResponse(false, "userId 错误");
        // }
        boolean flag = StringUtils.isEmpty(title) || videoCategory == null || videoCategory < 0;
        if (flag) {
            return new JSONResponse(false, "video 对象错误");
        }

        if (videoFile == null) {
            return new JSONResponse(false, "文件流为空");
        }


        if (videoFile.getSize() >= MAX_VIDEO_FILE_SIZE) {
            return new JSONResponse(false, "文件超过800M");
        }

        // 构建上传视频信息
        Video video = new Video();
        video.setTitle(title);
        video.setVideoCategory(videoCategory);
        video.setDescription(description);
        video.setUserId(currentUser.getUserId());
        video.setTags(strTagsToList(tags));
        ImageHolder imageHolder;
        try {
             imageHolder = new ImageHolder(videoFile.getOriginalFilename(), videoImage.getInputStream());
        } catch (IOException e) {
            e.printStackTrace();
            return new JSONResponse(false, "系统错误，获取图片流失败: " + e.getMessage());
        }

        try {
            video.setName(videoFile.getOriginalFilename());
            video.setVideoInputStream(videoFile.getInputStream());
        } catch (IOException e) {
            return new JSONResponse(false, "系统错误，获取文件流失败: " + e.getMessage());
        }

        try {
            VideoDTO videoDTO = videoService.uploadVideo(video, imageHolder);

            JSONResponse jsonResponse;
            if (videoDTO.getSuccess()) {
                jsonResponse = new JSONResponse((true), "上传成功");
            } else {
                jsonResponse = new JSONResponse(false, videoDTO.getMessage());
            }
            return jsonResponse;
        } catch (VideoServiceException e) {
            return new JSONResponse(false, e.getMessage());
        }
    }

    /**
     * 将String形式的标签转换为List形式
     *
     * @param tags 以逗号分隔的标签
     * @return VideoTag集合
     * @author fmy
     * @date 2018-07-25 14:45
     */
    private List<VideoTag> strTagsToList(String tags) {
        if (StringUtils.isEmpty(tags)) {
            return null;
        }

        String[] split = StringUtils.split(tags, ',');
        ArrayList<VideoTag> tagsList = new ArrayList<>();
        VideoTag videoTag = new VideoTag();

        for (int i = 0; i < split.length; i++) {
            videoTag.setName(StringUtils.trim(split[i]));
            tagsList.add(videoTag);
        }
        return tagsList;
    }


    // FIXME: 2018/7/26 0026 回调不可用

    // @PostMapping("/callback")
    @ApiIgnore(value = "getCallbackInfo")
    public void getCallbackInfo(HttpServletRequest request) {

        BufferedReader br = null;
        try {
            br = new BufferedReader(new InputStreamReader((ServletInputStream) request.getInputStream(), "utf-8"));
            StringBuffer sb = new StringBuffer("");
            String temp;
            while ((temp = br.readLine()) != null) {
                sb.append(temp);
            }
            br.close();

            System.out.println("视频处理回调----------------");
            System.out.println(sb.toString());
        } catch (IOException e) {
            e.printStackTrace();
        }

        // System.out.println("回调 videoCallBackDTO---------" + videoCallBackDTO);

        // if (StringUtils.isAnyEmpty(EventType, VideoId, Status)) {
        //     return;
        // }
        /*if (videoCallBackDTO == null) {
            return;
        }

        if ("success".equals(videoCallBackDTO.getStatus())) {
            Video videoCondition = new Video();
            videoCondition.setVideoId(videoCallBackDTO.getVideoId());

            if ("FileUploadComplete".equals(videoCallBackDTO.getEventType())) {
                videoCondition.setStatus(Video.TRANSCODING);
            } else if ("StreamTranscodeComplete".equals(videoCallBackDTO.getEventType())) {
                videoCondition.setStatus(Video.Normal);
            }

            videoService.updateVideoByVideoId(videoCondition);
        }*/
    }

    @ModelAttribute
    public void initUser(HttpSession session) {
        Object currentUser = session.getAttribute("currentUser");
        if (currentUser instanceof User) {
            log.debug("获取当前登录用户: " + currentUser);
            this.currentUser = (User) currentUser;
        }
    }

    @Autowired
    public VideoController(VideoService videoService) {
        this.videoService = videoService;
    }


}
