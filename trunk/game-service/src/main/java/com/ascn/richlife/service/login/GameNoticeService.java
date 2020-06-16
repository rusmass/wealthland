package com.ascn.richlife.service.login;

import com.ascn.richlife.model.login.GameNotice;

import java.util.List;

/**
 * 游戏公告服务
 *
 * Created by Administrator on 2017/6/27 0027.
 */
public interface GameNoticeService {

    /**
     * 查询公告
     * @return
     */
    List<GameNotice> findGameNotice()throws Exception;
}
