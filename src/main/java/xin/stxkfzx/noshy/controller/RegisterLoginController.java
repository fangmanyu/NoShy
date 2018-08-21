package xin.stxkfzx.noshy.controller;

import com.google.code.kaptcha.Constants;
import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.exception.RegisterException;
import xin.stxkfzx.noshy.service.RegisterLoginService;
import xin.stxkfzx.noshy.util.CheckUtils;
import xin.stxkfzx.noshy.vo.JSONResponse;
import xin.stxkfzx.noshy.vo.LoginInfoVO;

import javax.servlet.http.HttpSession;
import java.util.Date;

/**
 * 注册登录 Controller
 *
 * @author fmy
 * @date 2018-07-21 23:49
 */
@Api(description = "登录注册接口API")
@RestController
public class RegisterLoginController {

    private final RegisterLoginService registerLoginService;
    private static final Logger log = LogManager.getLogger(RegisterLoginController.class);

    @ApiOperation(value = "用户登录", notes = "根据用户的电话号码和密码进行登录")
    @PostMapping("/login")
    public JSONResponse login(@RequestBody @ApiParam LoginInfoVO loginInfo,
                              HttpSession session) {
        log.debug("登录信息: {}", loginInfo);

        if (!CheckUtils.checkVerifyCode(loginInfo.getVerifyCodeActual(), session)) {
            return new JSONResponse(false, "验证码错误");
        }

        if (!CheckUtils.checkPhone(loginInfo.getPhone()) || StringUtils.isEmpty(loginInfo.getPassword())) {
            return new JSONResponse(false, "手机或密码错误");
        }

        User user = registerLoginService.login(loginInfo.getPhone(), loginInfo.getPassword());
        if (user != null) {
            session.setAttribute("currentUser", user);
            return new JSONResponse(true, "登陆成功");
        }

        return new JSONResponse(false, "登陆失败，服务器内部错误");

    }

    @ApiOperation(value = "注册用户", produces = "application/json")
    @PostMapping("/register")
    public JSONResponse register(@RequestBody @ApiParam(name = "user", value = "用户对象") User user,
                                 @ApiParam(name = "smsCode", value = "手机验证码") @RequestParam("smsCode") String smsCode,
                                 HttpSession session) {
        if (user == null || StringUtils.isAnyEmpty(user.getUserPhone(), user.getUserPassword())) {
            return new JSONResponse(false, "用户信息为空");
        }

        if (StringUtils.isEmpty(smsCode) || !CheckUtils.checkSmsCode(smsCode, session)) {
            return new JSONResponse(false, "手机验证码错误");
        }

        // 初始化信息
        user.setCreateTime(new Date());
        user.setUserStatus(1);

        try {
            registerLoginService.register(user);
            return new JSONResponse(true, "注册成功");
        } catch (RegisterException e) {
            return new JSONResponse(false, e.getMessage());
        }
    }


    @Autowired
    public RegisterLoginController(RegisterLoginService registerLoginService) {
        this.registerLoginService = registerLoginService;
    }
}
