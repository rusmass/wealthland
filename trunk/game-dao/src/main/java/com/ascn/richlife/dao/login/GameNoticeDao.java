package com.ascn.richlife.dao.login;

import com.ascn.richlife.model.login.GameNotice;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * 游戏公告
 *
 * Created by zhenhb on 2017/6/27 0027.
 * @author zhenhb
 */
@Repository
public interface GameNoticeDao {

    /**
     *  查询最新的前五条公告
     * @return
     */
    List<GameNotice> findGameNotice() throws Exception;

}
