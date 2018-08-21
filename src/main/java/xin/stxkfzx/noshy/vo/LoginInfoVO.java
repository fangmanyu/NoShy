package xin.stxkfzx.noshy.vo;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * 登录信息封装 VO
 *
 * @author fmy
 * @date 2018-08-04 12:30
 */
@ApiModel(description = "登录信息")
public class LoginInfoVO {
    @ApiModelProperty(value = "登录电话", required = true)
    private String phone;
    @ApiModelProperty(value = "登录密码", required = true)
    private String password;
    @ApiModelProperty(value = "验证码", required = true)
    private String verifyCodeActual;

    @Override
    public String toString() {
        return "LoginInfoVO{" +
                "phone='" + phone + '\'' +
                ", password='" + password + '\'' +
                ", verifyCodeActual='" + verifyCodeActual + '\'' +
                '}';
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getVerifyCodeActual() {
        return verifyCodeActual;
    }

    public void setVerifyCodeActual(String verifyCodeActual) {
        this.verifyCodeActual = verifyCodeActual;
    }
}
