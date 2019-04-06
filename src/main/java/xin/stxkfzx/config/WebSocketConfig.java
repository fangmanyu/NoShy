package xin.stxkfzx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import xin.stxkfzx.noshy.interceptor.PostSocketInterceptor;
import xin.stxkfzx.noshy.socket.PostSocketHandler;

/**
 * web socket配置
 *
 * @author fmy
 * @date 2018-09-09 15:21
 */
@Configuration
@EnableWebSocket
public class WebSocketConfig implements WebSocketConfigurer {
    private final PostSocketInterceptor postSocketInterceptor;
    private final PostSocketHandler handler;

    @Autowired
    public WebSocketConfig(PostSocketInterceptor postSocketInterceptor, PostSocketHandler handler) {
        this.postSocketInterceptor = postSocketInterceptor;
        this.handler = handler;
    }

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(handler, "/post/chat/{postId}")
                .setAllowedOrigins("*")
                .addInterceptors(postSocketInterceptor);
    }
}
