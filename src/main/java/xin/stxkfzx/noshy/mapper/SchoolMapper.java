package xin.stxkfzx.noshy.mapper;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.School;

@Repository
public interface SchoolMapper {
    int deleteByPrimaryKey(Integer schoolId);

    int insert(School record);

    int insertSelective(School record);

    School selectByPrimaryKey(Integer schoolId);

    int updateByPrimaryKeySelective(School record);

    int updateByPrimaryKey(School record);
}