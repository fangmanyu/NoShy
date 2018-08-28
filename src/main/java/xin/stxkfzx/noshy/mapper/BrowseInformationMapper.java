package xin.stxkfzx.noshy.mapper;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.BrowseInformation;

@Repository
public interface BrowseInformationMapper {
    int deleteByPrimaryKey(Integer browseId);

    int insert(BrowseInformation record);

    int insertSelective(BrowseInformation record);

    BrowseInformation selectByPrimaryKey(Integer browseId);

    int updateByPrimaryKeySelective(BrowseInformation record);

    int updateByPrimaryKey(BrowseInformation record);
}