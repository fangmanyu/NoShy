package xin.stxkfzx.noshy.util;

import com.google.code.kaptcha.Constants;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.servlet.http.HttpSession;
import java.util.regex.Pattern;

/**
 * 校验工具类
 *
 * @author fmy
 * @date 2018-07-21 23:58
 */
public class CheckUtils {
    private static final Logger log = LogManager.getLogger(CheckUtils.class);


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

        if (verifyCodeActual == null || !verifyCodeActual.equals(verifyCodeExpected)) {
            return false;
        }

        return true;
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

        if (phone == null || "".equals(phone.trim()) || !Pattern.matches(phoneNumberReg, phone)) {
            return false;
        }

        return true;
    }

    /**
     * 检验短信验证码
     *
     * @param code  需要检验的短信验证码
     * @param session   HTTPSession对象
     * @return  如果需要检验的短信验证码正确，返回true；否则返回false
     * @author fmy
     * @date 2018-07-22 23:43
     */
    public static boolean checkSmsCode(String code, HttpSession session) {
        String smsCode = (String) session.getAttribute("smsCode");
        return StringUtils.equals(code, smsCode);
    }

    private CheckUtils() {
    }

}
