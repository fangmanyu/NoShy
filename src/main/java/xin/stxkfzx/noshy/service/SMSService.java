package xin.stxkfzx.noshy.service;

import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.exceptions.ClientException;
import xin.stxkfzx.noshy.dto.SMSDTO;

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

    /**
     * 发送短信
     *
     * @param phone 电话号码
     * @return  发送响应
     * @author fmy
     * @date 2018-07-20 15:32
     */
    SMSDTO sendSms(String phone);

    /**
     * 短信明细查询
     *
     * @param phone 手机号
     * @return 查询细节参数
     * @exception ClientException 阿里云客户端异常
     * @author fmy
     * @date 2018-07-20 15:39
     */
    QuerySendDetailsResponse querySendDetails(String phone) throws ClientException;

}
