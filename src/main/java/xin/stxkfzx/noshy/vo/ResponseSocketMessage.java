package xin.stxkfzx.noshy.vo;

/**
 * socket 响应消息
 *
 * @author fmy
 * @date 2018-09-15 17:05
 */
public class ResponseSocketMessage {
    private Integer postId;
    private Integer userId;
    private String message;
    private Integer infoId;
    private String imageAddr;
    private Boolean myMessage;

    @Override
    public String toString() {
        return "ResponseSocketMessage{" +
                "postId=" + postId +
                ", userId=" + userId +
                ", message='" + message + '\'' +
                ", infoId=" + infoId +
                ", imageAddr='" + imageAddr + '\'' +
                ", myMessage=" + myMessage +
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

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
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

    public String getImageAddr() {
        return imageAddr;
    }

    public void setImageAddr(String imageAddr) {
        this.imageAddr = imageAddr;
    }
}
