package xin.stxkfzx.noshy.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.Video;

import java.util.List;

@Repository
public interface VideoMapper {
    int deleteByPrimaryKey(String videoId);

    int insert(Video record);

    int insertSelective(Video record);

    Video selectByPrimaryKey(String videoId);

    int updateByPrimaryKeySelective(Video record);

    int updateByPrimaryKey(Video record);

    List<Video> selectByVideoCondition(@Param("videoCondition") Video videoCondition,
                                       @Param("rowIndex") int rowIndex,
                                       @Param("pageSize") int pageSize);

    int countByVideoCondition(@Param("videoCondition") Video videoCondition);


    int updateVideoStatus(@Param("videoId") String videoId, @Param("status") String status);

    List<Video> selectByCategoryId(@Param("cateId") Long cateId);

}