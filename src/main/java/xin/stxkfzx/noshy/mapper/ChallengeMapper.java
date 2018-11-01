package xin.stxkfzx.noshy.mapper;

import org.apache.ibatis.annotations.Param;

import java.util.List;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.Challenge;

@Repository
public interface ChallengeMapper {
    int deleteByPrimaryKey(Integer challengeId);

    int insert(Challenge record);

    int insertSelective(Challenge record);

    Challenge selectByPrimaryKey(Integer challengeId);

    int updateByPrimaryKeySelective(Challenge record);

    int updateByPrimaryKey(Challenge record);

    List<Challenge> find(@Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    Integer count();

    /**
     * 获取创建挑战者的视频Id
     *
     * @param challengeId
     * @param userId
     * @return
     * @author fmy
     * @date 2018-09-20 11:16
     */
    String findOwnerVideoId(@Param("challengeId") int challengeId, @Param("userId") int userId);


    Challenge findOneByChallengeIdAndStatusGreaterThanOrEqualTo(@Param("challengeId") Integer challengeId, @Param("minStatus") Integer minStatus);

    List<Challenge> findByStatusGreaterThanOrEqualTo(@Param("minStatus") Integer minStatus, @Param("rowIndex") int rowIndex, @Param("pageSize") int pageSize);

    Integer countByStatusGreaterThanOrEqualTo(@Param("minStatus") Integer minStatus);


    int updateStatusByChallengeIdAndUserId(@Param("updatedStatus")Integer updatedStatus,@Param("challengeId")Integer challengeId,@Param("userId")Integer userId);



}