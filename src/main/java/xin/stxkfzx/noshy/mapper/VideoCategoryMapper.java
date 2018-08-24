package xin.stxkfzx.noshy.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.VideoCategory;

import java.util.List;

@Repository
public interface VideoCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(VideoCategory record);

    int insertSelective(VideoCategory record);

    VideoCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(VideoCategory record);

    int updateByPrimaryKey(VideoCategory record);

    /**
     * 通过阿里云分类Id删除视频分类
     *
     * @param id 阿里云分类Id
     * @return
     * @author fmy
     * @date 2018-08-23 19:34
     */
    int deleteByAliyunId(Long id);

    /**
     * 通过阿里云id查询分类
     *
     * @param id
     * @return
     * @author fmy
     * @date 2018-08-24 10:20
     */
    VideoCategory selectByAliyunId(Long id);

    /**
     * 查询子分类列表
     *
     * @param parentId	父类Id.若查询一级分类,则parentId为null
     * @return
     * @author fmy
     * @date 2018-08-24 11:51
     */
    List<VideoCategory> selectChildrenCategory(@Param("parentId") Long parentId);

}