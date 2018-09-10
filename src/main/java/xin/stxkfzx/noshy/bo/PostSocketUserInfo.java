package xin.stxkfzx.noshy.bo;

import org.springframework.web.socket.WebSocketSession;

/**
 * 封装socket 用户信息
 *
 * @author fmy
 * @date 2018-09-10 11:01
 */
public class PostSocketUserInfo {

    private Integer userId;
    private WebSocketSession webSocketSession;
    private String  postId;

    public PostSocketUserInfo(Integer userId, WebSocketSession webSocketSession, String postId) {
        this.userId = userId;
        this.webSocketSession = webSocketSession;
        this.postId = postId;
    }

    @Override
    public String toString() {
        return "PostSocketUserInfo{" +
                "userId=" + userId +
                ", webSocketSession=" + webSocketSession +
                ", postId=" + postId +
                '}';
    }

    public String getPostId() {
        return postId;
    }

    public void setPostId(String postId) {
        this.postId = postId;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public WebSocketSession getWebSocketSession() {
        return webSocketSession;
    }

    public void setWebSocketSession(WebSocketSession webSocketSession) {
        this.webSocketSession = webSocketSession;
    }
}
