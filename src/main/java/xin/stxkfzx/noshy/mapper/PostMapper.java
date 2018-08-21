package xin.stxkfzx.noshy.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.Post;

import java.util.List;

@Repository
public interface PostMapper {
    int deleteByPrimaryKey(Integer postId);

    int insert(Post record);

    int insertSelective(Post record);

    Post selectByPrimaryKey(Integer postId);

    int updateByPrimaryKeySelective(Post record);

    int updateByPrimaryKey(Post record);

    /**
     * 获取帖子的状态
     *
     * @param postId 帖子Id
     * @return 状态值. 如果该帖子不存在，则返回null
     * @author fmy
     * @date 2018-07-29 20:50
     */
    Integer queryPostStatus(int postId);

    /**
     * 分页查询帖子列表
     *
     * @param postCondition 查询条件:标题(模糊),权限,创建者,帖子分类,描述(模糊),状态(不可见状态可以通过指定状态查询获取,
     *                      其他条件下只能获取状态为可见及以上(status >= 1))
     * @param rowIndex  起始位置
     * @param pageSize  分页大小
     * @return  查询结果集
     * @author fmy
     * @date 2018-07-29 22:58
     */
    List<Post> queryByPostCondition(@Param("postCondition") Post postCondition,
                                    @Param("rowIndex") int rowIndex,
                                    @Param("pageSize") int pageSize);

    /**
     * 获取queryByPostCondition查询总数
     *
     * @param postCondition 查询条件
     * @return  查询总数
     * @author fmy
     * @date 2018-07-29 22:59
     */
    int countByPostCondition(@Param("postCondition") Post postCondition);

}