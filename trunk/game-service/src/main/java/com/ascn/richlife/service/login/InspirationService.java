package com.ascn.richlife.service.login;

import com.ascn.richlife.model.login.Inspiration;

import java.util.List;

/**
 * 梦想板服务
 *
 * Created by Administrator on 2017/3/15 0015.
 */
public interface InspirationService {

    /**
     * 玩家反馈
     * @param content
     * @param nickName
     * @param avatar
     * @return
     */
    boolean inspiration(String content,String nickName,String avatar)throws Exception;

    /**
     * 分页查询感悟
     * @param start
     * @param end
     * @return
     */
    List<Inspiration> findPage(int start,int end);

    /**
     * 查询所有的记录
     * @param nickName
     * @return
     */
    int findCount(String nickName);

    /**
     * 查询个人发布感悟
     * @param nickName
     * @param start
     * @param end
     * @return
     */
    List<Inspiration> findByNickName(String nickName,int start,int end);
}
