package xin.stxkfzx.noshy.vo;

/**
 * socket 响应消息
 *
 * @author fmy
 * @date 2018-09-15 17:05
 */
public class ResponseSocketMessage {
    private Integer postId;
    private String message;
    private Integer infoId;

    private Boolean myMessage;
    private UserVO userInfo;

    @Override
    public String toString() {
        return "ResponseSocketMessage{" +
                "postId=" + postId +
                ", message='" + message + '\'' +
                ", infoId=" + infoId +
                ", myMessage=" + myMessage +
                ", userInfo=" + userInfo +
                '}';
    }

    public Boolean getMyMessage() {
        return myMessage;
    }

    public void setMyMessage(Boolean myMessage) {
        this.myMessage = myMessage;
    }

    public Integer getPostId() {
        return postId;
    }

    public void setPostId(Integer postId) {
        this.postId = postId;
    }

    public UserVO getUserInfo() {
        return userInfo;
    }

    public void setUserInfo(UserVO userInfo) {
        this.userInfo = userInfo;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public Integer getInfoId() {
        return infoId;
    }

    public void setInfoId(Integer infoId) {
        this.infoId = infoId;
    }

}
