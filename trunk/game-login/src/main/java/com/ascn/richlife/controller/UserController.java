package com.ascn.richlife.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.ascn.richlife.model.login.Group;
import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.model.login.User;
import com.ascn.richlife.service.login.PlayerService;
import com.ascn.richlife.service.login.UserService;
import com.ascn.richlife.util.EncoderUtil;
import com.ascn.richlife.util.HttpRequestUtil;
import com.ascn.richlife.util.ResultUtil;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.MultipartHttpServletRequest;

import javax.servlet.http.HttpServletRequest;
import java.io.*;
import java.text.SimpleDateFormat;
import java.util.*;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 玩家登录控制器
 *
 * Created by zhenhb on 2018/6/14 .
 * @zhenhb
 */
@Controller
@RequestMapping(value = "/user", produces = "application/json;charset=UTF-8")
public class UserController {

    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(UserController.class);

    /**
     * 图片存储地址
     */
    private ResourceBundle resourceBundle = ResourceBundle.getBundle("properties/address");

    /**
     * 用户正常状态
     */
    private static final String USER_STATUS = "1";

    @Autowired
    public UserService userService;

    @Autowired
    public PlayerService playerService;

    /**
     * 将短信验证码发给玩家手机,并缓存手机号和验证码
     *
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/getCode", method = RequestMethod.GET)
    public String codeController(@RequestParam String jsonString) throws Exception {

        //解析Json
        logger.debug("参数" + jsonString);
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        final String phone = jsonObject.getString("phone");
        Map<String, Object> result = sendCode(phone);
        String data = result.get("data").toString();
        final String code = result.get("code").toString();
        String[] dataStr = data.split(",");
        if (data != null && dataStr[5].equals("提交成功")) {

            //保存验证码,用户操作时进行判断
            if (code.equals(Group.accountGroup.get(phone))) {

                //缓存手机和验证码
                Group.accountGroup.put(phone, code);
            } else {
                logger.debug("发送验证码异常" + phone);
            }
            try {

                //成功返0
                JSONObject object = new JSONObject();
                object.put("code", code);

                //一分钟后销毁code
                ScheduledExecutorService scheduledExecutorService = new ScheduledThreadPoolExecutor(5);
                scheduledExecutorService.schedule(new Runnable() {
                    @Override
                    public void run() {
                        //移除缓存短信
                        Group.accountGroup.remove(phone);
                        logger.debug("验证码设置1分钟有效期");
                    }
                },60,TimeUnit.SECONDS);
                logger.debug("获取验证码成功" + code);
                return ResultUtil.setResult(0, "获取验证码成功", object);
            } catch (Exception e) {
                logger.error("未知错误" + e.getMessage());
            }
        } else {

            //发送验证码失败，移除失败的缓存
            Group.accountGroup.remove(phone);
            logger.debug(phone + "移除缓存失败");
            try {

                //失败返回-1
                logger.info("获取验证码错误");
                return ResultUtil.setJson(-1, "获取验证码失败");
            } catch (Exception e) {
                logger.error("未知错误" + e.getMessage());
            }
        }
        return ResultUtil.setJson(0, "获取验证码出现未知错误");
    }

    /**
     * 发送验证码到短信平台
     *
     * @param phone
     * @return
     */
    public Map<String, Object> sendCode(String phone) {

        //创建短信接口需要的参数
        Map<String, String> params = new HashMap<String, String>(16);
        //用户名
        params.put("name", "18601271509");
        //密码
        params.put("pwd", "A6EC06A6C0D921E34027AC9619AE");
        //固定参数
        params.put("type", "pt");
        //注册玩家手机号
        params.put("mobile", phone);
        String code;

        //判断是否重复发送验证码,如果重复则返回在有效期内的验证码,否则生成新的验证码
        if (Group.accountGroup.containsKey(phone)) {
            if (Group.accountGroup.get(phone) == null) {
                code = String.valueOf(getCode());
            } else {
                code = Group.accountGroup.get(phone);
                logger.debug("验证码" + code);
            }
        } else {
            code = String.valueOf(getCode());
        }

        //短信内容
        params.put("content", "【智富人生】短信验证码为：" + code + "。一分钟内有效，请不要把验证码泄露给其他人。");

        //post方式提交
        String data = HttpRequestUtil.doPost("http://web.cr6868.com/asmx/smsservice.aspx", params);
        Map<String, Object> result = new HashMap<String, Object>(16);
        result.put("data", data);
        result.put("code", code);

        //缓存手机和验证码
        Group.accountGroup.put(phone, code);
        return result;
    }

    /**
     * 随机生成4位数验证码
     *
     * @return
     */
    private int getCode() {
        return (int) ((Math.random() * 9000 + 1000));
    }

    /**
     * 找回账密码
     *
     * @param jsonString
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/findPassword", method = RequestMethod.POST)
    public String pwdController(@RequestParam String jsonString) throws Exception {
        logger.debug("找回密码参数" + jsonString);
        //解析Json字符串
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String phone = jsonObject.getString("phone");
        String code = jsonObject.getString("code");
        String pwd = jsonObject.getString("password");
        //MD5加密
        String password = EncoderUtil.EncoderByMd5(pwd);
        //判断是否获取验证码
        if (!Group.accountGroup.containsKey(phone)) {
            logger.debug(phone + "请先获取验证码");
            return ResultUtil.setJson(1, "请先获取验证码");
        } else {
            //查询手机账户是否存在
            User user = userService.findUserByPhone(phone);
            if (user == null) {
                logger.debug("手机号不存在，请注册" + user);
                return ResultUtil.setJson(1, "手机号不存在，请注册");
            }
            if (Group.accountGroup.get(phone) != null) {
                //获取缓存的验证码
                String codes = Group.accountGroup.get(phone);
                //判断用户输入的code和获取的code是否一样
                if (code.equals(codes) && Group.accountGroup.containsKey(phone)) {
                    user.setUser_pwd(password);
                    boolean result = userService.alterPwd(user);
                    if (result) {
                        logger.debug("密码修改成功" + user);
                        return ResultUtil.setJson(0, "密码修改成功");
                    } else {
                        logger.debug("密码修改失败" + user);
                        return ResultUtil.setJson(-1, "密码修改失败");
                    }
                }
            }
        }

        logger.debug("修改密码出现未知错误");
        return ResultUtil.setJson(-1, "未知错误");
    }

    /**
     * 账户注册
     *
     * @param jsonString
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public String registerController(@RequestParam String jsonString) throws Exception {
        logger.debug("参数" + jsonString);

        //解析Json
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String phone = jsonObject.getString("phone");
        String code = jsonObject.getString("code");
        String pwd = jsonObject.getString("password");

        //密码进行MD5加密
        String password = EncoderUtil.EncoderByMd5(pwd);

        //查询该手机账号是否存在
        User phoneUser = userService.findUserByPhone(phone);
        if (phoneUser != null) {
            logger.debug("该账户已存在" + phoneUser);
            return ResultUtil.setJson(1, "该账户已存在");
        }

        //判断是否获取手机验证码
        if (Group.accountGroup.get(phone) != null) {

            //获取缓存的信息
            String resultCode = Group.accountGroup.get(phone);

            //判断输入数据是否正确
            if (code.equals(resultCode) && Group.accountGroup.containsKey(phone)) {
                User user = new User();
                user.setUser_pwd(password);
                user.setUser_phone(phone);
                //手机类型为0,微信为1
                user.setUser_type("0");
                boolean result = userService.register(user);
                if (result) {
                    logger.debug(phone + "账户注册成功");
                    return ResultUtil.setJson(0, "注册成功");
                }
            } else {
                logger.debug("验证码或手机号输入错误");
                return ResultUtil.setJson(1, "验证码或手机号输入错误");
            }
        } else {
            logger.debug("验证码失效，请先获取验证码");
            return ResultUtil.setJson(1, "请先获取验证码");
        }
        logger.debug("注册失败");
        return ResultUtil.setJson(-1, "注册失败");
    }

    /**
     * 账户登录
     *
     * @param jsonString
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public String loginController(@RequestParam String jsonString) throws Exception {

        //解析Json
        logger.debug("登录参数" + jsonString);
        JSONObject jsonObject = JSON.parseObject(jsonString);

        //获取玩家类型
        int user_type = jsonObject.getIntValue("playerType");
        switch (user_type) {
            case 0:
                //获取登录参数
                String phone = jsonObject.getString("phone");
                String pwd = jsonObject.getString("password");
                //MD5加密
                String password = EncoderUtil.EncoderByMd5(pwd);
                //查询手机登录账户
                if (userService.findUserByPhone(phone) == null) {
                    return ResultUtil.setJson(1, "手机号不存在，请注册");
                } else {

                    //手机玩家登录
                    boolean is_login = userService.login(phone, password);
                    if (is_login) {
                        User loginSuccessUser = userService.findUserByPhone(phone);
                        logger.debug(loginSuccessUser.getUser_phone() + "用户登录成功"+loginSuccessUser.getUser_status());
                        if (USER_STATUS.equals(loginSuccessUser.getUser_status())) {

                            //查找玩家登录账户的角色信息
                            Player player = playerService.findPlayerByUserId(loginSuccessUser.getId());

                            if (player == null) {
                                JSONObject object = ResultUtil.buildObject("need", 0);
                                return ResultUtil.setResult(0, "首次手机登录创建角色", object);
                            } else {
                                JSONObject object = ResultUtil.buildObject("need", 1);
                                object.put("player", player);
                                int gameNumber = player.getGameNumber() + 1;
                                playerService.updateGameNumber(gameNumber, loginSuccessUser.getId());
                                return ResultUtil.setResult(0, "手机登录成功", object);
                            }
                        } else  {
                            return ResultUtil.setJson(-2, "该账户被封号");
                        }
                    } else {
                        return ResultUtil.setJson(-1, "密码错误,请重新输入");
                    }
                }

            case 1:

                //获取登录参数
                String weChatId = jsonObject.getString("weChatId");
                logger.debug("微信ID" + weChatId);
                User weChartUser = userService.findUserByWeChatId(weChatId);
                JSONObject object = new JSONObject();
                if (weChartUser == null) {
                    User user1 = new User();
                    user1.setWeChat_id(weChatId);
                    userService.register(user1);
                    object.put("bind", 0);
                    logger.debug("微信登录成功" + weChatId);
                    return ResultUtil.setResult(0, "微信登录成功绑定手机", object);
                }else if (weChartUser.getUser_phone() == null) {
                    object.put("bind", 0);
                    return ResultUtil.setResult(0, "微信登录成功绑定手机", object);
                } else {
                    //检测是否绑定手机
                    if (weChartUser.getUser_phone() == null) {
                        object.put("bind", 0);
                    } else {
                        object.put("bind", 1);
                    }
                }
                User user1 = userService.findUserByWeChatId(weChatId);

                //查看微信玩家角色
                Player player = playerService.findPlayerByUserId(user1.getId());
                logger.debug("玩家角色" + player);
                if (player == null) {
                    object.put("need", 0);
                    logger.debug("首次微信登录创建角色" + object);
                    return ResultUtil.setResult(0, "首次微信登录创建角色", object);
                } else if (player != null) {
                    object.put("need", 1);
                    object.put("player", player);
                    logger.debug("微信登录成功" + object);
                    int gameNumber = player.getGameNumber() + 1;
                    playerService.updateGameNumber(gameNumber, weChartUser.getId());
                    return ResultUtil.setResult(0, "微信登录成功", object);
                }

                break;
                default:
                    throw new RuntimeException("登录失败!");
        }
        return ResultUtil.setJson(-1, "登录失败");
    }

    /**
     * 创建角色信息
     *
     * @param jsonString
     * @return
     * @throws Exception
     */
    @ResponseBody
    @RequestMapping(value = "/player/perfectInfo", method = RequestMethod.POST)
    public String perfectInfoController(@RequestParam String jsonString) throws Exception {
        logger.debug("创建角色信息参数" + jsonString);

        //解析Json字符串
        JSONObject jsonObject = JSONObject.parseObject(jsonString);
        String gender = jsonObject.getString("gender");
        String avatar = jsonObject.getString("avatar");
        String name = jsonObject.getString("nick");
        //区分账户类型
        String account = jsonObject.getString("account");
        logger.debug(account + "参数");

        //查询角色昵称是否存在
        Boolean flag = playerService.findPlayerByName(name);
        if (flag) {
            JSONObject object = new JSONObject();
            object.put("nick", name);
            logger.debug("该角色已存在" + object);
            return ResultUtil.setResult(1, "该角色已存在", object);
        }
        User user;

        //缓存中获取账户Id
        if (account.length() == 11) {
            user = userService.findUserByPhone(account);
        } else {
            user = userService.findUserByWeChatId(account);
        }
        String userId = user.getId();
        logger.debug("userId" + userId);

        //创建角色
        if (userId != null) {
            Player player = new Player();
            player.setId(UUID.randomUUID().toString());
            player.setGender(gender);
            player.setAvatar(avatar);
            player.setName(name);
            player.setGameNumber(0);
            player.setUser_Id(userId);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy.MM.dd");
            player.setBirthday(sdf.format(new Date()));
            boolean result = playerService.createRole(player);
            if (result) {
                JSONObject object = new JSONObject();
                object.put("player", player);
                return ResultUtil.setResult(0, "玩家角色创建成功", object);
            } else {
                return ResultUtil.setJson(1, "玩家角色创建失败");
            }
        }
        return ResultUtil.setJson(-1, "创建角色出错");
    }

    /**
     * 绑定手机
     *
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bindPhone", method = RequestMethod.POST)
    public String bindPhone(String jsonString) throws Exception {
        JSONObject object = JSONObject.parseObject(jsonString);
        String phone = object.getString("phone");
        String code = object.getString("code");
        String pwd = object.getString("password");
        String account = object.getString("account");
        logger.debug(account + "参数");

        //MD5加密
        String password = EncoderUtil.EncoderByMd5(pwd);

        //判断是否获取手机验证码
        if (Group.accountGroup.get(phone) != null) {

            //判断输入数据是否正确
            if (code.equals(Group.accountGroup.get(phone)) && Group.accountGroup.containsKey(phone)) {
                if (phone != null && phone.length() == 11) {

                    //查询该手机账号是否存在
                    User phoneUser = userService.findUserByPhone(phone);
                    if (phoneUser != null && phoneUser.getWeChat_id() == null) {
                        User user = userService.findUserByWeChatId(account);
                        if (user != null) {

                            //删除已有的
                            userService.deleWeChat(account);
                            logger.debug(account + "原始微信账户已删除");
                        }

                        //绑定微信
                        userService.bindWeChat(phoneUser.getId(), account);
                        JSONObject object1 = new JSONObject();
                        Player player = playerService.findPlayerByUserId(phoneUser.getId());
                        if (player == null) {

                            //微信登录绑定的手机未创建角色
                            JSONObject object2 = new JSONObject();
                            object2.put("need", 0);
                            return ResultUtil.setResult(0, "绑定成功，需创建角色", object2);
                        }
                        object1.put("player", player);
                        logger.debug("绑定成功" + phoneUser);
                        return ResultUtil.setResult(1, "绑定成功", object1);
                    } else {
                        User user = userService.findUserByWeChatId(account);
                        if (user != null) {

                            //绑定手机
                            boolean flag = userService.bindPhone(user.getId(), phone, password);
                            if (flag) {
                                return ResultUtil.setJson(0, "绑定手机成功");
                            }
                        }
                    }
                }
            }
        } else {
            logger.debug("验证码失效，请先获取验证码");
            return ResultUtil.setJson(1, "请先获取验证码");
        }
        logger.debug("绑定手机失败" + phone + "微信ID" + account);
        return ResultUtil.setJson(-1, "绑定手机失败");
    }

    /**
     * 绑定微信
     *
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/bindWeChat", method = RequestMethod.POST)
    public String bindWeChat(String jsonString) throws Exception {
        JSONObject object = JSONObject.parseObject(jsonString);
        String weChat = object.getString("weChatId");
        if (weChat != null) {

            User user = userService.findUserByWeChatId(weChat);
            if (user != null) {

                //绑定微信
                boolean flag = userService.bindWeChat(user.getId(), weChat);
                if (flag) {
                    logger.debug(weChat + "绑定成功");
                    return ResultUtil.setJson(3, "绑定微信成功");
                }
            }
        }
        return ResultUtil.setJson(-1, "绑定微信失败");
    }

    /**
     * 手机账户验证
     *
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/authPhone", method = RequestMethod.POST)
    public String authPhone(String jsonString) throws Exception {
        JSONObject object = JSONObject.parseObject(jsonString);
        String phone = object.getString("phone");
        if (phone != null) {
            User user = userService.findUserByPhone(phone);
            if (user != null && user.getWeChat_id() != null) {
                return ResultUtil.setJson(0, "该账户已被绑定");
            }
            if (user != null && user.getWeChat_id() == null) {
                return ResultUtil.setJson(1, "可以绑定");
            }
            if (user == null) {
                return ResultUtil.setJson(2, "可以绑定");
            }
        }
        return ResultUtil.setJson(-1, "未知错误");
    }

    /**
     * 上传头像
     *
     * @param request
     * @throws IOException
     */
    @ResponseBody
    @RequestMapping(value = "/image", method = RequestMethod.POST)
    public String uploadImage(HttpServletRequest request) throws Exception {
        MultipartHttpServletRequest multipartHttpServletRequest = (MultipartHttpServletRequest) request;
        MultipartFile avatar = multipartHttpServletRequest.getFile("picture");
        String fileType = avatar.getOriginalFilename().substring(avatar.getOriginalFilename().indexOf("."), avatar.getOriginalFilename().length());
        String newFileName = System.currentTimeMillis() + fileType;

        // 文件磁盘路径
        String path = (request.getSession().getServletContext().getRealPath("/") + "upload/").replace("webapps\\game\\", "");
        logger.debug("头像路径："+path);

       
        //String path = "/home/image/";
        //创建目录
        File newFile = new File(path);

        // 如果目录不存在，则创建目录
        if (!newFile.isDirectory()) {
            newFile.mkdirs();
        }

        //创建目标文件
        File resultFile = new File(path + newFileName);

        //写入磁盘
        avatar.transferTo(resultFile);
        logger.debug("保存头像成功");

        //返回服务器显示路径
        String servicePath = resourceBundle.getString("fileSavePath") + "/" + "image/" + newFileName;

        if (request.getParameter("playerId") != null) {
            String playerId = request.getParameter("playerId");
            logger.debug("需更换头像玩家id" + playerId);
            if (playerId != null) {
                boolean flag = playerService.uploaderAvatar(servicePath, playerId);
                if (flag) {
                    JSONObject object = new JSONObject();
                    object.put("path", servicePath);
                    return ResultUtil.setResult(0, "更改头像成功", object);
                }
            }
        }

        JSONObject object = new JSONObject();
        object.put("path", servicePath);
        return ResultUtil.setResult(0, "上传成功", object);
    }

    /**
     * 编辑玩家信息
     *
     * @param jsonString
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "/updatePlayer", method = RequestMethod.POST)
    public String updatePlayer(String jsonString) {
        JSONObject jsonObject = JSON.parseObject(jsonString);
        String name = jsonObject.getString("name");
        String gender = jsonObject.getString("gender");
        String birthday = jsonObject.getString("birthday");
        String constellation = jsonObject.getString("constellation");
        String playerId = jsonObject.getString("playerId");
        Player player = new Player();
        player.setConstellation(constellation);
        player.setBirthday(birthday);
        player.setGender(gender);
        player.setName(name);
        player.setId(playerId);
        try {
            //构建返回消息
            JSONObject object = new JSONObject();
            object.put("gender", gender);
            object.put("birthday", birthday);
            object.put("constellation", constellation);

            if (playerService.findPlayerByName(name)) {
                Player player1 = playerService.getPlayerInfo(playerId);
                player.setName(null);
                if (playerService.updatePlayer(player)) {
                    object.put("name", playerService.getPlayerName(playerId));
                    if (playerService.getPlayerName(playerId).equals(name) && !player1.equals(player)) {
                        return ResultUtil.setResult(0, "更新部分信息成功", object);
                    }
                    return ResultUtil.setResult(0, "该昵称已存在", object);
                }
            } else {
                if (playerService.updatePlayer(player)) {
                    object.put("name", name);
                    return ResultUtil.setResult(0, "添加信息成功", object);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return ResultUtil.setJson(-1, "更新信息错误");
    }

}
