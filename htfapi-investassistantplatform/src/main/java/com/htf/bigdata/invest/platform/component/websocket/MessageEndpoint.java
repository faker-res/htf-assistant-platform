package com.htf.bigdata.invest.platform.component.websocket;

import com.alibaba.fastjson.JSON;
import com.htf.bigdata.invest.platform.model.response.Response;
import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.web.socket.server.standard.ServerEndpointExporter;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Map;

/**
 * websocket订阅
 * @author: amen
 * @Date: 2019/4/8 15:25
 * @Description:
 */
//@ServerEndpoint("/message/{userId}/{token}")
@ServerEndpoint("/ws/message/{userId}")
@Component
public class MessageEndpoint {

    private final static Logger logger = LogManager.getLogger(MessageEndpoint.class);

    @Bean
    public ServerEndpointExporter serverEndpointExporter() {
        return new ServerEndpointExporter();
    }

//    public static MessageEndpoint socketServerEndpoint;
//
//    @PostConstruct
//    public void init() {
//        socketServerEndpoint = this;
//    }

    /**
     * 建立连接的回调方法
     *
     * @param session       与客户端的WebSocket连接会话
     * @param userId 用户userid
     * @param token 用户token
     */
    @OnOpen
//    public void onOpen(Session session, @PathParam("userId") String userId,
//            @PathParam("token")  String token) {
    public void onOpen(Session session, @PathParam("userId") String userId) {
        try {
            WebSocketHelper.add(userId,session);
            logger.info(" socket user connect {} , count = {}",userId, WebSocketHelper.livingSessions.size());
            sendMessage(session,userId);
        }catch (Throwable e){
            logger.info(" socket connect error user:{} , count = {}",userId,session, WebSocketHelper.livingSessions.size());
            logger.error("e"+e.getMessage());
        }
    }

    /**
     * 收到客户端消息的回调方法
     *
     * @param message 客户端传过来的消息
     * @param session 对应的session
     */
    @OnMessage
    public void onMessage(String message, Session session) throws IOException {
        logger.info("收到客户端{}消息{}",session.getId(),message);
            sendMessage(session, "收到客户端"+session.getId()+"消息"+message);
    }

    /**
     * 给人发送消息
     * @param userId
     * @param message
     */
    public void sendMessage(String userId, String message) {
        if (StringUtils.isEmpty(userId) || StringUtils.isEmpty(message)){
            return;
        }
        Map<String, Session> sessions = WebSocketHelper.userSessionMaps.get(userId);
        if (sessions == null){
            return;
        }
        sessions.forEach((sessionId, session) -> {
            sendMessage(session, message);
        });
    }

    /**
     * 给所有在线用户发送消息
     *
     * @param message
     */
    public void sendMessageToAll(String message) {
        if (StringUtils.isEmpty(message)){
            return;
        }
        WebSocketHelper.livingSessions.forEach((sessionId, session) -> {
            sendMessage(session, message);
        });
    }

    /**
     * 单独发送消息
     *
     * @param session
     * @param message
     */
    private void sendMessage(Session session, String message) {
        try {
            String objectBody = getResponse(200,"",message);
            logger.info("send message {}, To Session = {}",objectBody,session);
            session.getBasicRemote().sendText(objectBody);
        } catch (IOException e) {
            logger.info("send message error: {}",e.getMessage());
            e.printStackTrace();
        }
    }

    /**
     * 返回消息的结构体 方法
     * @param code
     * @param message
     * @param data
     * @return
     */
    private String getResponse(int code,String message, Object data) {
        Response responseMessage = new Response();
        responseMessage.setCode(code);
        responseMessage.setMessage(message);
        responseMessage.setData(data);

        String str = JSON.toJSONString(responseMessage);
        return str;
    }

    /**
     * 发生错误的回调方法
     *
     * @param session
     * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        logger.info("socket 发生错误 error:{}", error.toString());
        WebSocketHelper.remove(null,session);
    }

    /**
     * 关闭连接的回调方法
     */
    @OnClose
    public void onClose(Session session, @PathParam("userId") String userId) {
        logger.info("Socket...连接关闭.session = {},count = {}",session, WebSocketHelper.livingSessions.size());
        WebSocketHelper.remove(userId,session);
    }


}
