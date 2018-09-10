package xin.stxkfzx.noshy.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.stereotype.Service;
import org.springframework.web.socket.*;
import xin.stxkfzx.noshy.bo.PostSocketInfoBO;
import xin.stxkfzx.noshy.bo.PostSocketUserInfo;
import xin.stxkfzx.noshy.domain.PostInformation;
import xin.stxkfzx.noshy.domain.User;
import xin.stxkfzx.noshy.dto.PostDTO;
import xin.stxkfzx.noshy.exception.PostServiceException;
import xin.stxkfzx.noshy.mapper.BrowseInformationMapper;
import xin.stxkfzx.noshy.mapper.PostInformationMapper;
import xin.stxkfzx.noshy.mapper.PostMapper;
import xin.stxkfzx.noshy.vo.JSONResponse;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Vector;

/**
 * @author fmy
 * @date 2018-09-09 13:55
 */
@Service(value = "postService")
public class PostSocketHandler extends PostServiceImpl implements WebSocketHandler {
    private static final Logger log = LogManager.getLogger(PostSocketHandler.class);
    private static final PostSocketInfoBO socketInfo;
    private static final ObjectMapper mapper = new ObjectMapper();

    static {
        socketInfo = new PostSocketInfoBO();
    }

    public PostSocketHandler(PostMapper postMapper, PostInformationMapper postInformationMapper, BrowseInformationMapper browseInformationMapper) {
        super(postMapper, postInformationMapper, browseInformationMapper);
    }

    @Override
    public void afterConnectionEstablished(WebSocketSession webSocketSession) throws Exception {
        log.info("建立socket连接");
        Map<String, Object> attributes = webSocketSession.getAttributes();
        User currentUser = (User) attributes.get("currentUser");
        log.debug("currentUser: {}", currentUser);
        String postId = (String) attributes.get("postId");
        log.debug("postId: {}", postId);
        Boolean isLogin = (Boolean) attributes.get("isLogin");
        log.debug("isLogin: {}", isLogin);

        Integer userId = isLogin ? currentUser.getUserId().intValue() : null;
        if (postId != null) {
            socketInfo.addUserInfo(new PostSocketUserInfo(userId, webSocketSession, postId));
            log.debug("postId: {}, userId: {} 加入socket中", postId, userId);
        }
    }

    @Override
    public void handleMessage(WebSocketSession webSocketSession, WebSocketMessage<?> webSocketMessage) throws Exception {
        log.info("socket 开始处理数据");
        Integer userId = getUserId(webSocketSession);
        String postId = getPostId(webSocketSession);

        // 读取消息
        Object payload = webSocketMessage.getPayload();
        log.debug("handleMessage  " + payload);
        JsonNode node = mapper.readTree((String) payload);
        String message = node.get("message").textValue();

        // 判断当前用户是否登录,没有登录返回错误
        JSONResponse jsonResponse = new JSONResponse();
        if (!isLogin(webSocketSession)) {
            log.debug("用户未登录");
            jsonResponse.setSuccess(false);
            jsonResponse.setMessage("用户未登录");
            String json = mapper.writeValueAsString(jsonResponse);
            webSocketSession.sendMessage(new TextMessage(json));
            return;
        }

        // 构建帖子消息,存到数据库中
        PostInformation information = new PostInformation();
        information.setUserId(userId);
        information.setInfoContent(message);
        information.setPostId(Integer.valueOf(postId));

        try {
            log.debug("信息存到数据库中");
            PostDTO postDTO = super.addPostInformation(information);

            Map<String, Object> data = new HashMap<>(4);
            data.put("userId", userId);
            data.put("postId", postId);
            data.put("message", message);
            jsonResponse.setSuccess(postDTO.getSuccess());
            jsonResponse.setMessage(postDTO.getMessage());
            jsonResponse.setData(data);

        } catch (PostServiceException e) {
            log.error(e.getMessage());
            jsonResponse.setMessage("系统内部错误");
            jsonResponse.setSuccess(false);
        }

        // 房间广播消息
        String json = mapper.writeValueAsString(jsonResponse);
        log.debug("socket 发送数据: {}", json);
        sendToPostRoom(postId, json);

        log.info("socket 处理数据结束");
    }

    @Override
    public void handleTransportError(WebSocketSession webSocketSession, Throwable throwable) throws Exception {
        log.error("socket 连接错误, 关闭Session会话");

        if (webSocketSession.isOpen()) {
            webSocketSession.close();
        }

        socketInfo.removeUserInfo(getPostId(webSocketSession), getUserId(webSocketSession));
    }

    @Override
    public void afterConnectionClosed(WebSocketSession webSocketSession, CloseStatus closeStatus) throws Exception {
        log.info("socket连接 结束");

        socketInfo.removeUserInfo(getPostId(webSocketSession), getUserId(webSocketSession));
    }

    @Override
    public boolean supportsPartialMessages() {
        return false;
    }

    private String getPostId(WebSocketSession webSocketSession) {
        String postId = (String) webSocketSession.getAttributes().get("postId");
        return postId;
    }

    private Boolean isLogin(WebSocketSession webSocketSession) {
        Boolean isLogin = (Boolean) webSocketSession.getAttributes().get("isLogin");
        return isLogin;
    }

    private Integer getUserId(WebSocketSession webSocketSession) {
        User currentUser = (User) webSocketSession.getAttributes().get("currentUser");
        Integer userId = isLogin(webSocketSession) ? currentUser.getUserId().intValue() : null;

        return userId;
    }

    private boolean sendToPostRoom(String postId, String msg) {
        boolean flag = true;

        if (postId == null) {
            return false;
        }

        Vector<PostSocketUserInfo> postUserList = socketInfo.getPostUserList(postId);
        TextMessage webSocketMessage = new TextMessage(msg);

        for (PostSocketUserInfo item : postUserList) {
            try {
                item.getWebSocketSession().sendMessage(webSocketMessage);
            } catch (IOException e) {
                flag = false;
                log.error("sendToPostRoom 发生错误: {}", e.getMessage());
            }
        }

        return flag;
    }
}
