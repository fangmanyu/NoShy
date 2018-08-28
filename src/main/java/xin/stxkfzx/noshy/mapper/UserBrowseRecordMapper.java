package xin.stxkfzx.noshy.mapper;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.UserBrowseRecord;

@Repository
public interface UserBrowseRecordMapper {
    int deleteByPrimaryKey(Integer browseRecordId);

    int insert(UserBrowseRecord record);

    int insertSelective(UserBrowseRecord record);

    UserBrowseRecord selectByPrimaryKey(Integer browseRecordId);

    int updateByPrimaryKeySelective(UserBrowseRecord record);

    int updateByPrimaryKey(UserBrowseRecord record);
}