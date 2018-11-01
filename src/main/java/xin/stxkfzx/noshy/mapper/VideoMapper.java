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
                                       @Param("pageSize") int pageSize, @Param("schoolId") Integer schoolId, @Param("isOurSchool")Boolean isOurSchool);

    int countByVideoCondition(@Param("videoCondition") Video videoCondition, @Param("schoolId") Integer schoolId, @Param("isOurSchool")Boolean isOurSchool);


    int updateVideoStatus(@Param("videoId") String videoId, @Param("status") String status);

    List<Video> selectByCategoryIdAndStatus(@Param("cateId") Long cateId, @Param("status")String status);

    List<Video> findByUserIdAndStatus(@Param("userId") Long userId, @Param("status")String status);
}