package xin.stxkfzx.noshy.mapper;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.Rank;

@Repository
public interface RankMapper {
    int deleteByPrimaryKey(Integer rankId);

    int insert(Rank record);

    int insertSelective(Rank record);

    Rank selectByPrimaryKey(Integer rankId);

    int updateByPrimaryKeySelective(Rank record);

    int updateByPrimaryKey(Rank record);

    List<Rank> findByChallengeIdAndType(@Param("challengeId")Integer challengeId,@Param("type")Integer type);

    Integer countByChallengeId(@Param("challengeId")Integer challengeId);


}