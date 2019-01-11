package xin.stxkfzx.noshy.controller;

import io.swagger.annotations.*;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import springfox.documentation.annotations.ApiIgnore;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.exception.RegisterException;
import xin.stxkfzx.noshy.service.UserService;
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

    private final UserService userService;
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
            return new JSONResponse(false, "手机或密码格式错误");
        }

        User user = userService.login(loginInfo.getPhone(), loginInfo.getPassword());
        if (user != null) {
            session.setAttribute("currentUser", user);
        System.out.println("session id"+session.getId());
            return new JSONResponse(true, "登陆成功");
        }

        return new JSONResponse(false, "登陆失败，手机或密码错误");

    }

    @ApiOperation(value = "注册用户", produces = "application/json")
    @PostMapping("/register")
    public JSONResponse register(@RequestBody @ApiParam(name = "user", value = "用户对象") User user,
                                 @ApiParam(name = "smsCode", value = "手机验证码") @RequestParam("smsCode") String smsCode) {
        if (user == null || StringUtils.isAnyEmpty(user.getUserPhone(), user.getUserPassword())) {
            return new JSONResponse(false, "用户信息为空");
        }

        if (StringUtils.isEmpty(smsCode) || !CheckUtils.checkSmsCode(user.getUserPhone(), smsCode)) {
            return new JSONResponse(false, "手机验证码错误");
        }

        // 初始化信息
        user.setCreateTime(new Date());
        user.setUserStatus(1);

        try {
            userService.register(user);
            return new JSONResponse(true, "注册成功");
        } catch (RegisterException e) {
            return new JSONResponse(false, e.getMessage());
        }
    }

    @ApiOperation(value = "获取个人页面URL地址")
    @GetMapping("/profile")
    public String getProfileHtmlURL() {
        return "http://119.23.208.165/NoShy/html/myself/profile.html";
    }


    @GetMapping("/login")
    public ModelAndView getLoginHtml() {
        return new ModelAndView("html/login");
    }

    @ApiIgnore
    @GetMapping("/register")
    public ModelAndView getRegisterHtml() {
        return new ModelAndView("html/registered");
    }

    @Autowired
    public RegisterLoginController(UserService userService) {
        this.userService = userService;
    }
}
