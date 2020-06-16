package com.ascn.richlife.dao.share;

import com.ascn.richlife.model.Share;
import org.springframework.stereotype.Repository;

/**
 * 分享信息
 */
@Repository("shareDao")
public interface ShareDao {

    /**
     * 查询分享游戏数据
     * @return
     */
    Share findGame();

    /**
     * 查询分享房间数据
     * @return
     */
    Share findRoom();

    /**
     * 查询梦想板
     * @return
     */
    Share findDreamBoard();

}
