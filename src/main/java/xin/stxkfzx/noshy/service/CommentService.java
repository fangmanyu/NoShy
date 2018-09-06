package xin.stxkfzx.noshy.service;

import xin.stxkfzx.noshy.domain.Comment;
import xin.stxkfzx.noshy.dto.CommentDTO;
import xin.stxkfzx.noshy.exception.CommentServiceException;

/**
 * 评论服务
 *
 * @author fmy
 * @date 2018-09-05 13:35
 */
public interface CommentService {

    /**
     * 添加评论
     *
     * @param comment
     * @return
     * @throws CommentServiceException
     * @author fmy
     * @date 2018-09-05 13:50
     */
    CommentDTO addComment(Comment comment) throws CommentServiceException;

    /**
     * 删除评论(包括子类评论)
     *
     * @param commentId
     * @return
     * @throws CommentServiceException
     * @author fmy
     * @date 2018-09-05 13:52
     */
    CommentDTO removerComment(int commentId) throws CommentServiceException;

    /**
     * 获取评论列表
     *
     * @param browseId
     * @return
     * @author fmy
     * @date 2018-09-05 13:53
     */
    CommentDTO listCommentByBrowseId(int browseId);

    /**
     * 获取评论
     *
     * @param commentId
     * @return
     * @author fmy
     * @date 2018-09-05 16:35
     */
    CommentDTO getComment(int commentId);
}
