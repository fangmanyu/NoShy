package xin.stxkfzx.noshy.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import xin.stxkfzx.noshy.domain.Post;
import xin.stxkfzx.noshy.domain.PostInformation;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.dto.PostDTO;
import xin.stxkfzx.noshy.service.PostService;
import xin.stxkfzx.noshy.vo.JSONResponse;
import xin.stxkfzx.noshy.vo.PostInformationVO;
import xin.stxkfzx.noshy.vo.UserVO;

import javax.servlet.http.HttpSession;
import javax.validation.constraints.Min;
import java.util.*;

/**
 * 论坛 Controller
 *
 * @author fmy
 * @date 2018-08-01 11:07
 */
@Api(description = "论坛服务相关API接口")
@RestController
@RequestMapping("/post")
@Validated
public class PostController {
    private static final Logger log = LogManager.getLogger(PostController.class);
    private final PostService postService;
    private User currentUser;

    @ModelAttribute
    public void initUser(HttpSession session) {
        Object currentUser = session.getAttribute("currentUser");
        log.debug("获取当前登录用户: " + currentUser);
        if (currentUser instanceof User) {
            this.currentUser = (User) currentUser;
        }
    }

    @ApiOperation(value = "更新帖子点击量")
    @ApiImplicitParam(name = "postId", value = "指定帖子Id", required = true, dataType = "int")
    @GetMapping("/addPageView/{postId}")
    public JSONResponse addPageView(@PathVariable @Min(1) int postId) {
        PostDTO postDTO = null;
        try {
            postDTO = postService.addPageViewNum(postId);
        return new JSONResponse(postDTO.getSuccess(), postDTO.getMessage());
        } catch (Exception e) {
            return new JSONResponse(false, "更新帖子点击量失败: " + e.getMessage());
        }
    }

    @ApiOperation(value = "获取剩余帖子内容")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "指定帖子Id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "loadSize", value = "加载数目", required = true, dataType = "int")
    })
    @GetMapping("/getAfterInfo")
    public JSONResponse getAfterInfo(@RequestParam("postId") @Min(1) int postId,
                                     @RequestParam("loadSize") @Min(0) int loadSize) {
        PostDTO afterPostInfo = postService.getAfterPostInfo(postId, loadSize);
        List<PostInformationVO> postInformationVOList = getPostInformationVOS(afterPostInfo);

        return new JSONResponse(afterPostInfo.getSuccess(), afterPostInfo.getMessage(), postInformationVOList);
    }

    private List<PostInformationVO> getPostInformationVOS(PostDTO afterPostInfo) {
        // DO -> VO
        List<PostInformationVO> postInformationVOList = new ArrayList<>(10);
        PostInformationVO vo = new PostInformationVO();
        UserVO userVO = new UserVO();
        for (PostInformation temp :
                afterPostInfo.getPostInformationList()) {

            BeanUtils.copyProperties(temp.getCreator(), userVO);
            BeanUtils.copyProperties(temp, vo);
            vo.setUser(userVO);

            postInformationVOList.add(vo);
            vo = new PostInformationVO();
        }
        return postInformationVOList;
    }

    @ApiOperation(value = "获取帖子全部内容信息")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postId", value = "指定帖子Id", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageIndex", value = "分页起始位置", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "int")
    })
    @GetMapping("/{postId}")
    public JSONResponse getPost(@PathVariable @Min(1) int postId,
                                @RequestParam(value = "pageIndex") @Min(0) int pageIndex,
                                @RequestParam("pageSize") @Min(1) int pageSize) {

        PostDTO post = postService.getPost(postId, pageIndex, pageSize);

        Map<String, Object> modelMap = new HashMap<>(3);
        modelMap.put("count", post.getCount());
        modelMap.put("currentUserId", Optional.ofNullable(currentUser).map(u -> u.getUserId()).orElse(-1L));
        modelMap.put("post", post.getPost());

        // DO -> VO
        List<PostInformationVO> postInformationVOList = getPostInformationVOS(post);
        modelMap.put("postInformationList", postInformationVOList);

        return new JSONResponse(post.getSuccess(), post.getMessage(), modelMap);
    }

    @ApiOperation(value = "根据条件获取帖子列表")
    @ApiImplicitParams({
            @ApiImplicitParam(name = "postConditionStr", value = "查询条件Post对应json字符串"),
            @ApiImplicitParam(name = "pageIndex", value = "分页起始位置", required = true, dataType = "int"),
            @ApiImplicitParam(name = "pageSize", value = "每页大小", required = true, dataType = "int")
    })
    @GetMapping
    public JSONResponse listPost(@RequestParam(value = "postConditionStr", required = false) String postConditionStr,
                                 @RequestParam(value = "pageIndex") @Min(0) int pageIndex,
                                 @RequestParam("pageSize") @Min(1) int pageSize) {
        PostDTO postDTO;
        if (StringUtils.isEmpty(postConditionStr)) {
            postDTO = postService.listPost(null, pageIndex, pageSize);
        } else {
            Post post = null;
            try {
                ObjectMapper mapper = new ObjectMapper();
                post = mapper.readValue(postConditionStr, Post.class);
                log.debug("解析的Post json对象为 {}", post);
            } catch (Exception e) {
                log.error("解析json错误: " + e.getMessage());
                return new JSONResponse(false, "系统内部错误: " + e.getMessage());
            }

            postDTO = postService.listPost(post, pageIndex, pageSize);
        }

        Map<String, Object> modelMap = new HashMap<>(3);
        modelMap.put("count", postDTO.getCount());
        modelMap.put("postList", postDTO.getPostList());

        return new JSONResponse(postDTO.getSuccess(), postDTO.getMessage(), modelMap);
    }

    @ApiOperation(value = "发送帖子消息")
    @PostMapping(value = "/addPostInfo", consumes = "application/json")
    public JSONResponse addPostInfo(@RequestBody @Validated @ApiParam(value = "帖子消息") PostInformation postInformation) {
        if (currentUser == null) {
            return new JSONResponse(false, "当前用户尚未登录");
        }

        log.debug("获取的帖子消息: " + postInformation);
        postInformation.setCreateTime(new Date());
        postInformation.setUserId(currentUser.getUserId().intValue());

        PostDTO postDTO = postService.addPostInformation(postInformation);

        return new JSONResponse(postDTO.getSuccess(), postDTO.getMessage());
    }

    @Autowired
    public PostController(PostService postService) {
        this.postService = postService;
    }
}
