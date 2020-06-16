package com.ascn.richlife.dao.login;

import com.ascn.richlife.model.login.User;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 用户信息
 */
@Repository
public interface UserDao {

    /**
     * 玩家注册
     * @param user
     * @return
     */
    int insertUser(User user)throws Exception;

    /**
     * 查询玩家账号信息
     * @param phone
     * @param password
     * @return
     */
    User login(@Param("phone") String phone, @Param("password") String password)throws Exception;

    /**
     * 设置账户新密码
     * @param user
     * @return
     */
    int setPwd(User user)throws Exception;

    /**
     * 通过手机号查询账号信息
     * @param phone
     * @return
     */
    User findUserByPhone(@Param("phone")String phone)throws Exception;

    /**
     * 通过id查询用户
     * @param id
     * @return
     */
    User findUserById(@Param("id")String id)throws Exception;

    /**
     * 通过微信ID查询用户
     * @param weChatId
     * @return
     */
    User findUserByWeChatId(@Param("weChatId")String weChatId)throws Exception;

    /**
     * 绑定手机
     * @param userId
     * @param phone
     * @return
     */
    int bindPhone(@Param("userId")String userId,@Param("phone")String phone,@Param("password")String password)throws Exception;

    /**
     * 绑定微信
     * @param userId
     * @param weChat
     * @return
     */
    int bindWeChat(@Param("userId")String userId,@Param("weChat")String weChat)throws Exception;

    /**
     * 删除已有微信账户
     * @param weChat
     * @return
     */
    int deleWeChat(@Param("weChat")String weChat)throws Exception;

    /**
     * 更新账户类型
     * @param userId
     * @param user_type
     * @return
     */
    int updateUserType(@Param("userId")String userId,@Param("user_type")String user_type)throws Exception;

}
