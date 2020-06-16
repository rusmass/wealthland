package com.ascn.richlife.model;

import java.util.Map;

/**
 * 选择角色信息
 */
public class ChooseRoleData {

    /**
     * 角色的状态
     */
    private Map<Integer,Boolean> roleStatus;

    /**
     * 玩家选择的角色
     */
    private Map<String, Integer> playerRole;

    //玩家是否准备
    private Map<String,Boolean> readyStatus;

    public Map<Integer, Boolean> getRoleStatus() {
        return roleStatus;
    }

    public ChooseRoleData setRoleStatus(Map<Integer, Boolean> roleStatus) {
        this.roleStatus = roleStatus;
        return this;
    }

    public Map<String, Integer> getPlayerRole() {
        return playerRole;
    }

    public ChooseRoleData setPlayerRole(Map<String, Integer> playerRole) {
        this.playerRole = playerRole;
        return this;
    }

    public Map<String, Boolean> getReadyStatus() {
        return readyStatus;
    }

    public ChooseRoleData setReadyStatus(Map<String, Boolean> readyStatus) {
        this.readyStatus = readyStatus;
        return this;
    }

    @Override
    public String toString() {
        return "ChooseRoleData{" +
                "roleStatus=" + roleStatus +
                ", playerRole=" + playerRole +
                ", readyStatus=" + readyStatus +
                '}';
    }
}
