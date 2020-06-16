package com.ascn.richlife.service.login;

import com.ascn.richlife.model.login.User;

/**
 * 游戏用户操作服务
 *
 * Created by zhenhb on 2018/6/14.
 * @author zhenhb
 */
public interface UserService {
    /**
     * 玩家注册
     * @param user
     * @return
     */
    boolean register(User user)throws Exception;

    /**
     * 修改密码
     * @param user
     * @return
     */
    boolean alterPwd(User user)throws Exception;

    /**
     * 玩家登录
     * @param phone
     * @param password
     * @return
     */
    boolean login(String phone, String password)throws Exception;

    /**
     * 通过手机查询用户信息
     * @param phone
     * @return
     */
    User findUserByPhone(String phone)throws Exception;

    /**
     * 通过微信查询用户ID
     * @param weChatId
     * @return
     */
    User findUserByWeChatId(String weChatId)throws Exception;

    /**
     * 绑定手机
     * @param userId
     * @param phone
     * @param password
     * @return
     */
    boolean bindPhone(String userId, String phone, String password)throws Exception;

    /**
     * 绑定微信
     * @param userId
     * @param weChat
     * @return
     */
    boolean bindWeChat(String userId, String weChat)throws Exception;

    /**
     * 删除已有微信账户
     * @param weChat
     * @return
     */
    boolean deleWeChat(String weChat)throws Exception;

    /**
     * 更新用户类型
     * @param userId
     * @param user_type
     * @return
     */
    boolean updateUserType(String userId,String user_type)throws Exception;
}
