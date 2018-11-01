package xin.stxkfzx.noshy.service;

import xin.stxkfzx.noshy.dto.BrowseDTO;
import xin.stxkfzx.noshy.exception.BrowseException;

/**
 * 浏览信息 服务
 *
 * @author fmy
 * @date 2018-11-01 13:48
 */
public interface BrowseService {
    /**
     * 更新浏览信息
     *
     * @param browseType 浏览类型
     * @param belongId 所属浏览类型Id(如:videoId, postId, challengeId)
     * @param typeName 更新类型名称("pageviews", "likes", "shares")
     * @return
     * @throws BrowseException
     * @author fmy
     * @date 2018-11-01 13:58
     */
    BrowseDTO updateBrowseInfo(int browseType, String belongId, String typeName) throws BrowseException;

}
