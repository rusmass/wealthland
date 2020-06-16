package com.ascn.richlife.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascn.richlife.model.login.GameVersion;
import com.ascn.richlife.service.login.GameService;
import com.ascn.richlife.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.*;

/**
 * 游戏版本控制器
 */
@Controller
@RequestMapping(value = "/game", produces = "application/json;charset=UTF-8")
public class GameController {
    private Logger logger = Logger.getLogger(GameController.class);

    @Autowired
    private GameService gameService;

    /**
     * 检测版本
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/version", method = RequestMethod.GET)
    public String checkVersion(@RequestParam String jsonString) {
        JSONObject object = JSONObject.parseObject(jsonString);
        String code = object.getString("versionCode");
        logger.info("客户端版本"+code);
        GameVersion gameVersion = null;
        try {
            gameVersion = gameService.checkVersion();
            //解析字符串为list集合
            List<String> list = Arrays.asList(gameVersion.getCompatible().split(","));
            if(list.contains(code)){
                gameVersion.setIsUpdate(0);
            }else {
                gameVersion.setIsUpdate(1);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("检测版本号" + gameVersion);
        return ResultUtil.setResult(0, "版本号", gameVersion);
    }

    /**
     * 查询所有服务器列表
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/server/list")
    public String findServer() {
        JSONArray jsonArray = null;
        try {
            jsonArray = gameService.findServer();
        } catch (Exception e) {
            e.printStackTrace();
        }
        logger.info("查询服务器成功");
        return ResultUtil.setResult(0, "查询成功", jsonArray);
    }

}
