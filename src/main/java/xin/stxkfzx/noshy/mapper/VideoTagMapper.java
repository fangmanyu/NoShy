package xin.stxkfzx.noshy.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.VideoTag;

import java.util.List;

@Repository
public interface VideoTagMapper {
    int deleteByPrimaryKey(Integer tagId);


    int insert(VideoTag record);

    int insertSelective(VideoTag record);

    VideoTag selectByPrimaryKey(Integer tagId);

    int updateByPrimaryKeySelective(VideoTag record);

    int updateByPrimaryKey(VideoTag record);

    /**
     * 通过videoId 查询VideoTag列表
     *
     * @param videoId
     * @return
     * @author fmy
     * @date 2018-07-24 14:23
     */
    List<VideoTag> queryByVideoId(String videoId);

    /**
     * 批量添加
     *
     * @param videoTagList
     * @return
     * @author fmy
     * @date 2018-07-24 14:23
     */
    int batchInsertVideoTag(List<VideoTag> videoTagList);

    /**
     * 将指定标签的外键设置为空(用于删除前置)
     *
     * @param videoId
     * @return
     * @author fmy
     * @date 2018-07-24 21:49
     */
    int setVideoIdToNull(String videoId);

    /**
     * 删除指定视频的标签
     *
     * @param videoId 指定视频Id，如果videoId 为null，则删除将videoId置空的标签
     * @return
     * @author fmy
     * @date 2018-07-26 21:53
     */
    int deleteByVideoId(@Param("videoId") String videoId);
}
