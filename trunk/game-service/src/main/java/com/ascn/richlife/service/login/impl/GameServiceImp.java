package com.ascn.richlife.service.login.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascn.richlife.dao.login.GameDao;
import com.ascn.richlife.model.login.GameVersion;
import com.ascn.richlife.service.login.GameService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.ResourceBundle;

/**
 * 游戏版本检测服务
 *
 * Created by Administrator on 2017/2/27 0027.
 */
@Service("gameService")
public class GameServiceImp implements GameService {

    @Autowired
    GameDao gameDao;

    /**
     * 版本检测
     *
     * @return
     */
    @Override
    public GameVersion checkVersion() throws Exception{
        ResourceBundle resourceBundle = ResourceBundle.getBundle("versionCode");
        String serverIp = resourceBundle.getString("serverIP");
        String serverPort = resourceBundle.getString("serverPort");
        GameVersion gameVersion = gameDao.getGameVersion();
        gameVersion.setServerIP(serverIp);
        gameVersion.setServerPort(serverPort);
        return gameVersion;
    }

    /**
     * 查询服务器列表
     *
     * @return
     */
    @Override
    public JSONArray findServer() throws Exception{
        ResourceBundle bundle = ResourceBundle.getBundle("server");
        JSONObject object = new JSONObject();
        object.put("serverName",bundle.getString("server"));
        JSONObject object1 = new JSONObject();
        object1.put("serverName",bundle.getString("serverName"));
        JSONArray jsonArray = new JSONArray();
        jsonArray.add(object);
        jsonArray.add(object1);
        return jsonArray;
    }

}
