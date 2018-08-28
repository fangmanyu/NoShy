package xin.stxkfzx.noshy.mapper;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.UserCollectionRecord;

@Repository
public interface UserCollectionRecordMapper {
    int deleteByPrimaryKey(Integer collectionRecordId);

    int insert(UserCollectionRecord record);

    int insertSelective(UserCollectionRecord record);

    UserCollectionRecord selectByPrimaryKey(Integer collectionRecordId);

    int updateByPrimaryKeySelective(UserCollectionRecord record);

    int updateByPrimaryKey(UserCollectionRecord record);
}