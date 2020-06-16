package com.ascn.richlife.server.group;

import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.model.Room;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 房间管理
 *
 * Created by zhangpengxiang on 2017/6/6.
 */
@Component
public class RoomManager {

    /**
     * 房间列表
     */
    private static Map<String, Room> roomMap = new ConcurrentHashMap<>();

    /**
     * 添加房间
     *
     * @param room
     */
    public static void addRoom(Room room) {
        roomMap.put(room.getRoomId(), room);
    }

    /**
     * 获取房间
     *
     * @param roomId
     * @return
     */
    public static Room getRoom(String roomId) {
        return roomMap.get(roomId);
    }

    /**
     * 删除房间
     *
     * @param roomId
     */
    public static void removeRoom(String roomId) {
        roomMap.remove(roomId);
    }

    /**
     * 是否有该房间
     *
     * @param roomId
     * @return
     */
    public static boolean containsKey(String roomId) {
        return roomMap.containsKey(roomId);
    }

    /**
     * 获取房间里的在线玩家
     *
     * @param roomId
     * @return
     */
    public static List<String> getPlayers(String roomId) {

        Room room = getRoom(roomId);

        Map<String, Player> players = room.getPlayers();

        List<String> playerIds = new ArrayList<>();

        for (Map.Entry<String, Player> entry : players.entrySet()) {
            if ("1".equals(entry.getValue().getIfBreak())) {
                playerIds.add(entry.getValue().getId());
            }
        }

        return playerIds;
    }

    /**
     * 获取在线的玩家
     *
     * @param roomId
     * @return
     */
    public static List<String> getOnlinePlayers(String roomId) {
        Room room = getRoom(roomId);
        List<String> onlinePlayer = new ArrayList<>();
        Map<String, Player> players = room.getPlayers();
        for (Map.Entry<String, Player> entry : players.entrySet()) {
            if ("1".equals(entry.getValue().getIfBreak())) {
                onlinePlayer.add(entry.getKey());
            }
        }
        return onlinePlayer;
    }

    /**
     * 获取房间中所有的玩家（在线和掉线）
     *
     * @param roomId
     * @return
     */
    public static List<String> getAllPlayers(String roomId) {
        Room room = getRoom(roomId);
        List<String> players = new ArrayList<>();
        for (Map.Entry<String, Player> entry : room.getPlayers().entrySet()) {
            players.add(entry.getKey());
        }
        return players;
    }

    /**
     * 获取房间里所有玩家的名字
     *
     * @param roomId
     * @return
     */
    public static Map<String, String> getPlayerNames(String roomId) {

        Map<String, String> playerNames = new HashMap<>();

        Room room = getRoom(roomId);

        Map<String, Player> players = room.getPlayers();

        for (Map.Entry<String, Player> entry : players.entrySet()) {

            playerNames.put(entry.getKey(), entry.getValue().getName());
        }

        return playerNames;
    }

    /**
     * 获取房间中玩家头像
     *
     * @param roomId
     * @return
     */
    public static Map<String, String> getPlayerAvatars(String roomId) {
        Map<String, String> avatars = new HashMap<>();
        Room room = getRoom(roomId);
        Map<String, Player> playerMap = room.getPlayers();
        for (Map.Entry<String, Player> entry : playerMap.entrySet()) {

            avatars.put(entry.getKey(), entry.getValue().getAvatar());
        }
        return avatars;
    }

    /**
     * 更新房间状态
     *
     * @param roomId
     */
    public static void updateRoomStatus(String roomId, int roomStatus) {
        getRoom(roomId).setRoomStatus(roomStatus);
    }

    /**
     * 玩家掉线修改为掉线状态
     *
     * @param roomId
     * @param playerId
     */
    public static void removeRoleFromRoom(String roomId, String playerId) {

        if(RoomManager.containsKey(roomId)&&RoomManager.getOnlinePlayers(roomId).contains(playerId)){
            //0为断线
            getRoom(roomId).getPlayers().get(playerId).setIfBreak("0");
        }
    }

    /**
     * 掉线玩家添加到房间中
     *
     * @param roomId
     * @param playerId
     */
    public static void putRoleInRoom(String roomId, String playerId) throws Exception {
        //1为在线
        getRoom(roomId).getPlayers().get(playerId).setIfBreak("1");
    }

    /**
     * 从当前房间移除该玩家
     *
     * @param roomId
     * @param playerId
     */
    public static void removeRole(String roomId, String playerId) {
        getRoom(roomId).getPlayers().remove(playerId);
    }

}
