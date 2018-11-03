package xin.stxkfzx.noshy.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.UserChallengeVideoLikes;

@Repository
public interface UserChallengeVideoLikesMapper {
    int deleteByPrimaryKey(@Param("userId") Integer userId, @Param("rankId") Integer rankId);

    int insert(UserChallengeVideoLikes record);

    int insertSelective(UserChallengeVideoLikes record);

    UserChallengeVideoLikes selectByPrimaryKey(@Param("userId") Integer userId, @Param("rankId") Integer rankId);

    int updateByPrimaryKeySelective(UserChallengeVideoLikes record);

    int updateByPrimaryKey(UserChallengeVideoLikes record);

}