package com.htf.bigdata.invest.platform.component.websocket;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.websocket.Session;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @author: amen
 * @Date: 2019/4/8 15:28
 * @Description:
 */
//@Component
public class WebSocketHelper {

    private final static Logger logger = LogManager.getLogger(WebSocketHelper.class);

    /**
     * 存活的session集合（使用线程安全的map保存）
     */
    public static Map<String, Session> livingSessions = new ConcurrentHashMap<>();
    /**
     * 存储session，通过user进行获取
     */
    public static Map<String, Map> userSessionMaps = new ConcurrentHashMap<>();

    /****
     * 添加在线用户与session绑定
     * @param userId
     * @param session
     */
    public static void add(String userId, Session session) {
        saveSessionId(userId,session);
        logger.info("online count = {}",livingSessions.size());

    }

    /*****
     * 删除离线用户
     * @param userId
     */
    public static void remove(String userId,Session session) {
        delSessionId(userId,session);
        logger.info("online count = {}",livingSessions.size());
    }

    /**
     * 存储userId sessionId
     * @param userId
     * @param session
     */
    private static void saveSessionId(String userId,Session session) {
        String userIdKey = userId;
        String sessionKey = session.getId();
        livingSessions.put(sessionKey,session);
        if (userSessionMaps.get(userIdKey) == null) {
            Map<String, Session> userSessionMap = new ConcurrentHashMap<>(25);
            userSessionMap.put(sessionKey,session);
            userSessionMaps.put(userIdKey,userSessionMap);
        }else{
            //sessionId 存在
            userSessionMaps.get(userIdKey).put(sessionKey,session);
        }
    }

    /**
     * 删除离线的 session
     * @param userId
     * @param session
     */
    private static void delSessionId(String userId,Session session) {
        String userIdKey = userId;
        String sessionKey = session.getId();
        livingSessions.remove(sessionKey);
        if (StringUtils.isEmpty(userIdKey)){
            return;
        }
        if (userSessionMaps.get(userIdKey) == null) {
            return;
        }
        userSessionMaps.get(userIdKey).remove(sessionKey);
        if (userSessionMaps.get(userIdKey).size() == 0) {
            userSessionMaps.remove(userIdKey);
        }
    }
}
