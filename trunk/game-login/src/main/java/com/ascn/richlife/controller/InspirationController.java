package com.ascn.richlife.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.ascn.richlife.model.login.Inspiration;
import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.service.login.InspirationService;
import com.ascn.richlife.service.login.PlayerService;
import com.ascn.richlife.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

/**
 * 梦想板控制器
 */
@Controller
@RequestMapping(value = "/inspiration", produces = "application/json;charset=UTF-8")
public class InspirationController {

    private Logger logger = Logger.getLogger(InspirationController.class);

    @Autowired
    private InspirationService inspirationService;

    @Autowired
    private PlayerService playerService;

    /**
     * 感悟添加
     *
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/add", method = RequestMethod.POST)
    public String inspiration(@RequestParam String jsonString) throws Exception {
        logger.info("感悟参数" + jsonString);
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String content = jsonObject.getString("input");
        String playerId = jsonObject.getString("playerId");
        logger.info("感悟内容" + content);
        boolean result = false;
        Player player;
        if (playerId != null) {
            player = playerService.getPlayerInfo(playerId);
            result = inspirationService.inspiration(content, player.getName(), player.getAvatar());
        } else {
            result = inspirationService.inspiration(content, null, null);
        }

        if (result) {
            logger.error("感悟添加成功");
            return ResultUtil.setJson(0, "感悟提交成功，请等待发布。");
        } else {
            logger.error("感悟添加失败");
            return ResultUtil.setJson(1, "感悟添加失败");
        }
    }

    /**
     * 分页显示感悟
     *
     * @param
     * @return
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST}, value = "/page")
    public String allInspiration(@RequestParam String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        int page = jsonObject.getIntValue("page");
        logger.info("页数" + page);
        int start = (page - 1) * 10;
        int end = 10;
        List<Inspiration> inspirations = inspirationService.findPage(start, end);
        if (inspirations.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (Inspiration inspiration : inspirations) {
                jsonArray.add(inspiration);
            }
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("inspirations", jsonArray);
            jsonObject1.put("page", page);
            jsonObject1.put("count", (inspirationService.findCount("") / 10) + 1);
            return ResultUtil.setResult(0, "查询成功!", jsonObject1);
        }
        return ResultUtil.setJson(1, "查询失败!");
    }

    /**
     * 通过昵称查询
     *
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(method = {RequestMethod.POST}, value = "/myself")
    public String findByNickName(@RequestParam String jsonString) throws Exception {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        int page = jsonObject.getIntValue("page");
        String playerId = jsonObject.getString("playerId");
        logger.info("页数" + page);
        int start = (page - 1) * 10;
        int end = 10;
        Player player = playerService.getPlayerInfo(playerId);
        List<Inspiration> inspirations = inspirationService.findByNickName(player.getName(), start, end);
        if (inspirations.size() > 0) {
            JSONArray jsonArray = new JSONArray();
            for (Inspiration inspiration : inspirations) {
                jsonArray.add(inspiration);
            }
            JSONObject jsonObject1 = new JSONObject();
            jsonObject1.put("inspirations", jsonArray);
            jsonObject1.put("page", page);
            jsonObject1.put("count", (inspirationService.findCount(player.getName()) / 10) + 1);
            return ResultUtil.setResult(0, "查询成功!", jsonObject1);
        }
        return ResultUtil.setJson(1, "查询失败!");
    }
}
