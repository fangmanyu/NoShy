package xin.stxkfzx.noshy.mapper;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.UserFavoriteRecord;

@Repository
public interface UserFavoriteRecordMapper {
    int deleteByPrimaryKey(Integer favoriteRecordId);

    int insert(UserFavoriteRecord record);

    int insertSelective(UserFavoriteRecord record);

    UserFavoriteRecord selectByPrimaryKey(Integer favoriteRecordId);

    int updateByPrimaryKeySelective(UserFavoriteRecord record);

    int updateByPrimaryKey(UserFavoriteRecord record);
}