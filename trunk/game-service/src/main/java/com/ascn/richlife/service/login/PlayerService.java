package com.ascn.richlife.service.login;

import com.ascn.richlife.model.login.Player;
import org.springframework.stereotype.Repository;

/**
 * 玩家信息获取服务
 *
 * Created by Administrator on 2017/6/9.
 */
@Repository
public interface PlayerService {

    /**
     * 获取玩家的昵称
     *
     * @param id
     * @return
     */
    String getPlayerName(String id) throws Exception;

    /**
     * 获取玩家的信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    Player getPlayerInfo(String id) throws Exception;

    /**
     * 通过用户id查询角色信息
     * @param id
     * @return
     */
    Player findPlayerByUserId(String id)throws Exception;

    /**
     * 创建角色
     * @param player
     * @return
     */
    boolean createRole(Player player)throws Exception;

    /**
     * 查询昵称是否重复
     * @param name
     * @return
     */
    Boolean findPlayerByName(String name)throws Exception;

    /**
     * 更新登录次数
     * @param gameNumber
     * @param userId
     * @return
     */
    boolean updateGameNumber(int gameNumber, String userId)throws Exception;

    /**
     * 上传头像
     * @param avatar
     * @param playerId
     * @return
     */
    boolean uploaderAvatar(String avatar, String playerId)throws Exception;

    /**
     * 编辑玩家信息
     * @return
     */
    boolean updatePlayer(Player player);

}
