package com.ascn.richlife.dao.gamerecord;

import com.ascn.richlife.model.countInfo.GameRecord;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 游戏对战记录
 *
 * Created by Administrator on 2018/1/23 0023.
 */
@Repository
public interface GameRecordDao {

    /**
     * 添加游戏对战记录
     * @param gameRecord
     * @return
     */
    int addGameRecord(GameRecord gameRecord);

    /**
     * 更新游戏对战记录
     * @param gameRecord
     * @return
     */
    int updateGameRecord(GameRecord gameRecord);

    /**
     * 获取游戏对战记录集合
     * @param playerId
     * @return
     */
    List<GameRecord> getGameRecords(String playerId);

    /**
     * 获取某一房间中的游戏对战记录
     * @param roomCode
     * @return
     */
    List<GameRecord> getGameRecordsRoom(String roomCode);
}
