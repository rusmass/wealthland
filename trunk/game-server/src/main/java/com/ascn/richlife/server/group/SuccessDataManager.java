package com.ascn.richlife.server.group;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * Created by Administrator on 2018/6/20 0020.
 */
public class SuccessDataManager {

    /**
     * 存储缓存玩家游戏胜利的数据
     */
    private static final Map<String,Map<String,Boolean>> successData = new ConcurrentHashMap<>();

    /**
     * 获取游戏胜利数据
     * @param roomId
     * @return
     */
    public static Map<String, Boolean> getSuccessData(String roomId){
        return successData.get(roomId);
    }

    /**
     * 获取房间中玩家的胜利状态
     * @param roomId
     * @param data
     */
    public static void addSuccessData(String roomId,Map<String,Boolean> data){
        successData.put(roomId,data);
    }

    /**
     * 获取玩家的胜利状态
     * @param roomId
     * @param playerId
     * @return
     */
    public static boolean getPlayerData(String roomId,String playerId){
        return successData.get(roomId).get(playerId);
    }

}
