package xin.stxkfzx.noshy.mapper;
import java.util.List;
import org.apache.ibatis.annotations.Param;

import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.Comment;

@Repository
public interface CommentMapper {
    int deleteByPrimaryKey(Integer commentId);

    int insert(Comment record);

    int insertSelective(Comment record);

    Comment selectByPrimaryKey(Integer commentId);

    int updateByPrimaryKeySelective(Comment record);

    int updateByPrimaryKey(Comment record);

    int deleteByParentId(@Param("parentId")Integer parentId);

    List<Comment> findByParentId(@Param("parentId")Integer parentId);


    List<Comment> findByBrowseAndParentId(@Param("browseId")Integer browseId, @Param("parentId") Integer parentId);
    
    

}