package com.ascn.richlife.service.login.impl;

import com.ascn.richlife.dao.login.UserDao;
import com.ascn.richlife.model.login.User;
import com.ascn.richlife.service.login.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * 游戏用户操作服务
 *
 * Created by zhenhb on 2018/6/14 0014.
 * @author zhenhb
 */
@Service("userService")
public class UserServiceImp implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public boolean register(User user) throws Exception {
        int result = 0;
            result = userDao.insertUser(user);
        if (result > 0) {
            return true;
        }
        return false;
    }

    @Override
    public boolean alterPwd(User user) throws Exception{
        int result = userDao.setPwd(user);
        if (result > 0) {
            return true;
        }
        return false;
    }
    @Override
    public boolean login(String phone, String password) throws Exception{
        User user = userDao.login(phone, password);
        if(user!=null){
            return true;
        }
        return false;
    }
    @Override
    public User findUserByPhone(String phone) throws Exception{
        User user = userDao.findUserByPhone(phone);
        return user;
    }

    @Override
    public User findUserByWeChatId(String weChatId) throws Exception{
        User user = userDao.findUserByWeChatId(weChatId);
        return user;
    }

    @Override
    public boolean bindPhone(String userId, String phone,String password) throws Exception{
        int result = userDao.bindPhone(userId,phone,password);
        if(result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean bindWeChat(String userId, String weChat) throws Exception{
        int result = userDao.bindWeChat(userId,weChat);
        if(result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean deleWeChat(String weChat) throws Exception{
        int result = userDao.deleWeChat(weChat);
        if(result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updateUserType(String userId, String user_type) throws Exception{
        if(userDao.updateUserType(userId,user_type)>0){
            return true;
        }
        return false;
    }
}
