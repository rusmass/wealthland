package com.ascn.richlife.dao.gamerecord;

import com.ascn.richlife.model.countInfo.GameStatistics;
import org.springframework.stereotype.Repository;

/**
 * 游戏数据统计
 *
 * Created by Administrator on 2018/1/22 0022.
 */
@Repository
public interface GameStatisticsDao {

    /**
     * 添加游戏统计
     *
     * @param gameStatistics
     * @return
     */
    int addGameStatistics(GameStatistics gameStatistics);

    /**
     * 获取游戏统计
     *
     * @param playerId
     * @return
     */
    GameStatistics getGameStatistics(String playerId);

    /**
     * 更新游戏统计
     *
     * @param gameStatistics
     * @return
     */
    int updateGameStatistics(GameStatistics gameStatistics);
}
