package xin.stxkfzx.noshy.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.ModelAttribute;
import xin.stxkfzx.noshy.domain.Comment;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.dto.CommentDTO;
import xin.stxkfzx.noshy.exception.CommentServiceException;
import xin.stxkfzx.noshy.mapper.CommentMapper;
import xin.stxkfzx.noshy.service.CommentService;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author fmy
 * @date 2018-09-05 13:54
 */
@Service
public class CommentServiceImpl implements CommentService {
    private final CommentMapper commentMapper;
    private static final Logger log = LogManager.getLogger(CommentServiceImpl.class);


    @Autowired
    public CommentServiceImpl(CommentMapper commentMapper) {
        this.commentMapper = commentMapper;
    }

    @Transactional(rollbackFor = CommentServiceException.class)
    @Override
    public CommentDTO addComment(Comment comment) throws CommentServiceException {
        if (comment == null) {
            return new CommentDTO(false, "添加评论为空");
        }
        comment.setCommentLikes(0);

        try {
            commentMapper.insert(comment);
        } catch (Exception e) {
            log.error(e.getMessage());
            throw new CommentServiceException("添加评论异常: " + e.getMessage());
        }

        return new CommentDTO(true, "添加评论成功");
    }

    @Transactional(rollbackFor = CommentServiceException.class)
    @Override
    public CommentDTO removerComment(int commentId) throws CommentServiceException {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        if (comment == null) {
            return new CommentDTO(false, "删除评论错误: 无该评论");
        }

        // 删除子类评论
        List<Comment> childrenCommentList = commentMapper.findByParentId(commentId);
        try {
            // 递归删除子类评论
            recursionRemoveChildrenCommentList(childrenCommentList);
        } catch (Exception e) {
            throw new CommentServiceException("删除子类评论异常: " + e.getMessage());
        }

        // 删除该评论
        try {
            commentMapper.deleteByPrimaryKey(commentId);
        } catch (Exception e) {
            throw new CommentServiceException("删除评论异常: " + e.getMessage());
        }

        return new CommentDTO(true, "删除评论成功");
    }

    @Override
    public CommentDTO listCommentByBrowseId(int browseId) {
        // 获取一级评论列表
        List<Comment> commentList = commentMapper.findByBrowseAndParentId(browseId, null);
        // 递归获取子类评论列表
        recursionGetChildrenCommentList(commentList);


        return new CommentDTO(true, "查询成功", commentList);
    }

    @Override
    public CommentDTO getComment(int commentId) {
        Comment comment = commentMapper.selectByPrimaryKey(commentId);
        List<Comment> commentList = commentMapper.findByParentId(commentId);
        recursionGetChildrenCommentList(commentList);
        comment.setChildrenCommentList(commentList);

        return new CommentDTO(true, "查询成功", comment);
    }



    private void recursionGetChildrenCommentList(List<Comment> commentList) {
        if (commentList == null) {
            return;
        }

        for (Comment comment :
                commentList) {
            int commentId = comment.getCommentId();
            List<Comment> comments = commentMapper.findByParentId(commentId);

            recursionGetChildrenCommentList(comments);
            comment.setChildrenCommentList(comments);
        }
    }

    private void recursionRemoveChildrenCommentList(List<Comment> commentList) throws Exception {
        if (commentList == null) {
            return;
        }

        for (Comment comment :
                commentList) {
            int commentId = comment.getCommentId();
            List<Comment> comments = commentMapper.findByParentId(commentId);

            recursionRemoveChildrenCommentList(comments);

            commentMapper.deleteByPrimaryKey(commentId);
        }
    }
}
