package com.ascn.richlife.dao.login;

import com.ascn.richlife.model.login.GameVersion;
import org.springframework.stereotype.Repository;

/**
 * 游戏版本
 */
@Repository
public interface GameDao {

    /**
     * 获取游戏版本
     *
     * @return
     */
    GameVersion getGameVersion();

}
