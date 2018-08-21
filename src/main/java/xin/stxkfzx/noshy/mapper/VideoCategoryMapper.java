package xin.stxkfzx.noshy.mapper;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.VideoCategory;

@Repository
public interface VideoCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(VideoCategory record);

    int insertSelective(VideoCategory record);

    VideoCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(VideoCategory record);

    int updateByPrimaryKey(VideoCategory record);
}