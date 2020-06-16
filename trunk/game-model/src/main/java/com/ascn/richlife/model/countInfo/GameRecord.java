package com.ascn.richlife.model.countInfo;

/**
 * 游戏对战记录
 */
public class GameRecord {

    /**
     * 游戏记录id
     */
    private String id;

    /**
     * 玩家id
     */
    private String playerId;

    /**
     * 玩家昵称
     */
    private String name;

    /**
     * 角色头像路径
     */
    private String job;

    /**
     * 职业
     */
    private String occupation;

    /**
     * 流动资金
     */
    private String flowCash;

    /**
     * 增加的资金
     */
    private String increasedCash;

    /**
     * 玩家位置
     */
    private int inOrOut;

    /**
     * 房间号
     */
    private String roomCode;

    /**
     * 时间积分
     */
    private String timeIntegral;

    /**
     * 品质积分
     */
    private String qualityIntegral;

    /**
     * 时长
     */
    private String whenLong;

    /**
     * 创建财富
     */
    private int createWealth;

    /**
     * 管理财富
     */
    private int manageWealth;

    /**
     * 运用财富
     */
    private int useOfWealth;

    /**
     * 超越财富
     */
    private int surpassWealth;

    /**
     * 综合评分
     */
    private int comprehensiveScore;

    /**
     * 状态
     */
    private int status;

    /**
     * 创建时间
     */
    private String createTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getJob() {
        return job;
    }

    public void setJob(String job) {
        this.job = job;
    }

    public String getOccupation() {
        return occupation;
    }

    public void setOccupation(String occupation) {
        this.occupation = occupation;
    }

    public String getFlowCash() {
        return flowCash;
    }

    public void setFlowCash(String flowCash) {
        this.flowCash = flowCash;
    }

    public String getTimeIntegral() {
        return timeIntegral;
    }

    public void setTimeIntegral(String timeIntegral) {
        this.timeIntegral = timeIntegral;
    }

    public String getQualityIntegral() {
        return qualityIntegral;
    }

    public void setQualityIntegral(String qualityIntegral) {
        this.qualityIntegral = qualityIntegral;
    }

    public String getWhenLong() {
        return whenLong;
    }

    public void setWhenLong(String whenLong) {
        this.whenLong = whenLong;
    }

    public double getCreateWealth() {
        return createWealth;
    }

    public void setCreateWealth(int createWealth) {
        this.createWealth = createWealth;
    }

    public int getManageWealth() {
        return manageWealth;
    }

    public void setManageWealth(int manageWealth) {
        this.manageWealth = manageWealth;
    }

    public int getUseOfWealth() {
        return useOfWealth;
    }

    public void setUseOfWealth(int useOfWealth) {
        this.useOfWealth = useOfWealth;
    }

    public int getSurpassWealth() {
        return surpassWealth;
    }

    public void setSurpassWealth(int surpassWealth) {
        this.surpassWealth = surpassWealth;
    }

    public int getComprehensiveScore() {
        return comprehensiveScore;
    }

    public void setComprehensiveScore(int comprehensiveScore) {
        this.comprehensiveScore = comprehensiveScore;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getCreateTime() {
        return createTime;
    }

    public void setCreateTime(String createTime) {
        this.createTime = createTime;
    }

    public String getIncreasedCash() {
        return increasedCash;
    }

    public void setIncreasedCash(String increasedCash) {
        this.increasedCash = increasedCash;
    }

    public int getInOrOut() {
        return inOrOut;
    }

    public void setInOrOut(int inOrOut) {
        this.inOrOut = inOrOut;
    }

    public String getRoomCode() {
        return roomCode;
    }

    public void setRoomCode(String roomCode) {
        this.roomCode = roomCode;
    }

    @Override
    public String toString() {
        return "GameRecord{" +
                "id='" + id + '\'' +
                ", playerId='" + playerId + '\'' +
                ", name='" + name + '\'' +
                ", job='" + job + '\'' +
                ", occupation='" + occupation + '\'' +
                ", flowCash='" + flowCash + '\'' +
                ", increasedCash='" + increasedCash + '\'' +
                ", inOrOut=" + inOrOut +
                ", roomCode='" + roomCode + '\'' +
                ", timeIntegral='" + timeIntegral + '\'' +
                ", qualityIntegral='" + qualityIntegral + '\'' +
                ", whenLong='" + whenLong + '\'' +
                ", createWealth=" + createWealth +
                ", manageWealth=" + manageWealth +
                ", useOfWealth=" + useOfWealth +
                ", surpassWealth=" + surpassWealth +
                ", comprehensiveScore=" + comprehensiveScore +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
