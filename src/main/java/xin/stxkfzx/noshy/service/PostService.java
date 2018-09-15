package xin.stxkfzx.noshy.service;

import xin.stxkfzx.noshy.domain.Post;
import xin.stxkfzx.noshy.domain.PostInformation;
import xin.stxkfzx.noshy.dto.PostDTO;
import xin.stxkfzx.noshy.exception.PostServiceException;

/**
 * 论坛服务
 *
 * @author fmy
 * @date 2018-07-28 15:11
 */
public interface PostService {

    /**
     * 创建帖子,并构建浏览信息
     *
     * @param post 帖子信息
     * @return 返回结果标识
     * @throws PostServiceException 论坛服务异常信息
     * @author fmy
     * @date 2018-07-28 18:24
     */
    PostDTO createPost(Post post) throws PostServiceException;

    /**
     * 删除帖子
     *
     * @param postId 帖子Id
     * @return 返回结果标识
     * @throws PostServiceException 论坛服务异常信息
     * @author fmy
     * @date 2018-07-28 19:20
     */
    PostDTO removePost(int postId) throws PostServiceException;

    /**
     * 添加帖子内容
     *
     * @param postInformation 帖子内容
     * @return 返回结果标识
     * @throws PostServiceException 论坛服务异常信息
     * @author fmy
     * @date 2018-07-28 19:26
     */
    PostDTO addPostInformation(PostInformation postInformation) throws PostServiceException;

    /**
     * 更改帖子状态
     *
     * @param postId 帖子Id
     * @param status 更改的状态
     * @return 返回结果标识
     * @throws PostServiceException 论坛服务异常信息
     * @author fmy
     * @date 2018-07-28 19:28
     */
    PostDTO modifyPostStatus(int postId, int status) throws PostServiceException;

    /**
     * 分页获取具体帖子信息
     *
     * @param postId    帖子Id
     * @param pageIndex 起始位置
     * @param pageSize  每页大小
     * @return 返回结果标识
     * @author fmy
     * @date 2018-07-28 19:37
     */
    PostDTO getPost(int postId, int pageIndex, int pageSize);

    /**
     * 获取全部帖子信息(不能获取到具体内容信息)
     *
     * @param postCondition 查询条件.如果要查询全部列表,则postCondition为null.
     * @param pageIndex     起始位置
     * @param pageSize      每页大小
     * @return 返回结果标识
     * @author fmy
     * @date 2018-07-28 19:39
     */
    PostDTO listPost(Post postCondition, int pageIndex, int pageSize);

    /**
     * 增加点击量
     *
     * @param postId 帖子Id
     * @return 返回结果标识
     * @throws PostServiceException 论坛服务异常信息
     * @author fmy
     * @date 2018-08-05 17:46
     */
    PostDTO addPageViewNum(int postId) throws PostServiceException;

    /**
     * 通过postId获取之前消息
     *
     * @param postId
     * @param pageIndex pageIndex可以为空
     * @param pageSize
     * @param infoId
     * @return
     * @author fmy
     * @date 2018-09-14 17:13
     */
    PostDTO listBeforeInformation(int postId, Integer pageIndex, int pageSize, Integer infoId);
}
