package xin.stxkfzx.noshy.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.MDC;
import xin.stxkfzx.noshy.exception.UnLoginException;

import java.util.Locale;


/**
 * 用户工具类
 *
 * @author fmy
 * @date 2019-04-05 15:15
 */
public class UserUtils {

    private static final ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();

    public static final String KEY_USER = "currentUser";
    public static final String KEY_LANG = "lang";
    private static final ThreadLocal<Locale> localeThreadLocal = ThreadLocal.withInitial(() -> Locale.ENGLISH);

    /**
     * 获取当前用户ID
     *
     * @return UserId 如果为空，则抛出<code>UnLoginException</code>异常
     * @author fmy
     * @date 2019-04-05 18:17
     */
    public static Long getUserId() {
        Long userId = userThreadLocal.get();

        if (userId == null) {
            throw new UnLoginException("currentUser is null");
        }

        return userId;
    }

    public static void setUserId(Long userId) {
        userThreadLocal.set(userId);
        // 将用户信息放到日志
        MDC.put(KEY_USER, String.valueOf(userId));

    }

    public static void removeUserId() {
        userThreadLocal.remove();
        MDC.remove(KEY_USER);
        localeThreadLocal.remove();

    }

    public static Locale getLocaleThreadLocal() {
        return localeThreadLocal.get();
    }

    public static void setLocaleThreadLocal(String lang) {
        if (StringUtils.isEmpty(lang)) {
            throw new IllegalArgumentException("lang is null");
        }

        localeThreadLocal.set(new Locale(lang));
    }
}
