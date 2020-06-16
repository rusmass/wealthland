package com.ascn.richlife.service.gamerecord;

import com.ascn.richlife.model.countInfo.GameStatistics;

/**
 * 游戏积分统计服务层
 *
 * Created by Administrator on 2018/1/23 0023.
 */
public interface GameStatisticsService {

    /**
     * 获取游戏积分统计
     *
     * @param playerId
     * @return
     */
    GameStatistics getGameStatistics(String playerId);

    /**
     * 添加游戏积分统计
     *
     * @param gameStatistics
     * @return
     */
    boolean addGameStatistics(GameStatistics gameStatistics);

    /**
     * 更新游戏积分统计
     *
     * @param gameStatistics
     * @return
     */
    boolean updateGameStatistics(GameStatistics gameStatistics);
}
