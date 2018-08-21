package xin.stxkfzx.noshy.service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;

/**
 * 短信服务
 *
 * @author fmy
 * @date 2018-07-20 15:28
 */
public interface SMSService {
    // 产品名称:云通信短信API产品,开发者无需替换
    String PRODUCT = "Dysmsapi";

    //产品域名,开发者无需替换
    String DOMAIN = "dysmsapi.aliyuncs.com";

    String ACCESS_KEY_ID = "LTAIPQqlRW1AMVLJ";
    String ACCESS_KEY_SECRET = "nVFZyVGzpNzLkhJATLVtMRhOiugBvy";

    /**
     * 发送短信
     *
     * @param phone 电话号码
     * @param code  验证码
     * @return  发送响应
     * @exception ClientException
     * @author fmy
     * @date 2018-07-20 15:32
     */
    SendSmsResponse sendSms(String phone, String code) throws ClientException;

    /**
     * 短信明细查询
     *
     * @param phone
     * @return
     * @exception ClientException
     * @author fmy
     * @date 2018-07-20 15:39
     */
    QuerySendDetailsResponse querySendDetails(String phone) throws ClientException;

}
