package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.Room;
import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.model.role.CurrentRole;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.server.group.*;
import com.ascn.richlife.server.handler.role.RoleDataManageInfoHandler;
import com.ascn.richlife.server.message.MessageStatus;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.CardType;
import com.ascn.richlife.server.protocol.ConnectionStatus;
import com.ascn.richlife.server.protocol.ProtoIds;
import com.ascn.richlife.server.protocol.RoomStatus;
import com.ascn.richlife.server.util.BuildResponseMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.*;
import java.util.concurrent.*;

/**
 * 玩家掉线处理工具类
 * <p>
 * Created by Administrator on 2017/9/19 0019.
 *
 * @author zhenhb
 */
@Component
public class DisconnectedHandler {

    @Resource
    private GameHandler gameHandler;

    @Resource
    private RoleDataManageInfoHandler roleDataManageInfoHandler;

    private static DisconnectedHandler factory;

    @PostConstruct
    public void init() {
        factory = this;
    }

    private static final String CURRENT_ROLE = "currentRole";

    public static DisconnectedHandler getFactory() {
        return factory;
    }

    //日志
    private static Logger logger = Logger.getLogger(DisconnectedHandler.class);

    /**
     * 大厅中断线处理
     *
     * @param playerId
     */
    public static void disconnectedOnLine(String playerId) {

        logger.debug("大厅断线");

        //清除玩家的连接
        ClientConnectionManager.removeConnection(playerId);

    }

    /**
     * 在房间中断线处理
     *
     * @param roomId
     * @param playerId
     */
    public static void disconnectedInRoom(String roomId, String playerId) {
        logger.debug("断线处理");

        //移除掉线玩家当前房间
        RoomManager.removeRoleFromRoom(roomId, playerId);

        List<String> players = RoomManager.getPlayers(roomId);

        if (players.size() != 0) {

            //清除掉线玩家的连接
            ClientConnectionManager.removeConnection(playerId);

            //构建信息
            NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INROOM, playerId, MessageStatus.SUCCESS, getRoomInfo(roomId, playerId));

            ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
        } else {

            logger.debug("开启保留房间5分钟");

            ResourceBundle resourceBundle = ResourceBundle.getBundle("saveRoomTime");

            String time = resourceBundle.getString("time");

            final long timeInterval = Long.parseLong(time);

            ScheduledExecutorService scheduledExecutorService = Executors.newScheduledThreadPool(5);

            ScheduledFuture scheduledFuture = scheduledExecutorService.schedule(new Callable<Object>() {
                @Override
                public Object call() throws Exception {
                    //解散房间
                    if (RoomManager.getPlayers(roomId).size() == 0) {
                        if (RoomManager.containsKey(roomId)) {
                            RoomManager.removeRoom(roomId);
                        }
                    }
                    return null;
                }
            }, timeInterval, TimeUnit.SECONDS);
        }
    }

    /**
     * 选择角色中断线处理
     *
     * @param roomId
     * @param playerId
     */
    public static void disconnectedChooseRole(String roomId, String playerId) {

        logger.debug("选择角色处理");

        //有人选择角色掉线返回房间中更新状态
        RoomManager.updateRoomStatus(roomId, RoomStatus.IN_ROOM);

        RoomManager.removeRoleFromRoom(roomId, playerId);

        List<String> players = RoomManager.getPlayers(roomId);

        for (String player : players) {
            ClientConnectionManager.updateStatus(player, ConnectionStatus.IN_THE_ROOM);
        }

        ChooseRoleManager.removeData(roomId);

        ClientConnectionManager.removeConnection(playerId);

        //构建信息
        NettyMessage nettyMessage = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INCHOOSEROLE, playerId, MessageStatus.SUCCESS, getRoomInfo(roomId, playerId));

        ClientConnectionManager.sendMessage(players, nettyMessage);

    }

    /**
     * 在游戏初始化中断线处理
     *
     * @param roomId
     * @param playerId
     */
    public static void disconnectedInGame(String roomId, String playerId) {

        logger.debug("初始化断线处理");

        removeRole(roomId, playerId);

        //房间不存在
        if (!RoomManager.containsKey(roomId)) {
            return;
        }

        //当前房间中的人数
        List<String> players = RoomManager.getPlayers(roomId);

        //构建信息
        NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INGAME, playerId, MessageStatus.SUCCESS, new HashMap());

        //发送断线讯息
        ClientConnectionManager.sendMessage(players, message);

        roleInitComplete(roomId, playerId, players);

    }

    /**
     * 在游戏进行中掉线处理
     *
     * @param roomId
     * @param playerId
     */
    public static void disconnectedInGameing(String roomId, String playerId) {

        logger.debug("在游戏进行中掉线");

        ClientConnectionManager.removeConnection(playerId);

        removeRole(roomId, playerId);

        String triggerCardPlayerId;

        //获取触发卡牌的玩家
        if (RoomManager.containsKey(roomId)) {
            triggerCardPlayerId = GameManager.getGame(roomId).getTriggerCardPlayerId();
        } else {
            return;
        }

        Map<String, Object> data = new HashMap<>();

        if (GameManager.getGame(roomId).getCurrentCardType() == CardType.giveChild) {
            logger.debug("发红包断线");
            if (playerId.equals(triggerCardPlayerId) && GameManager.getGame(roomId).getTriggerRedEnvelopesRole() == null) {
                logger.debug("触发卡牌但未购买生孩子卡牌掉线");

                data.put("currentRole", getCurrentRole(roomId));

                //构建发送的数据信息
                NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INGAMEING, playerId, MessageStatus.SUCCESS, data);

                //当前房间中的人数
                List<String> players = RoomManager.getPlayers(roomId);

                ClientConnectionManager.sendMessage(players, message);

            } else if (playerId.equals(triggerCardPlayerId) && GameManager.getGame(roomId).getTriggerRedEnvelopesRole() != null) {
                logger.debug("触发卡牌并且购买生孩子卡牌掉线");

                data.put("currentRole", getCurrentRole(roomId));

                //构建发送的数据信息
                NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INGAMEING, playerId, MessageStatus.SUCCESS, data);

                //当前房间中的人数
                List<String> players = RoomManager.getPlayers(roomId);

                ClientConnectionManager.sendMessage(players, message);

                updateCurrentRoleData(roomId);
            } else {
                logger.debug("非触发卡牌的人掉线");

                data.put("data", null);

                //构建发送的数据信息
                NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INGAMEING, playerId, MessageStatus.SUCCESS, data);

                //当前房间中的人数
                List<String> players = RoomManager.getPlayers(roomId);

                ClientConnectionManager.sendMessage(players, message);

                defaultSendRedEnvelope(roomId, playerId, 0, GameManager.getGame(roomId).getTriggerRedEnvelopesRole());

            }

        } else if (GameManager.getGame(roomId).getCurrentCardType() == CardType.outFate || GameManager.getGame(roomId).getCurrentCardType() == CardType.smallChance_stock) {
            logger.debug("多人回合断线");
            if (playerId.equals(triggerCardPlayerId)) {
                logger.debug("触发卡牌的人断线");

                data.put("data", "");

                //构建发送的数据信息
                NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INGAMEING, playerId, MessageStatus.SUCCESS, data);

                //当前房间中的人数
                List<String> players = RoomManager.getPlayers(roomId);

                ClientConnectionManager.sendMessage(players, message);

                isAllRoundEnd(roomId, playerId);
            } else {
                logger.debug("非触发卡牌的人掉线");

                if (playerId.equals(GameManager.getGame(roomId).getCurrentRole())) {

                    logger.debug("准备触发卡牌的人掉线");

                    data.put("currentRole", getCurrentRole(roomId));

                    //构建发送的数据信息
                    NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INGAMEING, playerId, MessageStatus.SUCCESS, data);

                    //当前房间中的人数
                    List<String> players = RoomManager.getPlayers(roomId);

                    ClientConnectionManager.sendMessage(players, message);
                } else {
                    logger.debug("非准备触发卡牌的人掉线");
                    data.put("data", "");

                    //构建发送的数据信息
                    NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INGAMEING, playerId, MessageStatus.SUCCESS, data);

                    //当前房间中的人数
                    List<String> players = RoomManager.getPlayers(roomId);

                    ClientConnectionManager.sendMessage(players, message);

                    isAllRoundEnd(roomId, playerId);
                }

            }

        } else {
            logger.debug("单人回合断线");
            if (playerId.equals(GameManager.getGame(roomId).getCurrentRole())) {

                logger.debug("持有骰子的人掉线");

                data.put("currentRole", getCurrentRole(roomId));

                //构建发送的数据信息
                NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INGAMEING, playerId, MessageStatus.SUCCESS, data);

                //当前房间中的人数
                List<String> players = RoomManager.getPlayers(roomId);

                ClientConnectionManager.sendMessage(players, message);

            } else {
                logger.debug("非持有骰子的人掉线");

                data.put("data", null);

                //构建发送的数据信息
                NettyMessage message = BuildResponseMessage.build(ProtoIds.DISCONNECTED_INGAMEING, playerId, MessageStatus.SUCCESS, data);

                //当前房间中的人数
                List<String> players = RoomManager.getPlayers(roomId);

                ClientConnectionManager.sendMessage(players, message);

            }
        }

        //重置获取红包的玩家
        GameManager.getGame(roomId).setTriggerRedEnvelopesRole(null);

    }

    /**
     * 判断多人回合是否结束
     *
     * @param roomId
     * @param playerId
     */
    public static void isAllRoundEnd(String roomId, String playerId) {

        for (Map.Entry<String, Boolean> entry : GameManager.getRoundEndData(roomId).entrySet()) {

            if (!entry.getValue()) {
                return;
            }
        }

        //检测是否所有人的回合都已经结束
        logger.debug("房间当前的人数" + GameManager.getRoundEndData(roomId));

        Map<String, Object> data = new HashMap<String, Object>(16);

        int ifPromotion = -1;

        RoleInfo roleInfo = GameManager.getRoleInfo(roomId, playerId);

        int nonLaborIncome = roleInfo.getRoleDataManageInfo().getNonLaborIncome();

        int totalSpending = roleInfo.getRoleDataManageInfo().getTotalSpending();

        if (nonLaborIncome > totalSpending) {
            if (roleInfo.getRoleDebtsInfo().getAddNewDebt().size() > 0) {
                ifPromotion = 2;
            } else {
                ifPromotion = 0;
                DisconnectedHandler.getFactory().gameHandler.initInRouletteInfo(roomId, playerId);
            }

        } else {
            ifPromotion = 1;

        }

        data.put("currentRole", getCurrentRole(roomId));

        data.put("nowPlayerId", playerId);

        data.put("ifPromotion", ifPromotion);

        GameManager.getGame(roomId).setCurrentCardType(0);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), BuildResponseMessage.build(ProtoIds.ALL_ROUND_END, "", 1, data));
    }

    /**
     * 游戏初始化完成断线
     *
     * @param roomId
     * @param players
     */
    private static void roleInitComplete(String roomId, String playerId, List players) {

        Map<String, Boolean> roleInitData = GameManager.getRoleInitData(roomId);

        //移除断线玩家初始化状态
        if (roleInitData.containsKey(playerId)) {
            GameManager.removeRoleInRoleInit(roomId, playerId);
            logger.debug("移除断线玩家初始化状态" + playerId);
        }

        //修改初始化掉线玩家连接状态为游戏中
        ClientConnectionManager.updateStatus(playerId, ConnectionStatus.IN_THE_GAMEING);

        for (Map.Entry<String, Boolean> entry : roleInitData.entrySet()) {
            if (!entry.getValue()) {
                return;
            }
        }

        //修改剩余玩家进入游戏的连接状态
        Map<String, Player> map = RoomManager.getRoom(roomId).getPlayers();
        for (Map.Entry<String, Player> entry : map.entrySet()) {

            //1表示在线玩家
            if ("1".equals(entry.getValue().getIfBreak())) {
                ClientConnectionManager.updateStatus(entry.getKey(), ConnectionStatus.IN_THE_GAMEING);
            }
        }

        Map<String, Object> data = new HashMap<>();

        String currentRole = getCurrentRole(roomId);

        data.put("currentRole", currentRole);

        GameManager.updateCurrentRole(roomId, currentRole);

        GameManager.getGame(roomId).setTriggerCardPlayerId(currentRole);

        logger.debug("当前掷骰子的角色id" + currentRole);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.GAME_INIT_OK, "", 1, data);

        ClientConnectionManager.sendMessage(players, message);
    }

    /**
     * 获取掉线后该房间信息
     *
     * @param roomId
     * @param playerId
     * @return
     */
    private static Map<String, Object> getRoomInfo(String roomId, String playerId) {

        Room room = RoomManager.getRoom(roomId);

        Map<String, Object> data = new HashMap<>();
        data.put("rooId", room.getRoomId());

        Map<String, Player> players = room.getPlayers();
        players.remove(playerId);

        List<Player> players1 = new LinkedList<>();

        for (Map.Entry<String, Player> entry : players.entrySet()) {
            players1.add(entry.getValue());
        }
        data.put("players", players1);

        Map<String, Boolean> readyStatus = room.getReadyStatus();
        readyStatus.remove(playerId);

        data.put("readyStatus", readyStatus);
        return data;
    }

    /**
     * 获取下一个掷骰子的角色
     *
     * @param roomId
     * @return
     */
    private static String getCurrentRole(String roomId) {

        Queue<CurrentRole> roleOrder = GameManager.getRoleOrder(roomId);

        String currentRole;

        while (true) {
            if ("1".equals(roleOrder.peek().getStatus())) {
                currentRole = roleOrder.poll().getPlayerId();
                roleOrder.add(new CurrentRole(currentRole, "1"));
                GameManager.getGame(roomId).setCurrentRole(currentRole);
                break;
            } else {
                CurrentRole currentRole1 = roleOrder.poll();
                roleOrder.add(currentRole1);
            }
        }
        return currentRole;

    }

    /**
     * 更新收红包玩家数据
     *
     * @param roomId
     */
    public static void updateCurrentRoleData(String roomId) {

        String targetPlayerId = GameManager.getGame(roomId).getTriggerRedEnvelopesRole();

        //检测是否所有玩家都发送完毕
        for (Map.Entry<String, Boolean> entry : GameManager.getSendRedEnvelopeData(roomId).entrySet()) {

            if (entry.getKey().equals(targetPlayerId)) {
                continue;
            }

            if (!entry.getValue()) {
                return;
            }
        }

        int totalMoney = 0;

        //获取玩家的金额
        for (Map.Entry<String, Integer> entry : GameManager.getGame(roomId).getSendRedEnvelopeMoney().entrySet()) {

            if (entry.getKey().equals(targetPlayerId)) {
                continue;
            }
            totalMoney += entry.getValue();

        }

        //更新收红包玩家的金币
        RoleInfo targetRoleAgent = GameManager.getRoleInfo(roomId, targetPlayerId);

        getFactory().roleDataManageInfoHandler.updateRoleCash(targetRoleAgent.getRoleDataManageInfo(), Math.abs(totalMoney));

        Map<String, Object> targetData = new HashMap<String, Object>();

        targetData.put("roleDataManageInfo", targetRoleAgent.getRoleDataManageInfo());

        targetData.put("redEnvelopeInfo", GameManager.getSendRedEnvelopeMoney(roomId));

        NettyMessage nettyMessage = BuildResponseMessage.build(ProtoIds.SEND_RED_ENVELOPE, targetPlayerId, 1, targetData);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), nettyMessage);

        //重置角色的红包
        List<String> playerList = RoomManager.getPlayers(roomId);

        Map<String, Boolean> sendRedEnvelopeData = new HashMap<>();

        for (String playerId1 : playerList) {

            sendRedEnvelopeData.put(playerId1, false);
        }

        //重置触发发红包的玩家
        GameManager.getGame(roomId).setTriggerRedEnvelopesRole(null);

    }

    /**
     * 游戏开始后当前房间中移除所有关于掉线玩家的判断
     *
     * @param roomId
     * @param playerId
     */
    public static void removeRole(String roomId, String playerId) {

        //角色顺序中移除
        Queue<CurrentRole> roleOrder = GameManager.getRoleOrder(roomId);
        for (CurrentRole currentRole : roleOrder) {
            if (currentRole.getPlayerId().equals(playerId)) {

                //修改当前角色的状态为0为断线
                currentRole.setStatus("0");
                logger.debug("角色顺序中移除" + playerId);
            }
        }

        //当前房间移除掉线玩家
        if (RoomManager.getRoom(roomId).getPlayers().containsKey(playerId)) {
            RoomManager.removeRoleFromRoom(roomId, playerId);
            logger.debug("移除掉线玩家后剩余玩家" + RoomManager.getPlayers(roomId));
        }

        //移除掉线玩家从多人回合
        if (GameManager.getGame(roomId).getRoundEndData().containsKey(playerId)) {
            GameManager.removeRoleFromAllRoundEnd(roomId, playerId);
            logger.debug("多人回合移除后剩余玩家" + GameManager.getRoundEndData(roomId));
        }

        //移除掉线玩家从发红包
        if (GameManager.getGame(roomId).getSendRedEnvelopeData().containsKey(playerId)) {
            GameManager.removeRoleFromSendRedEnvelop(roomId, playerId);
            logger.debug("发红包移除后剩余玩家" + GameManager.getSendRedEnvelopeData(roomId));
        }

        logger.debug("房间中人数" + RoomManager.getPlayers(roomId));

        //当前房间中掉线后人数为0，解散房间
        if (RoomManager.getPlayers(roomId).size() == 0) {

            if (RoomManager.getRoom(roomId).getReconnectPlayerId().size() != 0) {
                logger.debug("发送消息");
                Map<String, Object> data = new HashMap<>();
                data.put("status", 1);
                NettyMessage message = BuildResponseMessage.build(ProtoIds.RECONNECTION_READYINROOM, playerId, MessageStatus.SUCCESS, data);

                ClientConnectionManager.sendMessage(RoomManager.getRoom(roomId).getReconnectPlayerId(), message);

                for (String player : RoomManager.getRoom(roomId).getReconnectPlayerId()) {
                    ClientConnectionManager.updateStatus(player, ConnectionStatus.ON_LINE);
                }
            } else {
                ClientConnectionManager.updateStatus(playerId, ConnectionStatus.IN_THE_GAMEING);
            }

            logger.debug("房间解散");
            RoomManager.removeRoom(roomId);
            GameManager.removeGame(roomId);
        }

    }

    /**
     * 掉线发红包默认值
     *
     * @param roomId
     * @param playerId
     * @param money
     * @param targetPayerId
     */
    public static void defaultSendRedEnvelope(String roomId, String playerId, int money, String targetPayerId) {

        logger.debug(playerId + "发送红包金额:" + money + " 目标id:" + targetPayerId);

        //检测是否所有玩家都发送完毕
        for (Map.Entry<String, Boolean> entry : GameManager.getSendRedEnvelopeData(roomId).entrySet()) {

            if (entry.getKey().equals(targetPayerId)) {
                continue;
            }

            if (!entry.getValue()) {
                return;
            }
        }

        int totalMoney = 0;

        //获取玩家的金额
        for (Map.Entry<String, Integer> entry : GameManager.getGame(roomId).getSendRedEnvelopeMoney().entrySet()) {

            if (entry.getKey().equals(targetPayerId)) {
                continue;
            }
            totalMoney += entry.getValue();

        }

        //更新收红包玩家的金币
        RoleInfo targetRoleAgent = GameManager.getRoleInfo(roomId, targetPayerId);

        getFactory().roleDataManageInfoHandler.updateRoleCash(targetRoleAgent.getRoleDataManageInfo(), Math.abs(totalMoney));

        Map<String, Object> targetData = new HashMap<String, Object>();

        targetData.put("roleDataManageInfo", targetRoleAgent.getRoleDataManageInfo());

        targetData.put("redEnvelopeInfo", GameManager.getSendRedEnvelopeMoney(roomId));

        NettyMessage nettyMessage = BuildResponseMessage.build(ProtoIds.SEND_RED_ENVELOPE, targetPayerId, 1, targetData);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), nettyMessage);

        //重置角色的红包
        List<String> players = RoomManager.getPlayers(roomId);

        Map<String, Boolean> sendRedEnvelopeData = new HashMap<>();

        for (String playerId1 : players) {

            sendRedEnvelopeData.put(playerId1, false);
        }

        //重置触发发红包的玩家
        GameManager.getGame(roomId).setTriggerRedEnvelopesRole(null);

    }

}