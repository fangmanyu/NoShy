package xin.stxkfzx.noshy.service.impl;

import com.aliyun.vod.upload.impl.UploadVideoImpl;
import com.aliyun.vod.upload.req.UploadStreamRequest;
import com.aliyun.vod.upload.resp.UploadStreamResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.vod.model.v20170321.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import xin.stxkfzx.noshy.domain.*;
import xin.stxkfzx.noshy.dto.VideoDTO;
import xin.stxkfzx.noshy.exception.VideoServiceException;
import xin.stxkfzx.noshy.mapper.*;
import xin.stxkfzx.noshy.service.VideoService;
import xin.stxkfzx.noshy.util.ImageUtil;
import xin.stxkfzx.noshy.util.PageCalculator;
import xin.stxkfzx.noshy.util.PathUtil;
import xin.stxkfzx.noshy.vo.ImageHolder;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author fmy
 * @date 2018-07-23 14:21
 */
@Service
public class VideoServiceImpl implements VideoService {
    private static final Logger log = LogManager.getLogger(VideoServiceImpl.class);

    private static final String VIDEO_CALLBACK_URL = "http://fmy.ngrok.xiaomiqiu.cn:8080/video/callback";
    private final VideoMapper videoMapper;
    private final VideoTagMapper videoTagMapper;
    private final VideoCategoryMapper videoCategoryMapper;
    private final BrowseInformationMapper browseInformationMapper;
    private final ImageMapper imageMapper;

    /**
     * 高清度模板
     *
     * @date 2018-07-25 8:58
     */
    private static final String TEMPLATE_GROUP_ID = "a1e88a9d519118917522af2aca247bd8";

    @Autowired
    public VideoServiceImpl(VideoTagMapper videoTagMapper, VideoMapper videoMapper, VideoCategoryMapper videoCategoryMapper, BrowseInformationMapper browseInformationMapper, ImageMapper imageMapper) {
        this.videoTagMapper = videoTagMapper;
        this.videoMapper = videoMapper;
        this.videoCategoryMapper = videoCategoryMapper;
        this.browseInformationMapper = browseInformationMapper;
        this.imageMapper = imageMapper;
    }

    @Override
    public VideoDTO getPlayUrl(String videoId) throws VideoServiceException {

        if (StringUtils.isEmpty(videoId)) {
            return new VideoDTO(false, "videoId 不能为空");
        }

        DefaultAcsClient client = VideoServiceImpl.initVodClient();
        GetPlayInfoResponse response;
        try {
            response = getPlayInfo(client, videoId);
            List<GetPlayInfoResponse.PlayInfo> playInfoList = response.getPlayInfoList();
            //播放地址
            for (GetPlayInfoResponse.PlayInfo playInfo : playInfoList) {
                VideoDTO videoDTO = new VideoDTO(true, "获取播放地址成功");
                videoDTO.setPlayUrl(playInfo.getPlayURL());

                return videoDTO;

            }

        } catch (Exception e) {
            throw new VideoServiceException("获取视频信息错误：" + e.getLocalizedMessage());
        }

        return new VideoDTO(false, "获取视频信息错误");
    }

    @Transactional(rollbackFor = VideoServiceException.class)
    @Override
    public VideoDTO uploadVideo(Video video, ImageHolder image) throws VideoServiceException {
        if (video == null || StringUtils.isAllEmpty(video.getTitle(), video.getName())
                || video.getVideoInputStream() == null) {
            return new VideoDTO(false, "video 为空");
        }


        // 上传至阿里云点播
        log.info("开始上传视频");
        String videoId = uploadStream(video);

        if (StringUtils.isEmpty(videoId)) {
            throw new VideoServiceException("video 上传失败");
        }

        int type = BrowseInformation.VIDEO;
        String addr = null;
        if (image != null) {
            // 设置封面图片
            try {
                String path = PathUtil.getImageBasePath(videoId, type);
                addr = ImageUtil.generateThumbnail(image, path);

                Image image1 = new Image();
                image1.setType(type);
                image1.setBelongId(videoId);
                image1.setCreateTime(new Date());
                image1.setImageUrl(addr);
                imageMapper.insert(image1);
            } catch (Exception e) {
                throw new VideoServiceException("视频封面保存失败" + e.getMessage());
            }

        }

        // 构建浏览信息
        BrowseInformation browseInformation = new BrowseInformation();
        browseInformation.setBrowseType(type);
        browseInformation.setLikes(0);
        browseInformation.setPageviews(0);
        browseInformation.setShares(0);
        browseInformationMapper.insertSelective(browseInformation);
        log.debug("构建视频浏览信息Id: {}", browseInformation.getBrowseId());

        // 设置视频状态
        video.setStatus(Video.UPLOADING);
        video.setVideoId(videoId);
        video.setBrowseId(browseInformation.getBrowseId());
        video.setCreateTime(new Date());
        video.setLastEditTime(new Date());
        video.setImageUrl(addr);
        log.debug("上传的video信息: {}", video);

        int i = videoMapper.insert(video);
        if (i <= 0) {
            throw new VideoServiceException("保存video失败");
        }

        // 不能先保存标签，因为标签依赖于视频
        List<VideoTag> tags = video.getTags();
        if (tags != null && tags.size() > 0) {
            for (VideoTag videoTag :
                    tags) {
                videoTag.setVideoId(videoId);
            }

            i = videoTagMapper.batchInsertVideoTag(tags);
            if (i <= 0) {
                throw new VideoServiceException("保存videoTag失败： ");
            }
        }

        log.info("上传视频结束");

        return new VideoDTO(true, "上传视频成功");
    }

    @Transactional(rollbackFor = VideoServiceException.class)
    @Override
    public VideoDTO getVideoByVideoId(String videoId) throws VideoServiceException {
        if (StringUtils.isEmpty(videoId)) {
            return new VideoDTO(false, "videoId 为空");
        }

        // TODO 从阿里云获取视频状态，并更新到数据库中。这是获取视频状态的一个权宜之计(策略:你用我才更新),以后需要改进
        try {
            GetVideoInfoResponse response = getVideoInfo(videoId);
            String status = response.getVideo().getStatus();
            videoMapper.updateVideoStatus(videoId, status);
        } catch (Exception e) {
            log.error("视频状态获取失败");
            throw new VideoServiceException("视频状态获取失败: " + e.getMessage());
        }

        Video video = videoMapper.selectByPrimaryKey(videoId);
        log.debug("从数据库中查询的video信息: {}", video);

        return new VideoDTO(true, "查询成功", video);
    }


    @Transactional(rollbackFor = VideoServiceException.class)
    @Override
    public VideoDTO updateVideoByVideoId(Video videoCondition) throws VideoServiceException {
        if (videoCondition == null) {
            return new VideoDTO(false, "查询条件为空");
        }

        Video video = videoMapper.selectByPrimaryKey(videoCondition.getVideoId());
        if (video == null) {
            return new VideoDTO(false, "更新的视频不存在");
        }

        // 设置不允许更新字段和更新时间
        videoCondition.setStatus(null);
        videoCondition.setUserId(null);
        videoCondition.setCreateTime(null);
        videoCondition.setLastEditTime(new Date());

        // 更新数据库视频信息
        int effectedNum = videoMapper.updateByPrimaryKeySelective(videoCondition);
        if (effectedNum <= 0) {
            throw new VideoServiceException("数据库更新视频失败");
        }
        // 更新数据库标签信息
        List<VideoTag> videoTagList = videoCondition.getTags();
        if (videoTagList != null && videoTagList.size() > 0) {
            String videoId = videoCondition.getVideoId();
            effectedNum = videoTagMapper.deleteByVideoId(videoId);
            if (effectedNum <= 0) {
                throw new VideoServiceException("数据库删除标签失败");
            }

            for (VideoTag videoTag :
                    videoTagList) {
                videoTag.setVideoId(videoId);
            }
            effectedNum = videoTagMapper.batchInsertVideoTag(videoTagList);
            if (effectedNum <= 0) {
                throw new VideoServiceException("数据库更新标签失败");
            }
        }

        System.out.println("更新阿里云视频的信息: " + videoCondition);
        // 更新阿里云上的视频信息
        try {
            updateVideoInfo(videoCondition);
        } catch (Exception e) {
            throw new VideoServiceException("阿里云更新视频失败：" + e.getLocalizedMessage());
        }

        return new VideoDTO(true, "更新成功");
    }

    @Transactional(rollbackFor = VideoServiceException.class)
    @Override
    public VideoDTO deleteVideoByVideoId(String videoId) throws VideoServiceException {
        if (StringUtils.isEmpty(videoId)) {
            return new VideoDTO(false, "videoId 为空");
        }

        Video video = videoMapper.selectByPrimaryKey(videoId);
        if (video == null) {
            return new VideoDTO(false, "删除的视频不存在");
        }

        // 删除数据库中的记录
        /// 先解除标签与视频的外键约束
        int effectedNum = videoTagMapper.updateVideoIdToNull(videoId);
        if (effectedNum <= 0) {
            throw new VideoServiceException("数据库标签解除外键失败");
        }
        // 删除标签
        effectedNum = videoTagMapper.deleteByVideoId(null);
        if (effectedNum <= 0) {
            throw new VideoServiceException("数据库标签删除失败");
        }

        // 删除视频封面图片
        int type = BrowseInformation.VIDEO;
        try {
            imageMapper.deleteByTypeAndBelongId(type, videoId);
        } catch (Exception e) {
            e.printStackTrace();
            throw new VideoServiceException("数据库图片删除失败");
        }
        ImageUtil.deleteFile(PathUtil.getImageBasePath(videoId, type));

        // 删除视频
        effectedNum = videoMapper.deleteByPrimaryKey(videoId);
        if (effectedNum <= 0) {
            throw new VideoServiceException("数据库视频删除失败");
        }

        // 从阿里云上删除视频
        try {
            DeleteVideoRequest request = new DeleteVideoRequest();
            request.setVideoIds(videoId);
            initVodClient().getAcsResponse(request);
        } catch (ClientException e) {
            String localizedMessage = e.getLocalizedMessage();
            // 判断该异常是否由删除的视频不存在引起，如果是，则返回失败的结果；否则抛出该异常
            String notFoundVideo = "InvalidVideo.NotFound";
            if (StringUtils.contains(localizedMessage, notFoundVideo)) {
                return new VideoDTO(false, "删除的视频不存在");
            }

            throw new VideoServiceException("阿里云视频删除失败：" + localizedMessage);
        }

        return new VideoDTO(true, "视频删除成功");
    }

    @Override
    public VideoDTO listVideo(Video videoCondition, int pageIndex, int pageSize) {
        if (pageIndex < 0 || pageSize < 0) {
            return new VideoDTO(false, "pageIndex 或 pageSize 错误");
        }

        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);

        // 根据查询条件返回视频对象(没有关联视频标签，原因：关联和视频的关系为一对多，关联是将属于同一个视频的标签封装的一个List中，
        // 加入limit实现分页将导致得到的记录行数不完整，封装获取到的List大小和预期不符)
        List<Video> videoList = videoMapper.selectByVideoCondition(videoCondition, rowIndex, pageSize);
        // 获取查询总数
        int count = videoMapper.countByVideoCondition(videoCondition);
        if (videoList == null || videoList.size() == 0) {
            return new VideoDTO(false, "查询失败");
        }

        // 将标签列表放入视频中
        for (Video video : videoList) {
            List<VideoTag> videoTagList = videoTagMapper.queryByVideoId(video.getVideoId());
            video.setTags(videoTagList);
        }

        System.out.println("videoList---------" + videoList);
        VideoDTO videoDTO = new VideoDTO(true, "查询成功", videoList);
        videoDTO.setCount(count);

        return videoDTO;
    }

    @Override
    public VideoDTO getVideoCategoryByAliId(String id) {
        VideoCategory videoCategory = new VideoCategory();
        GetCategoriesResponse response;

        try {
            response = getCategories(Long.valueOf(id));
            GetCategoriesResponse.Category1 category1 = response.getCategory1();

            videoCategory.setParentId(category1.getParentId());
            videoCategory.setAliyunId(category1.getCateId());
            videoCategory.setCategoryName(category1.getCateName());

        } catch (Exception e) {
            throw new VideoServiceException("getVideoCategoryByAliId error: " + e.getMessage());
        }

        VideoDTO videoDTO = new VideoDTO(true, "查询成功");
        videoDTO.setVideoCategory(videoCategory);
        return videoDTO;
    }

    @Override
    public VideoDTO listVideoByCategory(Long id) throws VideoServiceException {
        VideoCategory videoCategory = videoCategoryMapper.selectByAliyunId(id);

        VideoDTO videoDTO = Optional.ofNullable(videoCategory).map(category -> {
            Long categoryAliyunId = category.getAliyunId();
            List<Video> videos = videoMapper.selectByCategoryId(categoryAliyunId);
            VideoDTO dto = new VideoDTO(true, "查询成功");
            dto.setVideoList(videos);
            return dto;
        }).orElse(new VideoDTO(false, "无该分类"));

        return videoDTO;
    }

    @Override
    public VideoDTO listCategory() {
        List<VideoCategory> videoCategoryList = videoCategoryMapper.selectChildrenCategory(null);
        generateChildCategory(videoCategoryList);

        log.debug("全部分类: {}", videoCategoryList);

        VideoDTO dto = new VideoDTO(true, "查询成功");
        dto.setVideoCategoryList(videoCategoryList);
        return dto;
    }

    @Override
    public VideoDTO listMyVideo(int userId) {
        List<Video> videoList = videoMapper.findByUserId((long) userId);
        return new VideoDTO(true, "查询成功", videoList);
    }

    @Transactional(rollbackFor = VideoServiceException.class)
    @Override
    public VideoDTO addCategory(String categoryName, Long parentId) throws VideoServiceException {
        AddCategoryResponse response;

        Long cateId;
        try {
            response = addCategoryDetail(categoryName, parentId);
            cateId = response.getCategory().getCateId();
        } catch (Exception e) {
            throw new VideoServiceException("阿里云创建分类失败: " + e.getMessage());
        }

        VideoCategory category = new VideoCategory();
        category.setAliyunId(cateId);
        category.setCategoryName(categoryName);
        if (parentId == -1) {
            category.setParentId(null);
        } else {
            category.setParentId(parentId);
        }
        try {
            videoCategoryMapper.insert(category);
        } catch (Exception e) {
            throw new VideoServiceException("数据库创建分类失败: " + e.getMessage());
        }


        return new VideoDTO(true, "创建成功");
    }

    @Transactional(rollbackFor = VideoServiceException.class)
    @Override
    public VideoDTO removeCategory(Long id) throws VideoServiceException {
        // TODO 将该分类下的视频移到父分类上.如果该分类是一级分类,则直接删除该分类下的视频

        DeleteCategoryRequest request = new DeleteCategoryRequest();
        request.setCateId(id);

        try {
            initVodClient().getAcsResponse(request);
        } catch (ClientException e) {
            throw new VideoServiceException("阿里云删除分类失败: " + e.getMessage());
        }

        try {
            videoCategoryMapper.deleteByAliyunId(id);
        } catch (Exception e) {
            throw new VideoServiceException("数据库删除分类失败: " + e.getMessage());
        }

        return new VideoDTO(true, "删除成功");

    }

    private GetCategoriesResponse getCategories(Long cateId) throws Exception {
        GetCategoriesRequest request = new GetCategoriesRequest();
        request.setCateId(cateId);
        return initVodClient().getAcsResponse(request);
    }

    private void generateChildCategory(List<VideoCategory> categoryList) {
        if (categoryList == null) {
            return;
        }

        for (VideoCategory category :
                categoryList) {
            List<VideoCategory> videoCategories = videoCategoryMapper.selectChildrenCategory(category.getAliyunId());
            generateChildCategory(videoCategories);
            category.setChildrenCategory(videoCategories);
        }

    }

    /**
     * 获取播放地址函数
     */
    private GetPlayInfoResponse getPlayInfo(DefaultAcsClient client, String videoId) throws Exception {
        GetPlayInfoRequest request = new GetPlayInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }

    private String uploadStream(Video video) throws VideoServiceException {
        UploadStreamRequest request = new UploadStreamRequest(ACCESS_KEY_ID, ACCESS_KEY_SECRET, video.getTitle(),
                video.getName(), video.getVideoInputStream());
        /* 是否使用默认水印(可选)，指定模板组ID时，根据模板组配置确定是否使用默认水印*/
        ///request.setShowWaterMark(true);
        // TODO 使用VOD上传到点播回调出现问题导致无法将上传的视频信息存储到数据库中，所以暂时取消事件回调。以后可以使用OOS进行上传
        /* 设置上传完成后的回调URL(可选)，建议通过点播控制台配置消息监听事件，参见文档 https://help.aliyun.com/document_detail/57029.html */
        // request.setCallback(VIDEO_CALLBACK_URL);
        /* 视频分类ID(可选) */
        request.setCateId(video.getVideoCategory());
        /* 视频标签,多个用逗号分隔(可选) */
        request.setTags(getTagString(video));
        /* 视频描述(可选) */
        request.setDescription(video.getDescription());
        /* 封面图片(可选) */
        request.setCoverURL(video.getImageUrl());
        /* 模板组ID(可选) */
        request.setTemplateGroupId(TEMPLATE_GROUP_ID);
        /* 存储区域(可选) */
        ///request.setStorageLocation("in-201703232118266-5sejdln9o.oss-cn-shanghai.aliyuncs.com");
        UploadVideoImpl uploader = new UploadVideoImpl();
        UploadStreamResponse response = uploader.uploadStream(request);

        // 上传不成功
        if (!response.isSuccess()) {
            throw new VideoServiceException("视频上传失败--" + response.getMessage());
        }

        return response.getVideoId();
    }

    private AddCategoryResponse addCategoryDetail(String categoryName, Long parentId) throws Exception {
        AddCategoryRequest request = new AddCategoryRequest();
        // 父分类ID，若不填，则默认生成一级分类，根节点分类ID为-1
        request.setParentId(parentId);
        // 分类名称，不能超过64个字节，UTF8编码
        request.setCateName(categoryName);
        return initVodClient().getAcsResponse(request);
    }

    private String getTagString(Video video) {
        List<VideoTag> tags = video.getTags();

        StringBuilder sb = new StringBuilder();
        if (tags != null && tags.size() > 0) {
            for (int i = 0; i < tags.size(); i++) {
                sb.append(tags.get(i).getName());
                if (i < tags.size() - 1) {
                    sb.append(",");
                }
            }
        }
        return "".equals(sb.toString()) ? null : sb.toString();
    }

    private static DefaultAcsClient initVodClient() {
        //点播服务所在的Region，国内请填cn-shanghai，不要填写别的区域
        String regionId = "cn-shanghai";
        DefaultProfile profile = DefaultProfile.getProfile(regionId, ACCESS_KEY_ID, ACCESS_KEY_SECRET);
        return new DefaultAcsClient(profile);
    }

    private UpdateVideoInfoResponse updateVideoInfo(Video video) throws Exception {
        DefaultAcsClient client = initVodClient();
        UpdateVideoInfoRequest request = new UpdateVideoInfoRequest();
        request.setVideoId(video.getVideoId());

        String title = video.getTitle();
        if (StringUtils.isNotEmpty(title)) {
            System.out.println("更新标题： " + title);
            request.setTitle(title);
        }

        String description = video.getDescription();
        if (StringUtils.isNotEmpty(description)) {

            System.out.println("更新描述： " + description);
            request.setDescription(description);
        }

        Long videoCategory = video.getVideoCategory();
        if (videoCategory != null) {
            System.out.println("更新分类： " + videoCategory);
            request.setCateId(videoCategory);
        }

        String imageUrl = video.getImageUrl();
        if (StringUtils.isNotEmpty(imageUrl)) {

            System.out.println("更新封面地址： " + imageUrl);
            request.setCoverURL(imageUrl);
        }

        String tagString = getTagString(video);
        System.out.println("更新标签： " + tagString);
        request.setTags(tagString);

        return client.getAcsResponse(request);
    }

    private GetVideoInfoResponse getVideoInfo(String videoId) throws Exception {
        DefaultAcsClient client = initVodClient();
        GetVideoInfoRequest request = new GetVideoInfoRequest();
        request.setVideoId(videoId);
        return client.getAcsResponse(request);
    }
}
