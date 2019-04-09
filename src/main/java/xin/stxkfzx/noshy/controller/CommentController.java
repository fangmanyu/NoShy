package xin.stxkfzx.noshy.controller;

import io.swagger.annotations.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.stxkfzx.noshy.domain.Comment;
import xin.stxkfzx.noshy.dto.CommentDTO;
import xin.stxkfzx.noshy.exception.CommentServiceException;
import xin.stxkfzx.noshy.service.CommentService;
import xin.stxkfzx.noshy.util.UserUtils;
import xin.stxkfzx.noshy.vo.AddCommentVO;
import xin.stxkfzx.noshy.vo.JsonResponse;

import javax.validation.constraints.Min;

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

    @Autowired
    public CommentController(CommentService commentService) {
        this.commentService = commentService;
    }

    @ApiOperation(value = "添加评论")
    @PostMapping
    public JsonResponse addComment(@RequestBody @Validated @ApiParam AddCommentVO commentInfo) {
        Long currentUserId = UserUtils.getUserId();

        Comment comment = new Comment();
        BeanUtils.copyProperties(commentInfo, comment);
        comment.setUserId(currentUserId);
        log.debug("添加评论信息: {}", comment);

        try {
            CommentDTO dto = commentService.addComment(comment);
            return new JsonResponse(dto.getSuccess(), dto.getMessage());
        } catch (CommentServiceException e) {
            log.error(e.getMessage());
            return new JsonResponse(false, "系统错误");
        }
    }

    @ApiOperation(value = "获取全部评论信息")
    @ApiImplicitParam(name = "browseId", value = "浏览信息Id")
    @GetMapping()
    public JsonResponse listCommentByBrowseId(@RequestParam @Min(0) int browseId) {
        CommentDTO commentDTO = commentService.listCommentByBrowseId(browseId);
        return new JsonResponse(commentDTO.getSuccess(), commentDTO.getMessage(), commentDTO.getCommentList());
    }

    @ApiOperation(value = "删除评论,包括子类评论")
    @ApiImplicitParam(name = "commentId", value = "评论Id")
    @DeleteMapping("/{commentId}")
    public JsonResponse removeComment(@PathVariable @Min(0) int commentId) {
        Long currentUserId = UserUtils.getUserId();

        try {
            // TODO 判断是否有权限删除评论
            CommentDTO commentDTO = commentService.removerComment(commentId);

            return new JsonResponse(commentDTO.getSuccess(), commentDTO.getMessage());
        } catch (CommentServiceException e) {
            log.error(e.getMessage());
            return new JsonResponse(false, e.getMessage());
        }
    }

    @ApiOperation(value = "获取指定评论,包括子类评论")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "commentId", value = "评论Id")
    })
    @GetMapping("/{commentId}")
    public JsonResponse getComment(@PathVariable @Min(0) int commentId) {
        CommentDTO comment = commentService.getComment(commentId);
        return new JsonResponse(comment.getSuccess(), comment.getMessage(), comment.getComment());
    }
}
