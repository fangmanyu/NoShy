package xin.stxkfzx.noshy.service.impl;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.transaction.annotation.Transactional;
import xin.stxkfzx.noshy.domain.*;
import xin.stxkfzx.noshy.dto.PostDTO;
import xin.stxkfzx.noshy.exception.PostServiceException;
import xin.stxkfzx.noshy.mapper.BrowseInformationMapper;
import xin.stxkfzx.noshy.mapper.PostInformationMapper;
import xin.stxkfzx.noshy.mapper.PostMapper;
import xin.stxkfzx.noshy.service.PostService;
import xin.stxkfzx.noshy.service.UserService;
import xin.stxkfzx.noshy.util.PageCalculator;
import xin.stxkfzx.noshy.vo.UserVO;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author fmy
 * @date 2018-07-28 20:34
 */
public class PostServiceImpl implements PostService {
    private final PostMapper postMapper;
    private final PostInformationMapper postInformationMapper;
    private final BrowseInformationMapper browseInformationMapper;
    private static final Logger log = LogManager.getLogger(PostServiceImpl.class);

    @Autowired
    public PostServiceImpl(PostMapper postMapper, PostInformationMapper postInformationMapper, BrowseInformationMapper browseInformationMapper, UserService userService) {
        this.postMapper = postMapper;
        this.postInformationMapper = postInformationMapper;
        this.browseInformationMapper = browseInformationMapper;
    }


    @Transactional(rollbackFor = PostServiceException.class)
    @Override
    public PostDTO createPost(Post post) throws PostServiceException {
        // 构建浏览信息
        BrowseInformation browseInformation = new BrowseInformation();
        browseInformation.setBrowseType(BrowseInformation.POST);
        browseInformation.setLikes(0);
        browseInformation.setPageviews(0);
        browseInformation.setShares(0);
        try {
            browseInformationMapper.insertSelective(browseInformation);
        } catch (Exception e) {
            throw new PostServiceException("创建浏览信息失败: " + e.getMessage());
        }
        log.debug("构建帖子浏览信息Id: {}", browseInformation.getBrowseId());

        post.setStatus(Post.DISPLAY);
        post.setLastEditTime(new Date());
        post.setCreateTime(new Date());
        post.setPageView(0);
        post.setBrowseId(browseInformation.getBrowseId());
        try {
            log.debug("添加帖子信息: {}", post);
            postMapper.insertSelective(post);
        } catch (Exception e) {
            throw new PostServiceException("创建帖子失败: " + e.getMessage());
        }

        PostDTO dto = new PostDTO(true, "创建帖子成功");

        dto.setPostId(post.getPostId());
        return dto;
    }

    @Transactional(rollbackFor = PostServiceException.class)
    @Override
    public PostDTO removePost(int postId) throws PostServiceException {
        // 删除帖子中所有所有内容信息
        try {
            postInformationMapper.deleteByPostId(postId);
        } catch (Exception e) {
            throw new PostServiceException("删除帖子内容失败: " + e.getMessage());
        }

        // 删除帖子
        try {
            postMapper.deleteByPrimaryKey(postId);
        } catch (Exception e) {
            throw new PostServiceException("删除帖子失败: " + e.getMessage());
        }

        return new PostDTO(true, "删除帖子成功");

    }

    @Transactional(rollbackFor = PostServiceException.class)
    @Override
    public PostDTO addPageViewNum(int postId) throws PostServiceException {
        Post post = postMapper.selectByPrimaryKey(postId);

        Optional.ofNullable(post).orElseThrow(() -> new PostServiceException("帖子不存在"));

        post.setPageView(post.getPageView() + 1);

        try {
            postMapper.updateByPrimaryKeySelective(post);
        } catch (Exception e) {
            throw new PostServiceException("浏览量更新失败");
        }

        return new PostDTO(true, "操作成功");
    }

    @Override
    public PostDTO listBeforeInformation(int postId, Integer pageIndex, int pageSize, Integer infoId) {
        Post post = postMapper.selectByPrimaryKey(postId);
        if (post == null) {
            return new PostDTO(false, "无该论坛");
        }

        Integer rowIndex = Optional.ofNullable(pageIndex)
                .map(integer -> PageCalculator.calculateRowIndex(integer, pageSize)).orElse(null);

        log.debug("查询参数: rowIndex={}", rowIndex);
        List<PostInformation> postInformationList = postInformationMapper.selectPostInformationListDESCCreateTime(postId, rowIndex, pageSize, infoId);

        PostDTO postDTO = new PostDTO(true, "查询成功");
        postDTO.setPostInformationList(postInformationList);
        return postDTO;
    }

    @Transactional(rollbackFor = PostServiceException.class)
    @Override
    public PostDTO addPostInformation(PostInformation postInformation) throws PostServiceException {
        postInformation.setCreateTime(new Date());
        try {
            postInformationMapper.insert(postInformation);
        } catch (Exception e) {
            throw new PostServiceException("创建帖子内容失败: " + e.getMessage());
        }
        log.debug("插入消息Id: {}", postInformation.getInfoId());
        PostDTO postDTO = new PostDTO(true, "帖子添加消息成功");
        postDTO.setInfoId(postInformation.getInfoId());
        return postDTO;
    }

    @Transactional(rollbackFor = PostServiceException.class)
    @Override
    public PostDTO modifyPostStatus(int postId, int status) throws PostServiceException {
        // 如果状态为删除，则删除该帖子
        if (status == Post.DELETE) {
            removePost(postId);
        }

        Post post = new Post();
        post.setPostId(postId);
        post.setStatus(status);
        try {
            postMapper.updateByPrimaryKeySelective(post);
        } catch (Exception e) {
            throw new PostServiceException("更改帖子状态失败: " + e.getMessage());
        }

        return new PostDTO(true, "更改状态成功");
    }

    @Override
    public PostDTO getPost(int postId, int pageIndex, int pageSize) {
        // 不可被展示的帖子不能被获取到
        Integer postStatus = postMapper.queryPostStatus(postId);
        if (postStatus == null) {
            return new PostDTO(false, "获得帖子失败： 该帖子不存在");
        } else if (postStatus == Post.UN_DISPLAY) {
            return new PostDTO(false, "获得帖子失败： 该帖子状态不可见");
        }

        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<PostInformation> postInformationList = postInformationMapper.selectPostInformationList(postId, rowIndex, pageSize);
        int count = postInformationMapper.countByPostId(postId);
        Post post = postMapper.selectByPrimaryKey(postId);

        PostDTO postDTO = new PostDTO(true, "查询成功");
        postDTO.setPostInformationList(postInformationList);
        postDTO.setCount(count);
        postDTO.setPost(post);

        return postDTO;
    }


    @Override
    public PostDTO listPost(Post postCondition, int pageIndex, int pageSize) {
        int rowIndex = PageCalculator.calculateRowIndex(pageIndex, pageSize);
        List<Post> postList = postMapper.queryByPostCondition(postCondition, rowIndex, pageSize);
        int count = postMapper.countByPostCondition(postCondition);

        PostDTO postDTO = new PostDTO(true, "查询成功");
        postDTO.setCount(count);
        postDTO.setPostList(postList);

        return postDTO;
    }
}
