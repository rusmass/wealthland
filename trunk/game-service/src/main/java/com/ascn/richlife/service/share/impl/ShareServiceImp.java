package com.ascn.richlife.service.share.impl;

import com.ascn.richlife.dao.share.ShareDao;
import com.ascn.richlife.model.Share;
import com.ascn.richlife.service.share.ShareService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 游戏分享
 *
 * Created by Administrator on 2017/8/8 0008.
 */
@Service("shareService")
public class ShareServiceImp implements ShareService {

    @Autowired
    ShareDao shareDao;

    @Override
    public Share findGame(String playerId) {
        return shareDao.findGame();
    }

    @Override
    public Share findRoom(String playerId) {
        return shareDao.findRoom();
    }

    @Override
    public Share findDreamBoard() {
        return shareDao.findDreamBoard();
    }
}
