package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.*;
import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.server.data.RoleData;
import com.ascn.richlife.server.group.ClientConnectionManager;
import com.ascn.richlife.server.group.RoomManager;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.ConnectionStatus;
import com.ascn.richlife.server.protocol.FriendFightMessage;
import com.ascn.richlife.server.message.MessageStatus;
import com.ascn.richlife.server.protocol.ProtoIds;
import com.ascn.richlife.server.protocol.RoomStatus;
import com.ascn.richlife.server.util.BuildResponseMessage;
import com.ascn.richlife.service.login.PlayerService;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 好友对战处理工具类
 *
 * Created by zhangpengxiang on 17/5/27.
 */
@Component("friendFightHandler")
public class FriendFightHandler {

    //日志
    private Logger logger = Logger.getLogger(FriendFightHandler.class);

    @Autowired
    private PlayerService playerService;

    @Autowired
    private ChooseRoleHandler chooseRoleHandler;

    /**
     * 创建房间 
     *
     * @param playerId
     */
    public void createRoom(String playerId) throws Exception {

        Room room = initRoom();

        Player player = playerService.getPlayerInfo(playerId);

        room.getPlayers().put(playerId, player);

        room.getReadyStatus().put(playerId, false);

        RoomManager.addRoom(room);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.CREATE_ROOM, playerId, MessageStatus.SUCCESS, getRoomInfo(room.getRoomId()));

        ClientConnectionManager.sendMessage(playerId, message);

        ClientConnectionManager.updateStatus(playerId, ConnectionStatus.IN_THE_ROOM);

        ClientConnectionManager.updateRoomId(playerId, room.getRoomId());
        
        logger.debug(player.getName() + "创建了一个房间,房间号:" + room.getRoomId());
    }

    /**
     * 加入房间
     *
     * @param playerId
     * @param roomId
     * @throws Exception
     */
    public void joinRoom(String playerId, String roomId)  {

        if (!roomIfExist(roomId, playerId, ProtoIds.JOIN_ROOM)) {
            return;
        }

        if(RoomManager.getRoom(roomId).getRoomStatus() == RoomStatus.IN_GAMEING){

            NettyMessage message = BuildResponseMessage.build(ProtoIds.JOIN_ROOM, playerId, -2, null);

            ClientConnectionManager.sendMessage(playerId, message);
            return;
        }

        Room room = RoomManager.getRoom(roomId);

        Player player = null;
        try {
            player = playerService.getPlayerInfo(playerId);
        } catch (Exception e) {
            e.printStackTrace();
        }

        room.getPlayers().put(playerId, player);

        room.getReadyStatus().put(playerId, false);

        ClientConnectionManager.updateStatus(playerId, ConnectionStatus.IN_THE_ROOM);

        ClientConnectionManager.updateRoomId(playerId, roomId);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.JOIN_ROOM, playerId, MessageStatus.SUCCESS, getRoomInfo(roomId));

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        logger.debug(player.getName() + "加入 " + roomId + " 房间");

    }

    /**
     * 退出房间
     *
     * @param playerId
     * @param roomId
     */
    public void exitRoom(String playerId, String roomId) {

        if (!roomIfExist(roomId, playerId, ProtoIds.EXIT_ROOM)) {
            return;
        }

        Room room = RoomManager.getRoom(roomId);

        logger.debug(room.getPlayers().get(playerId) + "退出房间,房间号：" + roomId);

        room.getPlayers().remove(playerId);

        //修改为在线状态
        ClientConnectionManager.updateStatus(playerId,ConnectionStatus.ON_LINE);
        //修改房间为初始值
        ClientConnectionManager.updateRoomId(playerId,null);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.EXIT_ROOM, playerId, MessageStatus.SUCCESS, getRoomInfo(roomId));

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        ClientConnectionManager.sendMessage(playerId, message);

    }

    /**
     * 玩家准备
     *
     * @param playerId
     * @param roomId
     */
    public void playerReady(String playerId, String roomId) {

        if (!roomIfExist(roomId, playerId, ProtoIds.PLAYER_READY_ROOM)) {
            return;
        }

        Room room = RoomManager.getRoom(roomId);

        room.getReadyStatus().put(playerId, true);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.PLAYER_READY_ROOM, playerId, MessageStatus.SUCCESS, getRoomInfo(roomId));

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        autoStartGame(playerId, roomId);

    }

    /**
     * 所有人准备好后自动开始选择角色
     *
     * @param playerId
     * @param roomId
     */
    public void autoStartGame(String playerId, String roomId) {

        Room room = RoomManager.getRoom(roomId);

        //检测人数是否是四个人
        if (room.getPlayers().size() != 4) {

            NettyMessage message = BuildResponseMessage.build(ProtoIds.START_CHOOSE_ROLE, playerId, FriendFightMessage.PLAYERS_NOT_ENOUGH, null);

            ClientConnectionManager.sendMessage(playerId, message);

            return;

        }

        //检测是否所有人都准备
        for (Map.Entry<String, Boolean> players : room.getReadyStatus().entrySet()) {

            if (!players.getValue()) {

                NettyMessage message = BuildResponseMessage.build(ProtoIds.START_CHOOSE_ROLE, playerId, FriendFightMessage.PLAYERS_NOT_READY, null);

                ClientConnectionManager.sendMessage(playerId, message);

                return;
            }

        }

        Map<String, Object> data = new HashMap<>();

        data.put("roleData", RoleData.roleData.values().toArray());

        chooseRoleHandler.initChooseRoleData(roomId, RoomManager.getPlayers(roomId));

        NettyMessage message = BuildResponseMessage.build(ProtoIds.START_CHOOSE_ROLE, playerId, MessageStatus.SUCCESS, data);

        List<String> players = RoomManager.getPlayers(roomId);

        for (String player : players) {
            ClientConnectionManager.updateStatus(player, ConnectionStatus.CHOOSE_ROLE);
        }

        //更新进入选择角色房间的状态
        RoomManager.updateRoomStatus(roomId, RoomStatus.IN_CHOOSEROLE);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        //开启任务
        /*ChooseRoleCountdown.addTask(new ChooseRoleCountdownTask(roomId));*/

    }

    /**
     * 判断房间是否存在
     *
     * @param roomId
     * @param playerId
     * @param type
     * @return
     */
    private boolean roomIfExist(String roomId, String playerId, int type) {

        if (RoomManager.containsKey(roomId)) {
            return true;
        } else {
            logger.error("房间号不存在");

            NettyMessage message = BuildResponseMessage.build(type, playerId, MessageStatus.FAILURE, null);
            ClientConnectionManager.sendMessage(playerId, message);

            return false;
        }

    }

    /**
     * 初始化房间
     *
     * @return
     */
    public Room initRoom() {

        Room room = new Room();

        //获取一个房间号
        String roomId = generateRoomId();

        //初始化玩家容器
        Map<String, Player> players = new LinkedHashMap<>();

        //玩家准备的状态
        Map<String, Boolean> readyStatus = new ConcurrentHashMap<>();

        //重连的玩家
        List<String> reconnectionRole = new LinkedList<>();

        //房间玩家退出房间的信息
        Map<String,Boolean> exitRoom = new ConcurrentHashMap<>();

        room.setReconnectPlayerId(reconnectionRole);

        room.setRoomId(roomId);

        room.setExitRoom(exitRoom);

        room.setPlayers(players);

        room.setReadyStatus(readyStatus);

        //初始化当前房间的状态
        room.setRoomStatus(RoomStatus.IN_ROOM);

        return room;
    }

    /**
     * 获取房间信息
     *
     * @param roomId
     * @return
     */
    public Map<String, Object> getRoomInfo(String roomId) {

        Room room = RoomManager.getRoom(roomId);

        Map<String, Object> data = new HashMap<>();

        //房间号
        data.put("roomId", room.getRoomId());

        //玩家
        data.put("players", room.getPlayers().values().toArray());

        //玩家准备状态
        data.put("readyStatus", room.getReadyStatus());

        //观战列表
        //data.put("audiences", room.getAudiences());

        return data;
    }

    /**
     * 生成房间号
     *
     * @return
     */
    private String generateRoomId() {

        //房间号
        String roomId = String.valueOf((int) ((Math.random() * 9 + 1) * 100000));

        //如果生成了重复的房间号码则继续生成
        if (RoomManager.containsKey(roomId)) {
            roomId = generateRoomId();
        }

        return roomId;
    }


}
