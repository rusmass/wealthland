package com.ascn.richlife.service.login.impl;

import com.ascn.richlife.dao.login.InspirationDao;
import com.ascn.richlife.model.login.Inspiration;
import com.ascn.richlife.service.login.InspirationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 梦想板服务
 *
 * Created by Administrator on 2017/3/15 0015.
 */
@Service("inspirationService")
public class InspirationServiceImp implements InspirationService {

    @Autowired
    private InspirationDao InspirationDao;

    /**
     * 玩家感悟添加
     *
     * @param content
     * @param nickName
     * @param avatar
     * @return
     */
    public boolean inspiration(String content, String nickName,String avatar) throws Exception {
        int result = InspirationDao.Inspiration(content, nickName, avatar);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 分页查询
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<Inspiration> findPage(int start, int end) {
        return InspirationDao.findPage(start,end);
    }

    /**
     * 查询所有的记录
     * @return
     */
    @Override
    public int findCount(String nickName) {
        return InspirationDao.findCount(nickName);
    }

    /**
     * 分页查询通过昵称
     * @param nickName
     * @param start
     * @param end
     * @return
     */
    @Override
    public List<Inspiration> findByNickName(String nickName, int start, int end) {
        return InspirationDao.findByNickName(nickName,start,end);
    }
}
