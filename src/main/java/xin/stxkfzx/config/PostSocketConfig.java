package xin.stxkfzx.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.socket.config.annotation.EnableWebSocket;
import org.springframework.web.socket.config.annotation.WebSocketConfigurer;
import org.springframework.web.socket.config.annotation.WebSocketHandlerRegistry;
import xin.stxkfzx.noshy.interceptor.PostSocketInterceptor;
import xin.stxkfzx.noshy.mapper.BrowseInformationMapper;
import xin.stxkfzx.noshy.mapper.PostInformationMapper;
import xin.stxkfzx.noshy.mapper.PostMapper;
import xin.stxkfzx.noshy.service.impl.PostSocketHandler;

/**
 * 论坛聊天Socket 配置
 *
 * @author fmy
 * @date 2018-09-09 15:21
 */
@Configuration
@EnableWebSocket
public class PostSocketConfig implements WebSocketConfigurer {
    @Autowired
    private PostMapper postMapper;
    @Autowired
    private PostInformationMapper postInformationMapper;
    @Autowired
    private BrowseInformationMapper browseInformationMapper;

    @Override
    public void registerWebSocketHandlers(WebSocketHandlerRegistry webSocketHandlerRegistry) {
        webSocketHandlerRegistry.addHandler(new PostSocketHandler(postMapper, postInformationMapper, browseInformationMapper), "/post/chat/{postId}")
                .setAllowedOrigins("*")
                .addInterceptors(new PostSocketInterceptor());
    }
}
