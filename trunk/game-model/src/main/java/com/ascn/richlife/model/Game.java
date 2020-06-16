package com.ascn.richlife.model;

import com.ascn.richlife.model.role.CurrentRole;
import com.ascn.richlife.model.role.RoleInfo;

import java.util.Map;
import java.util.Queue;

/**
 * 游戏模型
 */
public class Game {

    /**
     * 房间号
     */
    private String roomId;

    /**
     * 游戏的顺序
     */
    private Queue<CurrentRole> roleOrder;

    /**
     * 当前的角色
     */
    private String currentRole;

    /**
     * 当前操作的卡牌
     */
    private int currentCardType;

    /**
     * 当前游戏触发卡牌的玩家
     */
    private String triggerCardPlayerId;

    /**
     * 游戏初始化数据
     */
    private Map<String, Boolean> roleInitData;

    /**
     * 玩家角色数据
     */
    private Map<String, RoleInfo> roleInfoData;

    /**
     * 玩家轮盘位置数据
     */
    private Map<String, Location> roleLocationData;

    /**
     * 角色掷骰子数
     */
    private Map<String, Integer> roleRollDiceNumber;

    /**
     * 回合是否结束
     */
    private Map<String, Boolean> roundEndData;

    /**
     * 购买保险数据
     */
    private Map<String, Boolean> insuranceData;

    /**
     * 是否发红包
     */
    private Map<String, Boolean> sendRedEnvelopeData;

    /**
     * 发红包的金额
     */
    private Map<String, Integer> sendRedEnvelopeMoney;

    /**
     * 触发发红包的角色
     */
    private String triggerRedEnvelopesRole;

    /**
     * 卡组
     */
    private CardGroup cardGroup;

    /**
     * 游戏的状态
     */
    private int status;

    public String getTriggerCardPlayerId() {
        return triggerCardPlayerId;
    }

    public void setTriggerCardPlayerId(String triggerCardPlayerId) {
        this.triggerCardPlayerId = triggerCardPlayerId;
    }

    public int getCurrentCardType() {
        return currentCardType;
    }

    public void setCurrentCardType(int currentCardType) {
        this.currentCardType = currentCardType;
    }

    public String getRoomId() {
        return roomId;
    }

    public Game setRoomId(String roomId) {
        this.roomId = roomId;
        return this;
    }

    public String getTriggerRedEnvelopesRole() {
        return triggerRedEnvelopesRole;
    }

    public void setTriggerRedEnvelopesRole(String triggerRedEnvelopesRole) {
        this.triggerRedEnvelopesRole = triggerRedEnvelopesRole;
    }

    public Queue<CurrentRole> getRoleOrder() {
        return roleOrder;
    }

    public Game setRoleOrder(Queue<CurrentRole> roleOrder) {
        this.roleOrder = roleOrder;
        return this;
    }

    public String getCurrentRole() {
        return currentRole;
    }

    public Game setCurrentRole(String currentRole) {
        this.currentRole = currentRole;
        return this;
    }

    public Map<String, Boolean> getRoleInitData() {
        return roleInitData;
    }

    public Game setRoleInitData(Map<String, Boolean> roleInitData) {
        this.roleInitData = roleInitData;
        return this;
    }

    public Map<String, RoleInfo> getRoleInfoData() {
        return roleInfoData;
    }

    public Game setRoleInfoData(Map<String, RoleInfo> roleInfoData) {
        this.roleInfoData = roleInfoData;
        return this;
    }

    public Map<String, Location> getRoleLocationData() {
        return roleLocationData;
    }

    public Game setRoleLocationData(Map<String, Location> roleLocationData) {
        this.roleLocationData = roleLocationData;
        return this;
    }

    public Map<String, Integer> getRoleRollDiceNumber() {
        return roleRollDiceNumber;
    }

    public Game setRoleRollDiceNumber(Map<String, Integer> roleRollDiceNumber) {
        this.roleRollDiceNumber = roleRollDiceNumber;
        return this;
    }

    public Map<String, Boolean> getRoundEndData() {
        return roundEndData;
    }

    public Game setRoundEndData(Map<String, Boolean> roundEndData) {
        this.roundEndData = roundEndData;
        return this;
    }

    public Map<String, Boolean> getInsuranceData() {
        return insuranceData;
    }

    public Game setInsuranceData(Map<String, Boolean> insuranceData) {
        this.insuranceData = insuranceData;
        return this;
    }

    public CardGroup getCardGroup() {
        return cardGroup;
    }

    public Game setCardGroup(CardGroup cardGroup) {
        this.cardGroup = cardGroup;
        return this;
    }

    public Map<String, Boolean> getSendRedEnvelopeData() {
        return sendRedEnvelopeData;
    }

    public Game setSendRedEnvelopeData(Map<String, Boolean> sendRedEnvelopeData) {
        this.sendRedEnvelopeData = sendRedEnvelopeData;
        return this;
    }

    public Map<String, Integer> getSendRedEnvelopeMoney() {
        return sendRedEnvelopeMoney;
    }

    public Game setSendRedEnvelopeMoney(Map<String, Integer> sendRedEnvelopeMoney) {
        this.sendRedEnvelopeMoney = sendRedEnvelopeMoney;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public Game setStatus(int status) {
        this.status = status;
        return this;
    }

}
