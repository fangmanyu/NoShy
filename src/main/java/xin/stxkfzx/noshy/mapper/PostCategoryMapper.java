package xin.stxkfzx.noshy.mapper;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.PostCategory;

/**
 * @author fmy
 */
@Repository
public interface PostCategoryMapper {
    int deleteByPrimaryKey(Integer categoryId);

    int insert(PostCategory record);

    int insertSelective(PostCategory record);

    PostCategory selectByPrimaryKey(Integer categoryId);

    int updateByPrimaryKeySelective(PostCategory record);

    int updateByPrimaryKey(PostCategory record);
}