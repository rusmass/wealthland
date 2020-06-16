package com.ascn.richlife.service.gamerecord.imp;

import com.ascn.richlife.dao.gamerecord.GameStatisticsDao;
import com.ascn.richlife.model.countInfo.GameStatistics;
import com.ascn.richlife.service.gamerecord.GameStatisticsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 游戏积分统计
 *
 * Created by Administrator on 2018/1/23 0023.
 */
@Service
public class GameStatisticsServiceImp implements GameStatisticsService {

    @Autowired
    GameStatisticsDao gameStatisticsDao;

    @Override
    public GameStatistics getGameStatistics(String playerId) {
        return gameStatisticsDao.getGameStatistics(playerId);
    }

    @Override
    public boolean addGameStatistics(GameStatistics gameStatistics) {
        if(gameStatisticsDao.addGameStatistics(gameStatistics)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateGameStatistics(GameStatistics gameStatistics) {
        if(gameStatisticsDao.updateGameStatistics(gameStatistics)>0){
            return true;
        }
        return false;
    }


}
