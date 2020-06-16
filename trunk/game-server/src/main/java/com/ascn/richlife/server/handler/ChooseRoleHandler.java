package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.ChooseRoleData;
import com.ascn.richlife.model.role.CurrentRole;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.server.data.RoleCode;
import com.ascn.richlife.server.group.*;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.message.MessageStatus;
import com.ascn.richlife.server.protocol.ConnectionStatus;
import com.ascn.richlife.server.protocol.ProtoIds;
import com.ascn.richlife.server.protocol.RoomStatus;
import com.ascn.richlife.server.util.BuildResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

/**
 *  选择角色处理工具类
 *
 * Created by zhangpengxiang on 2017/6/20.
 */

@Component("chooseRoleHandler")
public class ChooseRoleHandler {

    @Autowired
    private GameInitHandler gameInitHandler;

    /**
     * 初始化选择角色数据
     *
     * @param roomId
     * @param playerIdList
     */
    public void initChooseRoleData(String roomId, List<String> playerIdList) {

        ChooseRoleData chooseRoleData = new ChooseRoleData();

        Map<Integer, Boolean> roleStatus = new ConcurrentHashMap<>();

        //初始化角色选择状态
        for (Integer code : RoleCode.roleCode) {
            roleStatus.put(code, false);
        }

        //初始化玩家选择的角色
        Map<String, Integer> playerRole = new ConcurrentHashMap<>();

        //初始化玩家的准备情况
        Map<String, Boolean> readyStatus = new ConcurrentHashMap<>();

        //初始化玩家选择角色的数据
        for (String playerId : playerIdList) {

            playerRole.put(playerId, 0);

            readyStatus.put(playerId, false);

        }

        chooseRoleData.setRoleStatus(roleStatus);

        chooseRoleData.setPlayerRole(playerRole);

        chooseRoleData.setReadyStatus(readyStatus);

        ChooseRoleManager.addData(roomId, chooseRoleData);

    }

    /**
     * 选择角色
     *
     * @param roomId
     * @param playerId
     * @param roleId
     */
    public void chooseRole(String roomId, String playerId, int roleId) throws Exception {

        //选择角色的数据
        ChooseRoleData chooseRoleData = ChooseRoleManager.getData(roomId);

        //获取角色的状态
        Map<Integer, Boolean> roleStatus = chooseRoleData.getRoleStatus();

        //获取玩家选择的角色
        Map<String, Integer> playerRole = chooseRoleData.getPlayerRole();

        //获取玩家的准备情况
        Map<String, Boolean> readyStatus = chooseRoleData.getReadyStatus();

        //角色是否已经被选择
        if (roleStatus.get(roleId)) {

            NettyMessage message = BuildResponseMessage.build(ProtoIds.CHOOSE_ROLE, playerId, -1, null);
            ClientConnectionManager.sendMessage(playerId, message);
            return;
        }

        //玩家是否重复选择角色
        if (readyStatus.get(playerId)) {
            NettyMessage message = BuildResponseMessage.build(ProtoIds.CHOOSE_ROLE, playerId, -2, null);
            ClientConnectionManager.sendMessage(playerId, message);
            return;
        }

        //更改角色的状态
        roleStatus.put(roleId, true);

        //玩家之前是否选了其他角色
        if (playerRole.get(playerId) == 0) {

            //更改玩家的角色
            playerRole.put(playerId, roleId);
        } else {

            //获取玩家之前选择的角色
            int roleCode = playerRole.get(playerId);

            roleStatus.put(roleCode, false);

            playerRole.put(playerId, roleId);
        }

        Map<String, Object> data = new HashMap<>();

        Map<String, Boolean> roleStatusCopy = new HashMap<>();

        for (Map.Entry<Integer, Boolean> entry : roleStatus.entrySet()) {
            roleStatusCopy.put(entry.getKey().toString(), entry.getValue());
        }

        data.put("roleStatus", roleStatusCopy);

        data.put("playerRole", playerRole);

        data.put("readyStatus", readyStatus);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.CHOOSE_ROLE, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

    }

    /**
     * 取消角色
     *
     * @param roomId
     * @param playerId
     */
    public void cancelRole(String roomId, String playerId) {

        //选择角色的数据
        ChooseRoleData chooseRoleData = ChooseRoleManager.getData(roomId);

        //获取角色的状态
        Map<Integer, Boolean> roleStatus = chooseRoleData.getRoleStatus();

        //获取玩家选择的角色
        Map<String, Integer> playerRole = chooseRoleData.getPlayerRole();

        //获取玩家的准备情况
        Map<String, Boolean> readyStatus = chooseRoleData.getReadyStatus();

        //判断角色有没有被锁定（玩家有没有准备）
        if (readyStatus.get(playerId)) {

            //取消角色失败，玩家已经准备
            NettyMessage message = BuildResponseMessage.build(ProtoIds.CANCEL_ROLE, playerId, -1, null);
            ClientConnectionManager.sendMessage(playerId, message);
            return;

        }

        //获取角色代码
        int roleCode = playerRole.get(playerId);
        if (roleCode == 0) {

            //玩家没有角色
            NettyMessage message = BuildResponseMessage.build(ProtoIds.CANCEL_ROLE, playerId, -2, null);
            ClientConnectionManager.sendMessage(playerId, message);
            return;
        }

        //置空角色
        roleStatus.put(roleCode, false);
        playerRole.put(playerId, 0);
        Map<String, Object> data = new HashMap<>();
        Map<String, Boolean> roleStatusCopy = new HashMap<>();
        for (Map.Entry<Integer, Boolean> entry : roleStatus.entrySet()) {
            roleStatusCopy.put(entry.getKey().toString(), entry.getValue());
        }

        data.put("roleStatus", roleStatusCopy);

        data.put("playerRole", playerRole);

        data.put("readyStatus", readyStatus);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.CANCEL_ROLE, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);


    }

    /**
     * 玩家锁定角色
     *
     * @param roomId
     * @param playerId
     */
    public void playerRoleReady(String roomId, String playerId) throws Exception {

        //选择角色的数据
        ChooseRoleData chooseRoleData = ChooseRoleManager.getData(roomId);

        //获取角色的状态
        Map<Integer, Boolean> roleStatus = chooseRoleData.getRoleStatus();

        //获取玩家选择的角色
        Map<String, Integer> playerRole = chooseRoleData.getPlayerRole();

        //获取玩家的准备情况
        Map<String, Boolean> readyStatus = chooseRoleData.getReadyStatus();

        readyStatus.put(playerId, true);

        Map<String, Object> data = new HashMap<>();

        Map<String, Boolean> roleStatusCopy = new HashMap<>();

        for (Map.Entry<Integer, Boolean> entry : roleStatus.entrySet()) {
            roleStatusCopy.put(entry.getKey().toString(), entry.getValue());
        }

        data.put("roleStatus", roleStatusCopy);

        data.put("playerRole", playerRole);

        data.put("readyStatus", readyStatus);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.PLAYER_ROLE_READY, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

        //检查是否所有角色都准备
        for (Map.Entry<String, Boolean> entry : readyStatus.entrySet()) {

            if (!entry.getValue()) {
                return;
            }

        }

        startGame(roomId);
    }

    /**
     * 默认选择角色
     *
     * @param roomId
     * @throws Exception
     */
    public void defaultChooseRole(String roomId, String playerId) throws Exception {

        //倒计时完成默认准备
        ChooseRoleData chooseRoleData = ChooseRoleManager.getData(roomId);

        //倒计时完成默认随机选择角色
        Map<String, Integer> playerRole = chooseRoleData.getPlayerRole();

        List<Integer> roles = new LinkedList<>();

        roles.addAll(RoleCode.roleCode);

        for (Map.Entry<String, Integer> entry : playerRole.entrySet()) {
            if (entry.getValue() != 0) {
                roles.remove(entry.getValue());
                chooseRoleData.getReadyStatus().put(entry.getKey(), true);
            } else {
                playerRole.remove(entry.getKey());
            }
        }

        Collections.shuffle(roles);
        playerRole.put(playerId, roles.get(0));
        chooseRoleData.getReadyStatus().put(playerId, true);
        roles.remove(0);

        Map<String, Boolean> readyStatus = chooseRoleData.getReadyStatus();

        //检查是否所有角色都准备
        for (Map.Entry<String, Boolean> entry : readyStatus.entrySet()) {
            if (!entry.getValue()) {
                return;
            }
        }

        startGame(roomId);

    }

    /**
     * 开始游戏
     *
     * @param roomId
     * @throws Exception
     */
    public void startGame(String roomId) throws Exception {

        long time = System.currentTimeMillis();

        //添加游戏开始的时间点
        GameStatisticsManager.addGameStatisticsTime(roomId, time);

        RoomManager.updateRoomStatus(roomId, RoomStatus.IN_GAMEING);

        List<String> players = RoomManager.getPlayers(roomId);

        for (String playerId : players) {
            ClientConnectionManager.updateStatus(playerId, ConnectionStatus.IN_THE_GAME);
        }

        gameInitHandler.createGame(roomId);

        Map<String, Object> data = new HashMap<>();

        Map<String, RoleInfo> roleInfoData = GameManager.getRoleInfoGroup(roomId);

        Queue<CurrentRole> roleOrder = GameManager.getRoleOrder(roomId);

        List<String> list = new LinkedList<>();
        for (CurrentRole currentRole : roleOrder) {
            list.add(currentRole.getPlayerId());
        }

        data.put("roleInfoData", roleInfoData);

        data.put("roleOrder", list);

        data.put("playerNames", RoomManager.getPlayerNames(roomId));

        data.put("roleInitData",GameManager.getGame(roomId).getRoleInitData());

        NettyMessage message = BuildResponseMessage.build(ProtoIds.START_GAME, "", MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);

    }

}
