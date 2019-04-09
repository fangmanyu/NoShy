package xin.stxkfzx.noshy.interceptor;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xin.stxkfzx.noshy.util.UserUtils;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Optional;


/**
 * 用户登录拦截器
 *
 * @author fmy
 * @date 2019-03-05 17:00
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {
    private static final Logger log = LoggerFactory.getLogger(UserLoginInterceptor.class);

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("{} 进入拦截器", request.getRequestURI());
        fillUserInfo(request);

        return true;
    }

    private void fillUserInfo(HttpServletRequest request) {
        // 获取当前登录用户
        Optional.of(request).map(req -> req.getSession(false))
                .map(s -> s.getAttribute(UserUtils.KEY_USER))
                .ifPresent(o -> {
                    UserUtils.setUserId((Long) o);
                    log.debug("userId={} 添加到UserUtils", o);
                });

        // 获取lang
        String lang = getLocalValue(request);
        if (StringUtils.isNotEmpty(lang)) {
            log.debug("当前用户语言：{}", lang);
            UserUtils.setLocaleThreadLocal(lang);
        }
    }

    private String getLocalValue(HttpServletRequest request) {
        Cookie[] cookies = request.getCookies();
        for (int i = 0; cookies != null && i < cookies.length; i++) {
            if (UserUtils.KEY_LANG.equalsIgnoreCase(cookies[i].getName())) {
                return cookies[i].getValue();
            }
        }
        return null;
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserUtils.removeUserId();
    }
}
