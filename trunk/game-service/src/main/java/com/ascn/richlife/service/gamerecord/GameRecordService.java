package com.ascn.richlife.service.gamerecord;

import com.ascn.richlife.model.countInfo.GameRecord;

import java.util.List;

/**
 * 游戏对战记录服务
 *
 * Created by Administrator on 2018/1/23 0023.
 */
public interface GameRecordService {

    /**
     * 添加游戏对战记录
     *
     * @param gameRecord
     * @return
     */
    boolean addGameRecord(GameRecord gameRecord);

    /**
     * 更新游戏对战记录
     *
     * @param gameRecord
     * @return
     */
    boolean updateGameRecord(GameRecord gameRecord);

    /**
     * 获取游戏对战记录
     *
     * @param playerId
     * @return
     */
    List<GameRecord> getGameRecords(String playerId);

    /**
     * 获取房间游戏对战记录
     *
     * @param roomCode
     * @return
     */
    List<GameRecord> getGameRecordsRoom(String roomCode);
}
