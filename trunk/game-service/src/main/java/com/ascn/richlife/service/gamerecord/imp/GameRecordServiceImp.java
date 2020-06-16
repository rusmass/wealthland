package com.ascn.richlife.service.gamerecord.imp;

import com.ascn.richlife.dao.gamerecord.GameRecordDao;
import com.ascn.richlife.model.countInfo.GameRecord;
import com.ascn.richlife.service.gamerecord.GameRecordService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏对战记录服务层
 *
 * Created by Administrator on 2018/1/23 0023.
 */
@Service
public class GameRecordServiceImp implements GameRecordService{

    @Autowired
    GameRecordDao gameRecordDao;

    @Override
    public boolean addGameRecord(GameRecord gameRecord) {
        if(gameRecordDao.addGameRecord(gameRecord)>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateGameRecord(GameRecord gameRecord) {
        if(gameRecordDao.updateGameRecord(gameRecord)>0){
            return true;
        }
        return false;
    }

    @Override
    public List<GameRecord> getGameRecords(String playerId) {
        return gameRecordDao.getGameRecords(playerId);
    }

    @Override
    public List<GameRecord> getGameRecordsRoom(String roomCode) {
        return gameRecordDao.getGameRecordsRoom(roomCode);
    }

}
