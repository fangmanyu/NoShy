package xin.stxkfzx.noshy.domain;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.Date;

/**
 * 用户 实体类
 *
 * @author fmy
 * @date 2018-07-20 23:55
 */
@ApiModel(description = "用户实体类")
public class User {
    @ApiModelProperty(hidden = true)
    private Long userId;

    @ApiModelProperty(name = "userName", value = "用户姓名", example = "张三")
    private String userName;

    @ApiModelProperty(name = "userPassword", value = "用户登录密码", required = true, example = "123456", dataType = "string")
    private String userPassword;

    @ApiModelProperty(name = "userPhone", value = "用户电话号码", required = true, example = "15346385475", dataType = "string")
    private String userPhone;

    @ApiModelProperty(hidden = true)
    private Integer userStatus;

    @ApiModelProperty(hidden = true)
    private Date createTime;

    @ApiModelProperty(name = "schoolId", value = "所属学校的Id", required = true, example = "1")
    private Long schoolId;


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
        this.userName = userName == null ? null : userName.trim();
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword == null ? null : userPassword.trim();
    }

    public String getUserPhone() {
        return userPhone;
    }

    public void setUserPhone(String userPhone) {
        this.userPhone = userPhone == null ? null : userPhone.trim();
    }

    public Integer getUserStatus() {
        return userStatus;
    }

    public void setUserStatus(Integer userStatus) {
        this.userStatus = userStatus;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Long getSchoolId() {
        return schoolId;
    }

    public void setSchoolId(Long schoolId) {
        this.schoolId = schoolId;
    }

    @Override
    public String toString() {
        return "User{" +
                "userId=" + userId +
                ", userName='" + userName + '\'' +
                ", userPassword='" + userPassword + '\'' +
                ", userPhone='" + userPhone + '\'' +
                ", userStatus=" + userStatus +
                ", createTime=" + createTime +
                ", schoolId=" + schoolId +
                '}';
    }
}