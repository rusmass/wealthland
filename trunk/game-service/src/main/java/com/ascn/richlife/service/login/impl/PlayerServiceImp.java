package com.ascn.richlife.service.login.impl;

import com.ascn.richlife.dao.login.PlayerDao;
import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.service.login.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * 玩家信息获取服务
 *
 * Created by Administrator on 2017/2/24 0024.
 */
@Service("playerService")
public class PlayerServiceImp implements PlayerService {

    @Autowired
    private PlayerDao playerDao;

    @Override
    public String getPlayerName(String id) throws Exception {
        return playerDao.getPlayerName(id);
    }

    @Override
    public Player getPlayerInfo(String id) throws Exception {
        return playerDao.getPlayerInfo(id);
    }

    /**
     * 通过用户ID查询角色
     * @param id
     * @return
     */
    @Override
    public Player findPlayerByUserId(String id) throws Exception{
        Player player = playerDao.findPlayerByUserId(id);
        return player;
    }

    /**
     * 创建角色
     *
     * @param player
     * @return
     */
    @Override
    public boolean createRole(Player player) throws Exception{
        int result = playerDao.createRoleInfo(player);
        if (result > 0) {
            return true;
        }
        return false;
    }

    /**
     * 查询昵称是否重复
     *
     * @param name
     * @return
     */
    @Override
    public Boolean findPlayerByName(String name) throws Exception{
        Player player = playerDao.findPlayerByName(name);
        if (player == null) {
            return false;
        }
        return true;
    }

    /**
     * 更新登录次数
     * @param gameNumber
     * @param userId
     * @return
     */
    @Override
    public boolean updateGameNumber(int gameNumber, String userId) throws Exception{
        int result = playerDao.updateGameNumber(gameNumber,userId);
        if(result>0){
            return true;
        }
        return false;
    }

    /**
     * 上传头像
     * @param avatar
     * @param playerId
     * @return
     */
    @Override
    public boolean uploaderAvatar(String avatar, String playerId) throws Exception{
        int result = playerDao.uploaderAvatar(avatar,playerId);
        if(result>0){
            return true;
        }
        return false;
    }

    @Override
    public boolean updatePlayer(Player player) {
        int result = playerDao.updatePlayer(player);
        if(result>0){
            return true;
        }
        return false;
    }

}
