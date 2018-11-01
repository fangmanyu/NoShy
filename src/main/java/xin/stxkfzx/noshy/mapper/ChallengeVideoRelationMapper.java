package xin.stxkfzx.noshy.mapper;
import java.util.List;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.ChallengeVideoRelation;

@Repository
public interface ChallengeVideoRelationMapper {
    int deleteByPrimaryKey(@Param("challengeId") Integer challengeId, @Param("videoId") String videoId);

    int insert(ChallengeVideoRelation record);

    int insertSelective(ChallengeVideoRelation record);

    ChallengeVideoRelation selectByPrimaryKey(@Param("challengeId") Integer challengeId, @Param("videoId") String videoId);

    int updateByPrimaryKeySelective(ChallengeVideoRelation record);

    int updateByPrimaryKey(ChallengeVideoRelation record);

    List<Integer> findChallengeIdByVideoId(@Param("videoId")String videoId);

    int updateStatusByVideoId(@Param("updatedStatus")Integer updatedStatus,@Param("videoId")String videoId);


}