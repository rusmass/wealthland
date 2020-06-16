package com.ascn.richlife.server.group;

import com.ascn.richlife.model.ConnectionInfo;
import com.ascn.richlife.server.message.NettyMessage;
import io.netty.channel.Channel;
import org.apache.log4j.Logger;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 连接信息管理
 * <p>
 * Created by zhangpengxiang on 17/6/1.
 *
 * @author zhangpengxiang
 */
public class ClientConnectionManager {

    /**
     * 日志
     */
    private static Logger logger = Logger.getLogger(ClientConnectionManager.class);

    /**
     * 保存所有玩家的连接信息
     */
    private static Map<String, ConnectionInfo> playerConnectionInfo = new ConcurrentHashMap<>();

    /**
     * 保存所有玩家的连接
     */
    private static Map<String, Channel> playerConnection = new ConcurrentHashMap<>();

    /**
     * 添加一个连接
     *
     * @param playerId
     * @param status
     */
    public static void addConnection(String playerId, int status, Channel channel) {

        if (!playerConnectionInfo.containsKey(playerId)) {
            ConnectionInfo connectionInfo = new ConnectionInfo(playerId, status, new Date());
            playerConnectionInfo.put(playerId, connectionInfo);
        }

        playerConnection.put(playerId, channel);
    }

    /**
     * 玩家的连接是否重复
     *
     * @param playerId
     * @return
     */
    public static boolean containsConnection(String playerId) {
        return playerConnection.containsKey(playerId);
    }

    /**
     * 玩家连接信息是否存在
     *
     * @param playerId
     * @return
     */
    public static boolean containsConnectionInfo(String playerId) {
        return playerConnectionInfo.containsKey(playerId);
    }

    /**
     * 获取玩家的连接
     *
     * @param playerId
     * @return
     */
    public static Channel getConnection(String playerId) {
        return playerConnection.get(playerId);
    }

    /**
     * 向指定的玩家发送消息
     *
     * @param playerId
     * @param message
     */
    public static void sendMessage(String playerId, NettyMessage message) {

        if (containsConnection(playerId)) {

            Channel channel = getConnection(playerId);

            if (channel.isActive()) {

                channel.writeAndFlush(message);

            } else {
                logger.error("玩家连接已经失效!");
            }
        } else {
            logger.error("没有这个玩家!"+playerId);
        }

    }

    /**
     * 向多玩家群发消息
     *
     * @param playerIds
     * @param message
     */
    public static void sendMessage(List<String> playerIds, NettyMessage message) {
        for (String playerId : playerIds) {
            sendMessage(playerId, message);
        }
    }

    /**
     * 向在线玩家发送信息
     *
     * @param message
     */
    public static void sendMessage(NettyMessage message) {

        for (Map.Entry<String, ConnectionInfo> entry : playerConnectionInfo.entrySet()) {
            sendMessage(entry.getKey(), message);
        }

    }

    /**
     * 获取连接的状态
     *
     * @param playerId
     * @return
     */
    public static int getConnectionStatus(String playerId) {
        return playerConnectionInfo.get(playerId).getStatus();
    }

    /**
     * 清除玩家的连接
     *
     * @param playerId
     */
    public static void removeConnection(String playerId) {
        playerConnection.remove(playerId);
        logger.debug("当前在线人数" + playerConnection.entrySet().size());
    }

    /**
     * 清除玩家的连接信息
     *
     * @param playerId
     */
    public static void removeConnectionInfo(String playerId) {
        playerConnectionInfo.remove(playerId);
    }

    /**
     * 获取房间号
     *
     * @param playerId
     * @return
     */
    public static String getRoomId(String playerId) {
        return playerConnectionInfo.get(playerId).getRoomId();
    }

    /**
     * 设置连接状态
     *
     * @param playerId
     * @param status
     */
    public static void updateStatus(String playerId, int status) {
        playerConnectionInfo.get(playerId).setStatus(status);
    }

    /**
     * 设置房间ID
     *
     * @param playerId
     * @param roomId
     */
    public static void updateRoomId(String playerId, String roomId) {
        playerConnectionInfo.get(playerId).setRoomId(roomId);
    }

    /**
     * 获取当前在线人数
     *
     * @return
     */
    public static int getOnlinePlayerNumber() {
        return playerConnection.entrySet().size();
    }

}
