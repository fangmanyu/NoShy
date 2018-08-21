package xin.stxkfzx.noshy.controller;

import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.stxkfzx.noshy.service.SMSService;
import xin.stxkfzx.noshy.util.CheckUtils;
import xin.stxkfzx.noshy.vo.JSONResponse;

import javax.servlet.http.HttpSession;
import java.util.Random;

/**
 * 发送短信
 *
 * @author fmy
 * @date 2018-07-20 13:22
 */
@RestController
@RequestMapping(value = "/sendSms")
@Api(description = "短信接口API")
public class SMSController {

    private static final String ISV_BUSINESS_LIMIT_CONTROL = "isv.BUSINESS_LIMIT_CONTROL";
    private static final String OK = "OK";
    private static final Logger log = LogManager.getLogger(SMSController.class);


    @Autowired
    private SMSService smsService;

    @ApiOperation(value = "发送短信验证码", notes = "根据用户手机号码发送验证码")
    @ApiImplicitParam(name = "phone", value = "发送短信的手机号码", required = true)
    @GetMapping
    public JSONResponse sendSms(@RequestParam("phone") String phone,
                                HttpSession session) {

        // 手机号码后台校验
        if (!CheckUtils.checkPhone(phone)) {
            return new JSONResponse(false, "手机号后台校验失败");
        }

        try {
            // 生成校验码
            String code = new Random().nextInt(9999) + 1000 + "";
            log.debug("手机号：{}， 发送的验证码 {}", phone, code);
            // 发送短信并获取发送状态响应
            SendSmsResponse sendSmsResponse = smsService.sendSms(phone, code);

            JSONResponse jsonResponse = new JSONResponse();

            // 发送成功
            if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals(OK)) {
                // 将手机校验码存到session中
                session.setAttribute("smsCode", code);

                jsonResponse.setSuccess(true);
                jsonResponse.setMessage("成功发送");
                log.debug("手机号：{} 发送短信成功", phone);

            } else if (sendSmsResponse.getCode() != null
                    && sendSmsResponse.getCode().equals(ISV_BUSINESS_LIMIT_CONTROL)) {
                // 业务限流。将短信发送频率限制在正常的业务流控范围内，默认流控：短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，
                // 支持1条/分钟，5条/小时 ，累计10条/天。
                jsonResponse.setSuccess(false);
                jsonResponse.setMessage("短信发送频率过快或达到限制(对同一个手机号码发送短信验证码，1条/分钟，5条/小时 ，累计10条/天。)");
            }

            return jsonResponse;
        } catch (ClientException e) {
            return new JSONResponse(false, e.getMessage());
        }
    }
}
