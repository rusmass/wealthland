package com.ascn.richlife.model.countInfo;

/**
 * 游戏数据统计
 */
public class GameStatistics {

    /**
     * 游戏数据ID
     */
    private String gameStatisticsId;

    /**
     * 玩家ID
     */
    private String playerId;

    /**
     * 胜利场次
     */
    private int winScreenings;

    /**
     * 失败场次
     */
    private int failScreenings;

    /**
     * 胜率
     */
    private String winrate;

    /**
     * 游戏平均时长
     */
    private String WhenLong;

    /**
     * 记录状态
     */
    private int status;

    /**
     * 创建时间
     */
    private String createTime;

    public GameStatistics() {
    }

    public GameStatistics(String playerId, int winScreenings, int failScreenings, String winrate, String whenLong) {
        this.playerId = playerId;
        this.winScreenings = winScreenings;
        this.failScreenings = failScreenings;
        this.winrate = winrate;
        WhenLong = whenLong;
    }

    public String getGameStatisticsId() {
        return gameStatisticsId;
    }

    public void setGameStatisticsId(String gameStatisticsId) {
        this.gameStatisticsId = gameStatisticsId;
    }

    public String getPlayerId() {
        return playerId;
    }

    public void setPlayerId(String playerId) {
        this.playerId = playerId;
    }

    public int getWinScreenings() {
        return winScreenings;
    }

    public void setWinScreenings(int winScreenings) {
        this.winScreenings = winScreenings;
    }

    public int getFailScreenings() {
        return failScreenings;
    }

    public void setFailScreenings(int failScreenings) {
        this.failScreenings = failScreenings;
    }

    public String getWinrate() {
        return winrate;
    }

    public void setWinrate(String winrate) {
        this.winrate = winrate;
    }

    public String getWhenLong() {
        return WhenLong;
    }

    public void setWhenLong(String whenLong) {
        WhenLong = whenLong;
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

    @Override
    public String toString() {
        return "GameStatistics{" +
                "gameStatisticsId='" + gameStatisticsId + '\'' +
                ", playerId='" + playerId + '\'' +
                ", winScreenings=" + winScreenings +
                ", failScreenings=" + failScreenings +
                ", winrate='" + winrate + '\'' +
                ", WhenLong='" + WhenLong + '\'' +
                ", status=" + status +
                ", createTime='" + createTime + '\'' +
                '}';
    }
}
