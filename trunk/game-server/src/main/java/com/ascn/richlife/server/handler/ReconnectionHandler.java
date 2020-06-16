package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.Location;
import com.ascn.richlife.model.login.Player;
import com.ascn.richlife.model.role.CurrentRole;
import com.ascn.richlife.model.role.RoleInfo;
import com.ascn.richlife.server.group.ClientConnectionManager;
import com.ascn.richlife.server.group.GameManager;
import com.ascn.richlife.server.group.RoomManager;
import com.ascn.richlife.server.message.MessageStatus;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.ConnectionStatus;
import com.ascn.richlife.server.protocol.ProtoIds;
import com.ascn.richlife.server.util.BuildResponseMessage;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

import java.util.*;

/**
 * 游戏重连处理工具类
 *
 * Created by Administrator on 2017/10/20 0020.
 */
@Component("reconnectionHandler")
public class ReconnectionHandler {

    Logger logger = Logger.getLogger(ReconnectionHandler.class);

    /**
     * 重连加入游戏中
     *
     * @param roomId
     * @param playerId
     */
    public void reconnectionInRoom(String roomId, String playerId) throws Exception {

        RoomManager.getRoom(roomId).getReconnectPlayerId().add(playerId);

        Map<String, Object> data = new HashMap<>();

        Map<String, RoleInfo> roleInfoData = GameManager.getRoleInfoGroup(roomId);

        Queue<CurrentRole> roleOrder = GameManager.getRoleOrder(roomId);

        List<String> list = new LinkedList<>();
        for (CurrentRole currentRole : roleOrder) {
            list.add(currentRole.getPlayerId());
        }

        Map<String, Object> roleOrders = new HashMap<>();
        for (CurrentRole currentRole : roleOrder) {
            roleOrders.put(currentRole.getPlayerId(), currentRole.getStatus());
        }

        //角色位置信息
        Map<String, Location> roleLocationData = GameManager.getRoleLocationData(roomId);

        Map<String, Object> placeByTurntables = new HashMap<>();

        Map<String, Object> outOrIns = new HashMap<>();

        Map<String, String> playerNames = RoomManager.getPlayerNames(roomId);

        System.err.println("players"+ RoomManager.getRoom(roomId).getPlayers());
        for (Map.Entry<String, Player> entry : RoomManager.getRoom(roomId).getPlayers().entrySet()) {
            placeByTurntables.put(entry.getKey(), roleLocationData.get(entry.getKey()).getPlaceByTurntable());
            outOrIns.put(entry.getKey(), roleLocationData.get(entry.getKey()).getOutOrIn());
        }

        data.put("roleIsBreaks", roleOrders);

        data.put("placeByTurntables", placeByTurntables);

        data.put("outOrIns", outOrIns);

        data.put("currentRole", GameManager.getGame(roomId).getCurrentRole());

        data.put("roleInfoData", roleInfoData);

        data.put("roleOrder", list);

        data.put("playerNames", playerNames);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.RECONNECTION_INROOM, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(playerId, message);

    }

    /**
     * 重连修改掉线玩家的状态
     *
     * @param roomId
     * @param playerId
     */
    public void reconnectionInGameing(String roomId, String playerId) {

        Queue<CurrentRole> roleOrder = GameManager.getGame(roomId).getRoleOrder();

        for(String player : RoomManager.getRoom(roomId).getReconnectPlayerId()){
            if (playerId.equals(player)) {
                for (CurrentRole currentRole : roleOrder) {
                    if (currentRole.getPlayerId().equals(playerId)) {

                        //玩家准备完成可以重连加入游戏
                        currentRole.setStatus("2");
                    }
                }
            }
        }

        NettyMessage message = BuildResponseMessage.build(ProtoIds.RECONNECTION_STATUS, playerId, MessageStatus.SUCCESS, null);

        ClientConnectionManager.sendMessage(RoomManager.getPlayers(roomId), message);
    }

    /**
     * 刷新掉线玩家数据
     *
     * @param roomId
     * @param playerId
     */
    public void refreshRoleData(String roomId, String playerId) {

        logger.debug("刷新掉线玩家数据");

        Map<String, Object> data = new HashMap<>();

        Map<String, RoleInfo> roleInfoData = GameManager.getRoleInfoGroup(roomId);

        Map<String, Object> roleDataManageInfo = new HashMap<>();

        Map<String, Object> roleHaveAssetInfo = new HashMap<>();
        for (Map.Entry<String, RoleInfo> entry : roleInfoData.entrySet()) {
            roleDataManageInfo.put(entry.getKey(), entry.getValue().getRoleDataManageInfo());
            roleHaveAssetInfo.put(entry.getKey(), entry.getValue().getRoleHaveAssetInfo());
        }
        Queue<CurrentRole> roleOrder = GameManager.getRoleOrder(roomId);

        List<String> list = new LinkedList<>();
        for (CurrentRole currentRole : roleOrder) {
            list.add(currentRole.getPlayerId());
        }

        Map<String, Object> roleOrders = new HashMap<>();
        for (CurrentRole currentRole : roleOrder) {
            roleOrders.put(currentRole.getPlayerId(), currentRole.getStatus());
        }

        //角色位置信息
        Map<String, Location> roleLocationData = GameManager.getRoleLocationData(roomId);

        Map<String, Object> placeByTurntables = new HashMap<>();

        Map<String, Object> outOrIns = new HashMap<>();

        for (Map.Entry<String, Player> entry : RoomManager.getRoom(roomId).getPlayers().entrySet()) {
            placeByTurntables.put(entry.getKey(), roleLocationData.get(entry.getKey()).getPlaceByTurntable());
            outOrIns.put(entry.getKey(), roleLocationData.get(entry.getKey()).getOutOrIn());
        }

        data.put("roleIsBreaks", roleOrders);

        data.put("placeByTurntables", placeByTurntables);

        data.put("outOrIns", outOrIns);

        data.put("currentRole", GameManager.getGame(roomId).getCurrentRole());

        data.put("roleDataManageInfo", roleDataManageInfo);

        data.put("roleHaveAssetInfo", roleHaveAssetInfo);

        data.put("roleOrder", list);

        NettyMessage message = BuildResponseMessage.build(ProtoIds.REFRESH_DATA, playerId, MessageStatus.SUCCESS, data);

        ClientConnectionManager.sendMessage(playerId, message);
    }

    /**
     * 拒绝重连
     *
     * @param roomId
     * @param playerId
     */
    public void refuseConnection(String roomId,String playerId){
        ClientConnectionManager.updateStatus(playerId, ConnectionStatus.ON_LINE);
        NettyMessage message = BuildResponseMessage.build(ProtoIds.REFUSE_RECONNECTION,playerId,MessageStatus.SUCCESS,null);
        ClientConnectionManager.sendMessage(playerId,message);
    }
}
