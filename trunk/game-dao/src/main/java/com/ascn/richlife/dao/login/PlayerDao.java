package com.ascn.richlife.dao.login;

import com.ascn.richlife.model.login.Player;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 玩家信息
 *
 * Created by zhenhb on 2018/6/14.
 * @zhenhb
 */
@Repository
public interface PlayerDao {
    /**
     * 获取玩家的昵称
     *
     * @param id
     * @return
     */
    String getPlayerName(@Param("id")String id) throws Exception;

    /**
     * 获取玩家的信息
     *
     * @param id
     * @return
     * @throws Exception
     */
    Player getPlayerInfo(@Param("id")String id) throws Exception;

    /**
     * 通过用户Id查询角色
     * @param id
     * @return
     */
    Player findPlayerByUserId(@Param("id")String id)throws Exception;

    /**
     * 创建角色信息
     * @param player
     * @return
     */
    int createRoleInfo(Player player)throws Exception;

    /**
     * 查询昵称是否重复
     * @param name
     * @return
     */
    Player findPlayerByName(@Param("name") String name)throws Exception;

    /**
     * 更新玩家登录次数
     * @param gameNumber
     * @param userId
     * @return
     */
    int updateGameNumber(@Param("gameNumber")int gameNumber,@Param("userId")String userId)throws Exception;

    /**
     * 上传头像
     * @param avatar
     * @param playerId
     * @return
     */
    int uploaderAvatar(@Param("avatar")String avatar,@Param("playerId")String playerId)throws Exception;

    /**
     * 编辑用户信息
     * @param player
     * @return
     */
    int updatePlayer(Player player);

}
