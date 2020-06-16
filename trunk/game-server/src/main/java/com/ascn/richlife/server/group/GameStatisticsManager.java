package com.ascn.richlife.server.group;

import com.ascn.richlife.model.role.RoleOperateNum;
import org.apache.log4j.Logger;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 游戏统计管理
 *
 * Created by Administrator on 2018/1/23 0023.
 */
public class GameStatisticsManager {

    private static Logger logger = Logger.getLogger(GameStatisticsManager.class);

    //存储游戏开始时间点
    private static Map<String, Long> gameStatisticsTime = new ConcurrentHashMap<>();

    //玩家当前游戏中操作的卡牌数
    private static Map<String,RoleOperateNum> roleOperateNumMap = new ConcurrentHashMap<>();

    /**
     * 移除缓存的开始时间点
     * @param roomId
     */
    public static void removeTime(String roomId){
        gameStatisticsTime.remove(roomId);
        logger.debug(roomId+"移除该房间缓存时间节点");
    }

    /**
     * 移除玩家的操作记录
     * @param roomId
     */
    public static void removeRoleOperateNum(String roomId){

        List<String> players = RoomManager.getAllPlayers(roomId);
        for(String playerId:players){
            roleOperateNumMap.remove(playerId);
        }
        logger.debug(roomId+"移除该房间操作缓存");

    }

    /**
     * 添加玩家操作
     * @param roleOperateNum
     */
    public static void addRoleOperateNum(RoleOperateNum roleOperateNum){
        roleOperateNumMap.put(roleOperateNum.getPlayerId(),roleOperateNum);
    }

    /**
     * 获取玩家的操作记录
     * @param playerId
     * @return
     */
    public static RoleOperateNum getRoleOperateNum(String playerId){
        return roleOperateNumMap.get(playerId);
    }

    /**
     * 缓存游戏开始时间点
     *
     * @param roomId
     * @param time
     */
    public static void addGameStatisticsTime(String roomId, Long time) {
        gameStatisticsTime.put(roomId, time);
    }

    /**
     * 获取游戏开始的时间点
     *
     * @param roomId
     * @return
     */
    public static Long getGameStatisticsTime(String roomId) {
        return gameStatisticsTime.get(roomId);
    }

}
