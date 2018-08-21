package xin.stxkfzx.noshy.vo;

/**
 * 用户信息 VO
 *
 * @author fmy
 * @date 2018-08-02 10:49
 */
public class UserVO {
    private Long userId;
    private String userName;

    @Override
    public String toString() {
        return "UserVO{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                '}';
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
