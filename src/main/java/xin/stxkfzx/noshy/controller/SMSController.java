package xin.stxkfzx.noshy.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import xin.stxkfzx.noshy.dto.SMSDTO;
import xin.stxkfzx.noshy.service.SMSService;
import xin.stxkfzx.noshy.vo.JsonResponse;

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

    @Autowired
    private SMSService smsService;

    @ApiOperation(value = "发送短信验证码", notes = "根据用户手机号码发送验证码")
    @ApiImplicitParam(name = "phone", value = "发送短信的手机号码", required = true)
    @GetMapping
    public JsonResponse sendSms(@RequestParam("phone") String phone) {
        SMSDTO baseDTO = smsService.sendSms(phone);
        return new JsonResponse(baseDTO.getSuccess(), baseDTO.getMessage());
    }
}
