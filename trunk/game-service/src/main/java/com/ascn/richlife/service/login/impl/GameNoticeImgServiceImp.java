package com.ascn.richlife.service.login.impl;

import com.ascn.richlife.dao.login.NoticeImgDao;
import com.ascn.richlife.model.login.NoticeImg;
import com.ascn.richlife.service.login.GameNoticeImgService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 游戏图片公告服务
 *
 * Created by Administrator on 2018/2/7 0007.
 */
@Service
public class GameNoticeImgServiceImp implements GameNoticeImgService{

    @Autowired
    NoticeImgDao noticeImgDao;

    @Override
    public NoticeImg getImg(int id) {
        return noticeImgDao.getImg(id);
    }
}
