package xin.stxkfzx.noshy.util;

import org.slf4j.MDC;
import xin.stxkfzx.noshy.exception.UnLoginException;


/**
 * 用户工具类
 *
 * @author fmy
 * @date 2019-04-05 15:15
 */
public class UserUtils {

    private static final ThreadLocal<Long> userThreadLocal = new ThreadLocal<>();

    public static final String KEY_USER = "currentUser";

    /**
     * 获取当前用户ID
     *
     * @return UserId 如果为空，则抛出<code>UnLoginException</code>异常
     * @author fmy
     * @date 2019-04-05 18:17
     */
    public static Long getUser() {
        Long userId = userThreadLocal.get();

        if (userId == null) {
            throw new UnLoginException("user is null");
        }

        return userId;
    }

    public static void setUser(Long userId) {
        userThreadLocal.set(userId);
        // 将用户信息放到日志
        MDC.put(KEY_USER, String.valueOf(userId));

    }

    public static void removeUser() {
        userThreadLocal.remove();
        MDC.remove(KEY_USER);
    }
}
