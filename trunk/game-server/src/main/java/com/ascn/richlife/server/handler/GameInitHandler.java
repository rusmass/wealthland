package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.CardGroup;
import com.ascn.richlife.model.Game;
import com.ascn.richlife.model.Location;
import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.model.role.CurrentRole;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.model.role.RoleOperateNum;
import com.ascn.richlife.server.group.*;
import com.ascn.richlife.server.handler.cardgroup.CardGroupHandler;
import com.ascn.richlife.server.handler.role.RoleInitHandler;
import com.ascn.richlife.server.message.MessageStatus;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.ConnectionStatus;
import com.ascn.richlife.server.protocol.GameStatus;
import com.ascn.richlife.server.protocol.ProtoIds;
import com.ascn.richlife.server.util.BuildResponseMessage;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 游戏初始化处理工具类
 *
 * Created by zhangpengxiang on 2017/7/11.
 */
@Component("gameInitHandler")
public class GameInitHandler {

    private Logger logger = Logger.getLogger(GameInitHandler.class);

    @Autowired
    private RoleInitHandler roleInitHandler;

    @Autowired
    private CardGroupHandler cardGroupHandler;

    /**
     * 创建一个游戏
     *
     * @param roomId
     */
    public void createGame(String roomId) throws Exception {

        if (GameManager.containsKey(roomId)) {
            return;
        }

        saveOperationCardRecord(roomId);

        successDataInit(roomId);

        Game game = new Game();

        game.setRoomId(roomId);

        Queue<CurrentRole> queue = initGamePlayerOrder(roomId);
        game.setRoleOrder(queue);

        Map<String, RoleInfo> roleInfoData = initRoleInfoData(roomId);
        game.setRoleInfoData(roleInfoData);

        Map<String, Boolean> roleInitData = initRoleInitData(roomId);
        game.setRoleInitData(roleInitData);

        Map<String, Location> roleLocationData = initRoleLocalData(roomId);
        game.setRoleLocationData(roleLocationData);

        Map<String, Integer> roleRollDice = initRoleRollDiceNumber(roomId);
        game.setRoleRollDiceNumber(roleRollDice);

        Map<String, Boolean> roundEnd = initRoundEndData(roomId);
        game.setRoundEndData(roundEnd);

        Map<String, Boolean> insuranceData = initInsuranceData(roomId);
        game.setInsuranceData(insuranceData);

        Map<String, Boolean> sendRedEnvelopeData = sendRedEnvelopeData(roomId);
        game.setSendRedEnvelopeData(sendRedEnvelopeData);

        Map<String, Integer> sendRedEnvelopeMoney = sendRedEnvelopeMoney(roomId);
        game.setSendRedEnvelopeMoney(sendRedEnvelopeMoney);

        CardGroup cardGroup = cardGroupHandler.init();
        game.setCardGroup(cardGroup);

        game.setStatus(GameStatus.CRATE_GAME);

        GameManager.addGame(game);

    }

    /**
     * 玩家初始化完成
     *
     * @param roomId
     * @param playerId
     */
    public void roleInitComplete(String roomId, String playerId) {

        Map<String, Boolean> roleInitData = GameManager.getRoleInitData(roomId);

        if (!roleInitData.containsKey(playerId)) {
            logger.error("初始化数据不存在玩家id");
            return;
        }

        roleInitData.put(playerId, true);

        //游戏初始化状态
        GameManager.getGame(roomId).setStatus(GameStatus.INIT_GAME);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GAME_INIT_OK, playerId, MessageStatus.SUCCESS, null);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        for (Map.Entry<String, Boolean> entry : roleInitData.entrySet()) {
            if (!entry.getValue()) {
                return;
            }
        }

        allRoleInitComplete(roomId, RoomManager.getPlayers(roomId));

    }

    /**
     * 所有角色初始化完成
     *
     * @param roomId
     * @param players
     */
    public void allRoleInitComplete(String roomId, List<String> players) {

        Map<String, Object> data = new HashMap<>();

        Queue<CurrentRole> roleOrder = GameManager.getRoleOrder(roomId);

        String currentRole;

        while (true) {

            //1为在线
            if ("1".equals(roleOrder.peek().getStatus())) {
                currentRole = roleOrder.poll().getPlayerId();
                roleOrder.add(new CurrentRole(currentRole, "1"));
                break;
            } else {
                CurrentRole currentRole1 = roleOrder.poll();
                roleOrder.add(currentRole1);
            }
        }

        GameManager.updateCurrentRole(roomId, currentRole);

        GameManager.getGame(roomId).setTriggerCardPlayerId(currentRole);

        data.put("currentRole", currentRole);

        //修改剩余玩家进入游戏的连接状态
        Map<String, Player> map = RoomManager.getRoom(roomId).getPlayers();
        for (Map.Entry<String, Player> entry : map.entrySet()) {

            //1表示在线玩家
            if ("1".equals(entry.getValue().getIfBreak())) {
                ClientConnectionManager.updateStatus(entry.getKey(), ConnectionStatus.IN_THE_GAMEING);
            }
        }

        //更新当前的游戏状态
        GameManager.getGame(roomId).setStatus(GameStatus.START_GAMEING);

        //移除之前选择角色的数据
        ChooseRoleManager.removeData(roomId);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GAME_INIT_OK, "", 1, data);

        ClientConnectionManager.sendMessage(players, message);

    }

    /**
     * 初始化游戏顺序
     *
     * @param roomId
     * @return
     */
    private Queue<CurrentRole> initGamePlayerOrder(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);

        //打乱角色的顺序
        Collections.shuffle(playerList);

        Queue<CurrentRole> queue = new LinkedList<>();

        for (String playerId : playerList) {
            CurrentRole currentRole = new CurrentRole(playerId, "1");
            queue.add(currentRole);
        }

        return queue;

    }

    /**
     * 初始化角色数据
     *
     * @param roomId
     * @return
     */
    private Map<String, RoleInfo> initRoleInfoData(String roomId) {

        //获取玩家所选择的角色
        Map<String, Integer> playerRole = ChooseRoleManager.getData(roomId).getPlayerRole();

        Map<String, RoleInfo> roleInfoData = new HashMap<>();

        for (Map.Entry<String, Integer> entry : playerRole.entrySet()) {

            RoleInfo roleInfo = roleInitHandler.roleInit(entry.getValue());

            roleInfoData.put(entry.getKey(), roleInfo);
        }

        return roleInfoData;

    }

    /**
     * 角色初始化数据
     *
     * @param roomId
     * @return
     */
    private Map<String, Boolean> initRoleInitData(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);

        Map<String, Boolean> roleInitData = new HashMap<>();

        for (String playerId : playerList) {
            if (playerId.contains("robot")) {
                roleInitData.put(playerId, true);
            } else {
                roleInitData.put(playerId, false);
            }
        }

        return roleInitData;
    }

    /**
     * 初始化角色在轮盘上的位置信息
     *
     * @param roomId
     * @return
     */
    private Map<String, Location> initRoleLocalData(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);

        Map<String, Location> roleLocationData = new HashMap<>();

        for (String playerId : playerList) {

            Location location = initLocation();
            roleLocationData.put(playerId, location);
        }

        return roleLocationData;
    }

    /**
     * 初始化位置
     *
     * @return
     */
    private Location initLocation() {

        Location location = new Location();

        location.setOutOrIn(0);

        location.setPlaceByTurntable(0);

        location.setOuterTotalPoint(0);

        location.setInnerTotalPoint(0);

        return location;

    }

    /**
     * 初始化角色掷骰子数
     *
     * @param roomId
     * @return
     */
    private Map<String, Integer> initRoleRollDiceNumber(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);
        Map<String, Integer> roleRollDice = new HashMap<>();
        for (String playerId : playerList) {
            roleRollDice.put(playerId, 1);
        }
        return roleRollDice;

    }

    /**
     * 初始化回合结束数据
     *
     * @param roomId
     * @return
     */
    private Map<String, Boolean> initRoundEndData(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);

        Map<String, Boolean> roundEnd = new HashMap<>();

        for (String playerId : playerList) {

            roundEnd.put(playerId, false);
        }
        return roundEnd;
    }

    /**
     * 初始化保险数据
     *
     * @param roomId
     * @return
     */
    private Map<String, Boolean> initInsuranceData(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);

        Map<String, Boolean> insuranceData = new HashMap<>();

        for (String playerId : playerList) {

            insuranceData.put(playerId, false);
        }
        return insuranceData;

    }

    /**
     * 发红包数据
     *
     * @param roomId
     * @return
     */
    private Map<String, Boolean> sendRedEnvelopeData(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);

        Map<String, Boolean> sendRedEnvelopeData = new HashMap<>();

        for (String playerId : playerList) {
            sendRedEnvelopeData.put(playerId, false);
        }

        return sendRedEnvelopeData;

    }

    /**
     * 发送红包的金额
     *
     * @param roomId
     * @return
     */
    private Map<String, Integer> sendRedEnvelopeMoney(String roomId) {

        List<String> playerList = RoomManager.getPlayers(roomId);

        Map<String, Integer> sendRedEnvelopeMoney = new HashMap<>();

        for (String playerId : playerList) {
            sendRedEnvelopeMoney.put(playerId, 0);
        }

        return sendRedEnvelopeMoney;
    }

    /**
     * 初始化卡牌操作
     * @param roomId
     */
    private void saveOperationCardRecord(String roomId){
        List<String> players = RoomManager.getPlayers(roomId);

        //缓存玩家的操作卡牌次数
        for (String playerId : players) {
            RoleOperateNum roleOperateNum = new RoleOperateNum();
            roleOperateNum.setPlayerId(playerId);
            GameStatisticsManager.addRoleOperateNum(roleOperateNum);
        }
    }

    /**
     * 初始化游戏玩家胜利
     * @param roomId
     * @return
     */
    private Map<String,Boolean> successDataInit(String roomId){
        List<String> players = RoomManager.getPlayers(roomId);
        Map<String,Boolean> successData = new HashMap<>(16);
        for(String playerId:players){
            successData.put(playerId,false);
        }
        SuccessDataManager.addSuccessData(roomId,successData);
        return SuccessDataManager.getSuccessData(roomId);
    }

}
