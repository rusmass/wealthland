package com.ascn.richlife.model;

import java.util.Date;

/**
 * 连接信息
 */
public class ConnectionInfo {

    /**
     * 玩家id
     */
    private String playerId;

    /**
     * 玩家状态
     */
    private int status;

    /**
     * 房间号
     */
    private String roomId;

    /**
     * 玩家连接的日期
     */
    private Date date;

    public String getPlayerId() {
        return playerId;
    }

    public ConnectionInfo setPlayerId(String playerId) {
        this.playerId = playerId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public ConnectionInfo setStatus(int status) {
        this.status = status;
        return this;
    }

    public String getRoomId() {
        return roomId;
    }

    public ConnectionInfo setRoomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

    public Date getDate() {
        return date;
    }

    public ConnectionInfo setDate(Date date) {
        this.date = date;
        return this;
    }

    public ConnectionInfo() {
    }

    public ConnectionInfo(String playerId, int status, Date date) {
        this.playerId = playerId;
        this.status = status;
        this.date = date;
    }

    @Override
    public String toString() {
        return "ConnectionInfo{" +
                "playerId='" + playerId + '\'' +
                ", status=" + status +
                ", roomId='" + roomId + '\'' +
                ", date=" + date +
                '}';
    }
}
