package com.ascn.richlife.service.share;

import com.ascn.richlife.model.Share;

/**
 * 游戏分享
 *
 * Created by Administrator on 2017/8/8 0008.
 */
public interface ShareService {

    /**
     * 查询游戏分享数据
     * @param playerId
     */
    Share findGame(String playerId);

    /**
     * 查询房间号分享数据
     * @param playerId
     */
    Share findRoom(String playerId);

    /**
     * 梦想板分享
     * @return
     */
    Share findDreamBoard();
}
