package xin.stxkfzx.noshy.bo;

import org.apache.commons.lang3.ObjectUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.web.socket.WebSocketSession;

import java.util.Iterator;
import java.util.Objects;
import java.util.Vector;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

/**
 * 封装论坛发送消息对象
 *
 * @author fmy
 * @date 2018-09-09 14:15
 */
public class PostSocketInfoBO {
    private static final ConcurrentMap<String , Vector<PostSocketUserInfo>> onlineUserList = new ConcurrentHashMap<>(100);

    public void addUserInfo(PostSocketUserInfo userInfo) {
        Vector<PostSocketUserInfo> postSocketUserInfoList = getPostUserList(userInfo.getPostId());

        if (postSocketUserInfoList == null) {
            Vector<PostSocketUserInfo> postUserInfoList = new Vector<>(100);
            postUserInfoList.add(userInfo);

            onlineUserList.put(userInfo.getPostId(), postUserInfoList);
        } else {
            postSocketUserInfoList.add(userInfo);
        }
    }

    public Vector<PostSocketUserInfo> getPostUserList(String  postId) {
        return onlineUserList.get(postId);
    }

    public void removeUserInfo(String postId, Integer userId) {
        if (StringUtils.isEmpty(postId) || userId == null) {
            return;
        }

        Vector<PostSocketUserInfo> postUserList = getPostUserList(postId);
        if (postUserList != null) {
            Iterator<PostSocketUserInfo> iterator = postUserList.iterator();
            while (iterator.hasNext()) {
                Integer id = iterator.next().getUserId();
                if (Objects.equals(id, userId)) {
                    iterator.remove();
                }
            }
        }
    }

}
