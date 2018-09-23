package xin.stxkfzx.noshy.mapper;

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
}