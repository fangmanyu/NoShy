package xin.stxkfzx.noshy.service;

import xin.stxkfzx.noshy.domain.Video;
import xin.stxkfzx.noshy.dto.VideoDTO;
import xin.stxkfzx.noshy.exception.VideoServiceException;

/**
 * 阿里视频点播服务
 *
 * @author fmy
 * @date 2018-07-23 14:21
 */
public interface VideoService {
    String ACCESS_KEY_ID = "LTAIPQqlRW1AMVLJ";
    String ACCESS_KEY_SECRET = "nVFZyVGzpNzLkhJATLVtMRhOiugBvy";

    /**
     * 将视频上传到阿里云点播库，并将保存的视频信息保存保存到数据库中,并创建浏览信息
     *
     * @param video 需要上传的视频。其中视频标题，名称(含后缀名)，文件流是必须的
     * @return 返回状态标识
     * @throws VideoServiceException 视频服务异常
     * @author fmy
     * @date 2018-07-24 15:00
     */
    VideoDTO uploadVideo(Video video) throws VideoServiceException;

    /**
     * 获取指定视频在阿里云的播放地址
     *
     * @param videoId 指定视频Id
     * @return  返回状态标识
     * @throws VideoServiceException 视频服务异常
     * @author fmy
     * @date 2018-07-24 17:17
     */
    VideoDTO getPlayUrl(String videoId) throws VideoServiceException;

    /**
     * 获取指定的视频信息
     *
     * @param videoId   指定的视频ID
     * @return  返回状态标识
     * @throws VideoServiceException 视频服务异常
     * @author fmy
     * @date 2018-07-24 17:29
     */
    VideoDTO getVideoByVideoId(String videoId)  throws VideoServiceException;

    /**
     * 更新指定视频信息
     *
     * @param videoCondition 更新内容。可以更新标题，描述，标签，封面图片地址，视频分类
     * @return 返回状态标识
     * @throws VideoServiceException 视频服务异常
     * @author fmy
     * @date 2018-07-24 17:56
     */
    VideoDTO updateVideoByVideoId(Video videoCondition) throws VideoServiceException;

    /**
     * 删除指定视频
     *
     * @param videoId 指定的视频ID
     * @return 返回状态标识
     * @throws VideoServiceException 视频服务异常
     * @author fmy
     * @date 2018-07-24 18:01
     */
    VideoDTO deleteVideoByVideoId(String videoId) throws VideoServiceException;

    /**
     * 根据查询条件分页获取视频列表
     *
     * @param videoCondition 查询条件。可选条件：标题名(模糊)，视频分类，视频状态，用户Id, 描述(模糊)
     * @param pageIndex 起始位置
     * @param pageSize 每页大小
     * @return 返回状态标识
     * @author fmy
     * @date 2018-07-25 8:41
     */
    VideoDTO listVideo(Video videoCondition, int pageIndex, int pageSize);

    /**
     * 获取视频分类
     *
     * @param id id
     * @return 返回状态标识
     * @author fmy
     * @date 2018-08-21 16:31
     */
    VideoDTO getVideoCategoryByAliId(String id);

    /**
     * 创建分类
     *
     * @param categoryName	分类名称
     * @param parentId	 父类Id.若是一级分类,则id为-1
     * @return 返回状态标识
     * @throws VideoServiceException 视频服务异常
     * @author fmy
     * @date 2018-08-21 17:50
     */
    VideoDTO addCategory(String categoryName, Long parentId) throws VideoServiceException;

    /**
     * 删除分类
     *
     * @param id 分类Id
     * @return 返回状态标识
     * @throws VideoServiceException 视频服务异常
     * @author fmy
     * @date 2018-08-21 18:38
     */
    VideoDTO removeCategory(Long id) throws VideoServiceException;

    /**
     * 通过分类获取视频
     *
     * @param id
     * @return
     * @throws VideoServiceException
     * @author fmy
     * @date 2018-08-24 10:16
     */
    VideoDTO listVideoByCategory(Long id) throws VideoServiceException;

    /**
     * 查询全部分类列表
     *
     * @param
     * @return
     * @author fmy
     * @date 2018-08-24 10:17
     */
    VideoDTO listCategory();
}
