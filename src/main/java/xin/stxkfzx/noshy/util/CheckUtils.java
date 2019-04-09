package xin.stxkfzx.noshy.util;

import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.MessageSource;
import org.springframework.data.redis.core.RedisTemplate;
import xin.stxkfzx.noshy.exception.CheckException;

import javax.servlet.http.HttpSession;
import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import java.util.Set;
import java.util.regex.Pattern;

/**
 * 校验工具类
 *
 * @author fmy
 * @date 2018-07-21 23:58
 */
public class CheckUtils {
    private static final Logger log = LogManager.getLogger(CheckUtils.class);
    private static MessageSource source;

    public static void setSource(MessageSource source) {
        CheckUtils.source = source;
    }

    /**
     * 检验条件是否成立
     *
     * @param condition 条件
     * @param msgKey 错误信息
     * @param args 错误参数
     * @author fmy
     * @date 2019-04-08 14:48
     */
    public static void check(boolean condition, String msgKey, Object... args) {
        if (!condition) {
            fail(msgKey, args);
        }
    }

    /**
     * 校验验证码
     *
     * @param verifyCodeActual 需要校验的验证码
     * @param session          HTTPSession对象
     * @return 如果需要校验的验证码正确，返回true；否则返回false
     * @author fmy
     * @date 2018-07-22 0:00
     */
    public static boolean checkVerifyCode(String verifyCodeActual, HttpSession session) {
        String verifyCodeExpected = (String) session.getAttribute(Constants.KAPTCHA_SESSION_KEY);

        log.debug("服务器验证码: {}, 客户端验证码: {}", verifyCodeExpected, verifyCodeActual);
        log.debug("校验sessionId = {}", session.getId());

        return verifyCodeActual != null && verifyCodeActual.equals(verifyCodeExpected);
    }


    /**
     * 校验手机号码的合法性(非null、非空、符合手机号格式)
     *
     * @param phone 需要校验的手机号码
     * @return 如果需要校验的手机号码合法，返回true；否则，返回false
     * @author fmy
     * @date 2018-07-22 0:08
     */
    public static boolean checkPhone(String phone) {
        // 校验手机号的正则表达式
        final String phoneNumberReg = "^(13[0-9]|14[579]|15[0-3,5-9]|16[6]|17[0135678]|18[0-9]|19[89])\\d{8}$";

        return phone != null && !"".equals(phone.trim()) && Pattern.matches(phoneNumberReg, phone);
    }

    /**
     * 检验短信验证码
     *
     * @param phone 手机号
     * @param code  需要检验的短信验证码
     * @return 如果需要检验的短信验证码正确，返回true；否则返回false
     * @author fmy
     * @date 2018-12-21 13:21
     */
    public static boolean checkSmsCode(String phone, String code) {
        RedisTemplate redisTemplate = ApplicationContextUtil.getRedisTemplate();
        String smsCode = (String) redisTemplate.opsForValue().get(phone);
        return StringUtils.equals(code, smsCode);
    }

    /**
     * 显式Bean校验
     *
     * @param bean
     * @return
     * @author fmy
     * @date 2018-09-16 21:18
     */
    public static <T> String checkBean(T bean) {
        // TODO: 2018/9/16 0016 抛出参数检查异常,由全局异常处理统一处理
        StringBuilder sb = new StringBuilder();
        Validator validator = Validation.buildDefaultValidatorFactory().getValidator();
        Set<ConstraintViolation<T>> violations = validator.validate(bean);
        for (ConstraintViolation<T> item :
                violations) {
            sb.append(item.getPropertyPath().toString()).append(item.getMessage()).append(";");
        }
        return sb.toString();
    }

    private static void fail(String msgKey, Object... args) {
        throw new CheckException(source.getMessage(msgKey, args, UserUtils.getLocaleThreadLocal()));
    }

    private CheckUtils() {
    }

}
