package com.ascn.richlife.server.group;

import com.ascn.richlife.model.CardGroup;
import com.ascn.richlife.model.Game;
import com.ascn.richlife.model.Location;
import com.ascn.richlife.model.role.CurrentRole;
import com.ascn.richlife.model.role.RoleInfo;

import java.util.Map;
import java.util.Queue;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 游戏对战逻辑管理
 */
public class GameManager {

    //游戏列表
    private static Map<String, Game> gameMap = new ConcurrentHashMap<>();

    /**
     * 添加一个游戏
     *
     * @param game
     */
    public static void addGame(Game game) {
        gameMap.put(game.getRoomId(), game);
    }

    /**
     * 获取指定的游戏
     *
     * @param roomId
     */
    public static Game getGame(String roomId) {
        return gameMap.get(roomId);
    }

    /**
     * 删除一个游戏
     *
     * @param roomId
     */
    public static void removeGame(String roomId) {
        gameMap.remove(roomId);
    }

    /**
     * 是否有该游戏
     *
     * @param roomId
     * @return
     */
    public static boolean containsKey(String roomId) {
        return gameMap.containsKey(roomId);
    }

    /**
     * 获取指定游戏的玩家初始化数据
     *
     * @param roomId
     * @return
     */
    public static Map<String, Boolean> getRoleInitData(String roomId) {
        if (gameMap.containsKey(roomId)) {
            return getGame(roomId).getRoleInitData();
        }
        throw new RuntimeException("没有该房间");
    }

    /**
     * 获取指定角色的游戏数据
     *
     * @param roomId
     * @param playerId
     * @return
     */
    public static RoleInfo getRoleInfo(String roomId, String playerId) {
        return getGame(roomId).getRoleInfoData().get(playerId);
    }

    /**
     * 获取游戏中所有的角色数据
     *
     * @param roomId
     * @return
     */
    public static Map<String, RoleInfo> getRoleInfoGroup(String roomId) {
        return getGame(roomId).getRoleInfoData();
    }

    /**
     * 获取角色顺序
     *
     * @param roomId
     * @return
     */
    public static Queue<CurrentRole> getRoleOrder(String roomId) {
        return getGame(roomId).getRoleOrder();
    }

    /**
     * 获取角色位置信息
     *
     * @param roomId
     * @return
     */
    public static Map<String, Location> getRoleLocationData(String roomId) {
        return getGame(roomId).getRoleLocationData();
    }

    /**
     * 获取角色掷骰子数
     *
     * @param roomId
     * @return
     */
    public static Map<String, Integer> getRoleRollDiceData(String roomId) {
        return getGame(roomId).getRoleRollDiceNumber();
    }

    /**
     * 获取卡组
     *
     * @param roomId
     * @return
     */
    public static CardGroup getCardGroup(String roomId) {
        return getGame(roomId).getCardGroup();
    }

    /**
     * 获取回合结束数据
     *
     * @param roomId
     * @return
     */
    public static Map<String, Boolean> getRoundEndData(String roomId) {
        return getGame(roomId).getRoundEndData();
    }

    /**
     * 更新角色回合结束数据
     *
     * @param roomId
     * @param roundEndData
     */
    public static void updateRoundEndData(String roomId, Map<String, Boolean> roundEndData) {
        getGame(roomId).setRoundEndData(roundEndData);
    }

    /**
     * 获取角色购买保险数据
     *
     * @param roomId
     */
    public static Map<String, Boolean> getInsuranceData(String roomId) {
        return getGame(roomId).getInsuranceData();
    }

    /**
     * 获取发红包的数据
     *
     * @param roomId
     * @return
     */
    public static Map<String, Boolean> getSendRedEnvelopeData(String roomId) {
        return getGame(roomId).getSendRedEnvelopeData();
    }

    /**
     * 获取发红包的金额
     *
     * @param roomId
     * @return
     */
    public static Map<String, Integer> getSendRedEnvelopeMoney(String roomId) {
        return getGame(roomId).getSendRedEnvelopeMoney();
    }

    /**
     * 重置红包数据
     *
     * @param roomId
     * @param sendRedEnvelopeData
     */
    public static void updateSendRedEnvelopeData(String roomId, Map<String, Boolean> sendRedEnvelopeData) {
        getGame(roomId).setSendRedEnvelopeData(sendRedEnvelopeData);
    }

    /**
     * 重置当前角色
     *
     * @param roomId
     * @param currentRole
     */
    public static void updateCurrentRole(String roomId, String currentRole) {
        getGame(roomId).setCurrentRole(currentRole);
    }

    /**
     * 移除初始化断线的玩家
     *
     * @param roomId
     * @param playerId
     */
    public static void removeRoleInRoleInit(String roomId, String playerId) {
        getRoleInitData(roomId).remove(playerId);
    }

    /**
     * 移除掉线角色多人回合
     *
     * @param roomId
     * @param playerId
     */
    public static void removeRoleFromAllRoundEnd(String roomId, String playerId) {
        getGame(roomId).getRoundEndData().remove(playerId);
    }

    /**
     * 从当前发红包房间移除掉线玩家
     *
     * @param roomId
     * @param playerId
     */
    public static void removeRoleFromSendRedEnvelop(String roomId, String playerId) {
        getGame(roomId).getSendRedEnvelopeData().remove(playerId);
    }

    /**
     * 将掉线玩家重新添加到游戏中发红包、多人回合
     *
     * @param roomId
     * @param playerId
     */
    public static void putRoleInGame(String roomId, String playerId) {
        getGame(roomId).getSendRedEnvelopeData().put(playerId, false);
        getGame(roomId).getRoundEndData().put(playerId, false);
    }

}
