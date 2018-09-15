package xin.stxkfzx.noshy.mapper;

import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;
import xin.stxkfzx.noshy.domain.PostInformation;

import java.util.List;

/**
 * @author fmy
 */
@Repository
public interface PostInformationMapper {
    int deleteByPrimaryKey(Integer infoId);

    int insert(PostInformation record);

    int insertSelective(PostInformation record);

    PostInformation selectByPrimaryKey(Integer infoId);

    int updateByPrimaryKeySelective(PostInformation record);

    int updateByPrimaryKey(PostInformation record);

    int deleteByPostId(Integer postId);

    /**
     * 分页获取帖子内容
     *
     * @param postId   帖子Id
     * @param rowIndex 起始位置
     * @param pageSize 分页大小
     * @return 帖子内容列表
     * @author fmy
     * @date 2018-07-29 21:13
     */
    List<PostInformation> selectPostInformationList(@Param("postId") int postId,
                                                    @Param("rowIndex") int rowIndex,
                                                    @Param("pageSize") int pageSize);

    /**
     * 获取帖子内容总数
     *
     * @param postId 帖子Id
     * @return 帖子内容总数
     * @author fmy
     * @date 2018-07-29 21:32
     */
    int countByPostId(@Param("postId") int postId);

    List<PostInformation> selectPostInformationListDESCCreateTime(@Param("postId") int postId, @Param("rowIndex") Integer rowIndex, @Param("pageSize") int pageSize, @Param("infoId")Integer infoId);
}