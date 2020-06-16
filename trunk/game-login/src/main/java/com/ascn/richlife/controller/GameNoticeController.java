package com.ascn.richlife.controller;

import com.alibaba.fastjson.JSONArray;
import com.ascn.richlife.model.login.GameNotice;
import com.ascn.richlife.service.login.GameNoticeImgService;
import com.ascn.richlife.service.login.GameNoticeService;
import com.ascn.richlife.util.ResultUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.util.List;

/**
 * 游戏公告控制器
 */
@Controller
@RequestMapping(value = "/gameNotice", produces = "application/json;charset=UTF-8")
public class GameNoticeController {

    @Autowired
    GameNoticeImgService gameNoticeImgService;

    @Autowired
    GameNoticeService gameNoticeService;

    /**
     * 查询公告
     *
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/find", method = RequestMethod.GET)
    public String noticeController() throws Exception {
        List<GameNotice> gameNotices = gameNoticeService.findGameNotice();
        JSONArray jsonArray = new JSONArray();
        for (GameNotice gameNotice : gameNotices) {
            jsonArray.add(gameNotice);
        }
        return ResultUtil.setResult(0, "查询公告成功", jsonArray);
    }

    /**
     * 获取图片
     *
     * @param request
     * @param response
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/getImg")
    public void imgController(HttpServletRequest request, HttpServletResponse response) throws IOException {

        // 禁止图像缓存
        response.setHeader("Pragma", "no-cache");
        response.setHeader("Cache-Control", "no-cache");
        response.setDateHeader("Expires", 0);
        response.setContentType("image/jpeg");

        // 将图像输出到Servlet输出流中。
        int id = Integer.parseInt(request.getParameter("id"));
        ServletOutputStream sos = response.getOutputStream();
        ByteArrayInputStream bais = new ByteArrayInputStream(gameNoticeImgService.getImg(id).getImg());
        BufferedImage bi1 = ImageIO.read(bais);
        ImageIO.write(bi1, "jpeg", sos);
        sos.close();
    }
}
