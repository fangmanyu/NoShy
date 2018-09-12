package xin.stxkfzx.noshy.interceptor;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServerHttpResponse;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.messaging.simp.SimpMessageHeaderAccessor;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.HandshakeInterceptor;
import org.springframework.web.socket.server.support.HttpSessionHandshakeInterceptor;
import xin.stxkfzx.noshy.domain.User;

import javax.servlet.http.HttpSession;
import java.util.Enumeration;
import java.util.Map;
import java.util.Optional;

/**
 * 论坛聊天拦截器
 *
 * @author fmy
 * @date 2018-09-09 15:04
 */
public class PostSocketInterceptor extends HttpSessionHandshakeInterceptor {
    private static final Logger log = LogManager.getLogger(PostSocketInterceptor.class);

    @Override
    public boolean beforeHandshake(ServerHttpRequest serverHttpRequest, ServerHttpResponse serverHttpResponse, WebSocketHandler webSocketHandler, Map<String, Object> map) throws Exception {
        super.beforeHandshake(serverHttpRequest, serverHttpResponse,webSocketHandler,map);

        log.info("进入webSocket 拦截器");
        if (serverHttpRequest instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest request = (ServletServerHttpRequest) serverHttpRequest;
            String postId = request.getURI().toString().split("postId=")[1];
            log.debug("进入论坛房间Id: {}", postId);

            User currentUser = (User) request.getServletRequest().getSession().getAttribute("currentUser");
            log.debug("当前登录用户: {}", currentUser);

            // map 是ConcurrentMap类,key-value 都不能为空
            map.put("currentUser", Optional.ofNullable(currentUser).orElse(new User()));
            map.put("isLogin", currentUser != null);
            map.put("postId", postId);

            String id = request.getServletRequest().getSession().getId();
            System.out.println("session id = " + id);
        }

        HttpSession session = getSession(serverHttpRequest);
        if (session != null) {
            User currentUser = (User) session.getAttribute("currentUser");
            log.debug("getSession ---> " + currentUser);

            Enumeration<String> attributeNames = session.getAttributeNames();
            while (attributeNames.hasMoreElements()) {
                String s = attributeNames.nextElement();
                System.out.println(s);
            }
        } else {
            log.debug("session is null");
        }


        return true;
    }


    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest)request;
            return serverRequest.getServletRequest().getSession(this.isCreateSession());
        } else {
            return null;
        }
    }

}
