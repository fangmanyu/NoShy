package xin.stxkfzx.noshy.mapper;

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
}