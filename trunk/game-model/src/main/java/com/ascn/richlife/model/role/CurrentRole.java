package com.ascn.richlife.model.role;

/**
 * 当前操作的玩家模型
 */
public class CurrentRole {

    /**
     * 玩家ID
     */
    private String playerId;

    /**
     * 玩家状态
     */
    private String status;

    public CurrentRole(){}

    public CurrentRole(String playerId, String status) {
        this.playerId = playerId;
        this.status = status;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return "CurrentRole{" +
                "playerId='" + playerId + '\'' +
                ", status='" + status + '\'' +
                '}';
    }
}
