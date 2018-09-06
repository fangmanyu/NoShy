package xin.stxkfzx.noshy.controller;

import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.stxkfzx.noshy.domain.Comment;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.dto.CommentDTO;
import xin.stxkfzx.noshy.exception.CommentServiceException;
import xin.stxkfzx.noshy.service.CommentService;
import xin.stxkfzx.noshy.vo.AddCommentVO;
import xin.stxkfzx.noshy.vo.JSONResponse;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import java.util.List;

/**
 * 评论接口
 *
 * @author fmy
 * @date 2018-09-05 16:31
 */
@Api(description = "评论相关API接口")
@Validated
@RestController
@RequestMapping("/comment")
public class CommentController {
    private static final Logger log = LogManager.getLogger(CommentController.class);
    private final CommentService commentService;
    private User currentUser;

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ModelAttribute
    public void getCurrentUser(HttpSession session) {
        this.currentUser = (User) session.getAttribute("currentUser");
        log.debug("当前登录用户信息: {}", currentUser);
    }

    @ApiOperation(value = "添加评论")
    @PostMapping
    public JSONResponse addComment(@RequestBody @Validated @ApiParam AddCommentVO commentInfo) {
        // FIXME: 2018/9/5 0005 解除注释
        // if (currentUser == null || currentUser.getUserId() == null) {
        //     return new JSONResponse(false, "用户尚未登录");
        // }

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentInfo, comment);
        // FIXME: 2018/9/5 0005 解除注释
        // comment.setUserId(currentUser.getUserId());
        log.debug("添加评论信息: {}", comment);

        try {
            CommentDTO dto = commentService.addComment(comment);
            return new JSONResponse(dto.getSuccess(), dto.getMessage());
        } catch (CommentServiceException e) {
            log.error(e.getMessage());
            return new JSONResponse(false, "系统错误");
        }
    }

    @ApiOperation(value = "获取全部评论信息")
    @ApiImplicitParam(name = "browseId", value = "浏览信息Id")
    @GetMapping("/{browseId}")
    public JSONResponse listCommentByBrowseId(@PathVariable @Min(0) int browseId) {
        CommentDTO commentDTO = commentService.listCommentByBrowseId(browseId);
        return new JSONResponse(commentDTO.getSuccess(), commentDTO.getMessage(), commentDTO.getCommentList());
    }

    @ApiOperation(value = "删除评论,包括子类评论")
    @ApiImplicitParam(name = "commentId", value = "评论Id")
    @DeleteMapping("/{commentId}")
    public JSONResponse removeComment(@PathVariable @Min(0) int commentId) {
        // FIXME: 2018/9/6 0006 解除注释
        // if (currentUser == null || currentUser.getUserId() == null) {
        //     return new JSONResponse(false, "用户尚未登录");
        // }

        try {
            CommentDTO commentDTO = commentService.removerComment(commentId);

            return new JSONResponse(commentDTO.getSuccess(), commentDTO.getMessage());
        } catch (CommentServiceException e) {
            log.error(e.getMessage());
            return new JSONResponse(false, e.getMessage());
        }
    }

    @ApiOperation(value = "获取指定评论,包括子类评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "browseId", value = "浏览信息Id"),
            @ApiImplicitParam(name = "commentId", value = "评论Id")
    })
    @GetMapping("/{browseId}/{commentId}")
    public JSONResponse getComment(@PathVariable @Min(0) int browseId,
                                   @PathVariable @Min(0) int commentId) {
        List<Comment> commentList = commentService.listCommentByBrowseId(browseId).getCommentList();
        if (commentList == null || commentList.size() == 0) {
            return new JSONResponse(false, "浏览信息Id错误");
        }

        CommentDTO comment = commentService.getComment(commentId);
        return new JSONResponse(comment.getSuccess(), comment.getMessage(), comment.getComment());
    }
}
