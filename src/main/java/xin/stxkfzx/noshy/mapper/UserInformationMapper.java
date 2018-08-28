package xin.stxkfzx.noshy.mapper;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.UserInformation;

@Repository
public interface UserInformationMapper {
    int deleteByPrimaryKey(Long userId);

    int insert(UserInformation record);

    int insertSelective(UserInformation record);

    UserInformation selectByPrimaryKey(Long userId);

    int updateByPrimaryKeySelective(UserInformation record);

    int updateByPrimaryKey(UserInformation record);
}