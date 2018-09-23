package xin.stxkfzx.noshy.mapper;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.Image;

@Repository
public interface ImageMapper {
    int deleteByPrimaryKey(Integer imageId);

    int insert(Image record);

    int insertSelective(Image record);

    Image selectByPrimaryKey(Integer imageId);

    int updateByPrimaryKeySelective(Image record);

    int updateByPrimaryKey(Image record);

    int deleteByTypeAndBelongId(@Param("type")Integer type,@Param("belongId")String belongId);

    Image findOneByTypeAndBelongId(@Param("type")Integer type,@Param("belongId")String belongId);


}