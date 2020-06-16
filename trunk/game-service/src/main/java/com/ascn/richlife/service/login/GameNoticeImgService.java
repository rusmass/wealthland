package com.ascn.richlife.service.login;

import com.ascn.richlife.model.login.NoticeImg;

/**
 * 游图片戏公告
 *
 * Created by Administrator on 2018/2/7 0007.
 */
public interface GameNoticeImgService {

    /**
     * 获取图片
     * @param id
     * @return
     */
    NoticeImg getImg(int id);
}
