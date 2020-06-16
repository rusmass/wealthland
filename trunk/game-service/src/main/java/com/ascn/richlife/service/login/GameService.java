package com.ascn.richlife.service.login;

import com.alibaba.fastjson.JSONArray;
import com.ascn.richlife.model.login.GameVersion;


/**
 * 游戏版本检测服务
 *
 * Created by Administrator on 2017/2/24 0024.
 */
public interface GameService {
    /**
     * 游戏版本检测
     * @return
     */
    GameVersion checkVersion()throws Exception;

    /**
     * 查询服务器列表
     * @return
     */
    JSONArray findServer()throws Exception;

}
