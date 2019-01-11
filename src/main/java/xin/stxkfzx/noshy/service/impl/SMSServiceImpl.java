package xin.stxkfzx.noshy.service.impl;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.QuerySendDetailsResponse;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsRequest;
import com.aliyuncs.dysmsapi.model.v20170525.SendSmsResponse;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.profile.DefaultProfile;
import com.aliyuncs.profile.IClientProfile;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Service;
import xin.stxkfzx.noshy.dto.SMSDTO;
import xin.stxkfzx.noshy.service.SMSService;
import xin.stxkfzx.noshy.util.ApplicationContextUtil;
import xin.stxkfzx.noshy.util.CheckUtils;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import java.util.concurrent.TimeUnit;

/**
 * @author fmy
 * @date 2018-07-20 15:40
 */
@Service
public class SMSServiceImpl implements SMSService {
    private static final Logger log = LogManager.getLogger(SMSServiceImpl.class);

    @Value("${ali.accessKey.id}")
    private String accessKeyId;
    @Value("${ali.accessKye.secret}")
    private String accessKeySecret;
    private static final long EXPIRE_TIME = 180L;
    private static final String OK = "OK";
    private static final String ISV_BUSINESS_LIMIT_CONTROL = "isv.BUSINESS_LIMIT_CONTROL";

    private SendSmsResponse sendSmsDetail(String phone, String code) throws ClientException {
        if (!CheckUtils.checkPhone(phone)) {
            throw new IllegalArgumentException("电话号码格式错误");
        }

        if (code == null || code.length() != 4) {
            throw new IllegalArgumentException("验证码格式错误");
        }

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象-具体描述见控制台-文档部分内容
        SendSmsRequest request = new SendSmsRequest();
        //必填:待发送手机号
        request.setPhoneNumbers(phone);
        //必填:短信签名-可在短信控制台中找到
        request.setSignName("双体系开发中心");
        //必填:短信模板-可在短信控制台中找到
        request.setTemplateCode("SMS_138045041");
        //可选:模板中的变量替换JSON串,如模板内容为"您的验证码为${code}"时,此处的值为
        request.setTemplateParam("{\"code\":\"" + code + "\"}");

        //可选:outId 为提供给业务方扩展字段,最终在短信回执消息中将此值带回给调用者
        request.setOutId(code);

        //选填-上行短信扩展码(无特殊需求用户请忽略此字段)
        ///request.setSmsUpExtendCode("90997");

        return acsClient.getAcsResponse(request);

    }

    @SuppressWarnings("unchecked")
    @Override
    public SMSDTO sendSms(String phone) {
        // 生成校验码
        String code = new Random().nextInt(9000) + 1000 + "";
        log.debug("手机号：{}， 发送的验证码 {}", phone, code);

        SendSmsResponse sendSmsResponse = null;
        try {
            sendSmsResponse = sendSmsDetail(phone, code);
        } catch (ClientException e) {
            e.printStackTrace();
            log.error("阿里云短信接口调用失败：{}", e.getMessage());
        }

        // 发送成功
        if (sendSmsResponse.getCode() != null && sendSmsResponse.getCode().equals(OK)) {
            // 将手机验证码存到redis中，设置过期时间
            RedisTemplate redisTemplate = ApplicationContextUtil.getRedisTemplate();
            ValueOperations valueOperations = redisTemplate.opsForValue();
            valueOperations.set(phone, code, EXPIRE_TIME, TimeUnit.SECONDS);

            log.info("手机号：{} 发送短信成功", phone);
            return new SMSDTO(true, "成功发送");

        } else if (sendSmsResponse.getCode() != null
                && sendSmsResponse.getCode().equals(ISV_BUSINESS_LIMIT_CONTROL)) {
            // 业务限流。将短信发送频率限制在正常的业务流控范围内，默认流控：短信验证码 ：使用同一个签名，对同一个手机号码发送短信验证码，
            // 支持1条/分钟，5条/小时 ，累计10条/天。
            return new SMSDTO(false, "短信发送频率过快或达到限制");
        }

        return new SMSDTO(false, sendSmsResponse.getCode());
    }

    @Override
    public QuerySendDetailsResponse querySendDetails(String phone) throws ClientException {
        if (phone == null || "".equals(phone.trim())) {
            throw new ClientException("电话号码错误");
        }

        //可自助调整超时时间
        System.setProperty("sun.net.client.defaultConnectTimeout", "10000");
        System.setProperty("sun.net.client.defaultReadTimeout", "10000");

        //初始化acsClient,暂不支持region化
        IClientProfile profile = DefaultProfile.getProfile("cn-hangzhou", accessKeyId, accessKeySecret);
        DefaultProfile.addEndpoint("cn-hangzhou", "cn-hangzhou", PRODUCT, DOMAIN);
        IAcsClient acsClient = new DefaultAcsClient(profile);

        //组装请求对象
        QuerySendDetailsRequest request = new QuerySendDetailsRequest();
        //必填-号码
        request.setPhoneNumber(phone);
        //必填-发送日期 支持30天内记录查询，格式yyyyMMdd
        SimpleDateFormat ft = new SimpleDateFormat("yyyyMMdd");
        request.setSendDate(ft.format(new Date()));
        //必填-页大小
        request.setPageSize(10L);
        //必填-当前页码从1开始计数
        request.setCurrentPage(1L);
        //可选-流水号
        ///request.setBizId(bizId);

        return acsClient.getAcsResponse(request);
    }



}
