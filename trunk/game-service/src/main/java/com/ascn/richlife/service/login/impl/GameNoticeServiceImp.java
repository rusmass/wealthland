package com.ascn.richlife.service.login.impl;

import com.ascn.richlife.dao.login.GameNoticeDao;
import com.ascn.richlife.model.login.GameNotice;
import com.ascn.richlife.service.login.GameNoticeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 游戏公告
 *
 * Created by Administrator on 2017/6/27 0027.
 */
@Service("gameNoticeService")
public class GameNoticeServiceImp implements GameNoticeService {

    @Autowired
    GameNoticeDao gameNoticeDao;

    public List<GameNotice> findGameNotice() throws Exception{
        return gameNoticeDao.findGameNotice();
    }
}
