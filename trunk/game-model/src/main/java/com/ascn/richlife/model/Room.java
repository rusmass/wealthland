package com.ascn.richlife.model;

import com.ascn.richlife.model.login.Player;

import java.util.List;
import java.util.Map;

/**
 * 房间信息
 */
public class Room {

    /**
     * 房间号
     */
    private String roomId;

    /**
     * 玩家
     */
    private Map<String, Player> players;

    /**
     * 准备状态
     */
    private Map<String, Boolean> readyStatus;

    /**
     * 退出房间信息
     */
    private Map<String, Boolean> exitRoom;

    /**
     * 房间状态
     */
    private int roomStatus;

    /**
     * 重连的玩家id
     */
    private List<String> reconnectPlayerId;

    public String getRoomId() {
        return roomId;
    }

    public List<String> getReconnectPlayerId() {
        return reconnectPlayerId;
    }

    public void setReconnectPlayerId(List<String> reconnectPlayerId) {
        this.reconnectPlayerId = reconnectPlayerId;
    }

    public Room setRoomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

    public Map<String, Player> getPlayers() {
        return players;
    }

    public Room setPlayers(Map<String, Player> players) {
        this.players = players;
        return this;
    }

    public Map<String, Boolean> getReadyStatus() {
        return readyStatus;
    }

    public Room setReadyStatus(Map<String, Boolean> readyStatus) {
        this.readyStatus = readyStatus;
        return this;
    }

    public Map<String, Boolean> getExitRoom() {
        return exitRoom;
    }

    public void setExitRoom(Map<String, Boolean> exitRoom) {
        this.exitRoom = exitRoom;
    }

    public int getRoomStatus() {
        return roomStatus;
    }

    public Room setRoomStatus(int roomStatus) {
        this.roomStatus = roomStatus;
        return this;
    }
}
