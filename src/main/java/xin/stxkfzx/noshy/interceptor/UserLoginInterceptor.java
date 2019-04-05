package xin.stxkfzx.noshy.interceptor;

import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import xin.stxkfzx.noshy.util.UserUtils;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.Optional;


/**
 * 用户登录拦截器
 *
 * @author fmy
 * @date 2019-03-05 17:00
 */
@Component
public class UserLoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Optional.of(request).map(req -> req.getSession(false)).ifPresent(this::fillUserInfo);

        return true;
    }

    private void fillUserInfo(HttpSession session) {
        Optional.ofNullable(session)
                .map(s -> s.getAttribute(UserUtils.KEY_USER)).ifPresent(o -> UserUtils.setUser((Long) o));
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        UserUtils.removeUser();
    }
}
