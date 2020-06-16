package com.ascn.richlife.server.handler;

import com.ascn.richlife.model.countInfo.GameStatistics;
import com.ascn.richlife.server.group.*;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.ConnectionStatus;
import com.ascn.richlife.server.message.MessageStatus;
import com.ascn.richlife.server.protocol.ProtoIds;
import com.ascn.richlife.server.util.BuildResponseMessage;
import com.ascn.richlife.service.gamerecord.GameRecordService;
import com.ascn.richlife.service.gamerecord.GameStatisticsService;
import com.ascn.richlife.service.login.PlayerService;
import io.netty.channel.Channel;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.net.InetSocketAddress;
import java.util.HashMap;
import java.util.Map;

/**
 * 登录认证处理工具类
 */
@Component("loginAuthHandle")
public class LoginAuthHandle {

    //日志
    private Logger logger = Logger.getLogger(LoginAuthHandle.class);

    @Autowired
    private PlayerService playerService;

    @Autowired
    private GameStatisticsService gameStatisticsService;

    @Autowired
    private GameRecordService gameRecordService;

    /**
     * 登录认证
     *
     * @param ctx
     * @param message
     * @throws Exception
     */
    public void loginAuth(ChannelHandlerContext ctx, NettyMessage message) throws Exception {

        //验证消息
        if (message.getHeader() == null || message.getHeader().getPlayerId().isEmpty()) {
            logger.error("错误的协议消息");
            return;
        }

        //如果是登录认证消息，处理，其他消息透传
        if (message.getHeader().getType() == ProtoIds.LOGIN_AUTH) {

            //获取玩家的id
            String playerId = message.getHeader().getPlayerId();

            //绑定玩家的playerId
            ctx.attr(AttributeKey.valueOf("playerId")).set(playerId);

            //获取玩家的昵称
            String playerName = playerService.getPlayerName(playerId);

            //判断玩家是否重复登录
            if (ClientConnectionManager.containsConnection(playerId) && ClientConnectionManager.getConnection(playerId).isActive()) {

                Map<String, Object> data = new HashMap<>(16);
                data.put("tip", "您的账号在其他设备登录，请及时修改密码，保障账户安全！");

                //通知玩家 帐号在别的地方上线
                ClientConnectionManager.sendMessage(playerId, BuildResponseMessage.build(ProtoIds.REPEAT_LOGIN, playerId, 0, data));

                //关闭已经存在的连接
                ClientConnectionManager.getConnection(playerId).close();

                //重新连接之前操作
                reConnection(ctx, message);

                logger.debug(playerName + "帐号在别的地方上线！");
            }

            //判断玩家是否正常重连
            if (ClientConnectionManager.containsConnectionInfo(playerId)) {

                //玩家重连操作
                reConnection(ctx, message);
            } else {

                //玩家正常登录保存玩家的连接
                ClientConnectionManager.addConnection(playerId, ConnectionStatus.ON_LINE, ctx.channel());

                Map<String,Object> data = new HashMap<>(16);
                data.put("online_player_number",ClientConnectionManager.getOnlinePlayerNumber());
                logger.debug("在线人数"+ClientConnectionManager.getOnlinePlayerNumber());

                //登录成功
                ClientConnectionManager.sendMessage(playerId, BuildResponseMessage.build(ProtoIds.LOGIN_AUTH, playerId, 0, null));

                //添加登录成功玩家没有对战记录
                if (gameStatisticsService.getGameStatistics(playerId) == null) {
                    GameStatistics gameStatistics = new GameStatistics(playerId, 0, 0, "0%", "0");
                    gameStatisticsService.addGameStatistics(gameStatistics);
                }

                logger.debug(playerName + "登录成功！");
            }

        } else {
            ctx.fireChannelRead(message);
        }

    }

    /**
     * 重新登录后的操作
     *
     * @param ctx
     * @param message
     * @throws Exception
     */
    public void reConnection(ChannelHandlerContext ctx, NettyMessage message) throws Exception {

        String playerId = message.getHeader().getPlayerId();

        String playerName = playerService.getPlayerName(playerId);

        //获取连接的状态
        int status = ClientConnectionManager.getConnectionStatus(playerId);

        //获取客户端连接
        int clientStatus = (int) message.getBody().get("clientStatus");

        //保存客户端连接
        ClientConnectionManager.addConnection(playerId, status, ctx.channel());

        //通知玩家
        ClientConnectionManager.sendMessage(playerId, BuildResponseMessage.build(ProtoIds.LOGIN_AUTH, playerId, MessageStatus.SUCCESS, null));

        switch (status) {

            //在线状态
            case ConnectionStatus.ON_LINE:

                logger.debug(playerName + "登录成功");

                break;

            //匹配队列
            case ConnectionStatus.MATCH_THE_QUEUE:

                if (clientStatus == 0) {

                    logger.debug(playerName + "从匹配队列离开");

                    ClientConnectionManager.updateStatus(playerId, ConnectionStatus.ON_LINE);

                } else {

                    logger.debug(playerName + "重新连接，回到匹配队列");
                }

                break;

            //在房间中
            case ConnectionStatus.IN_THE_ROOM:

                if (clientStatus == 0) {

                    logger.debug(playerName + "从房间中离开");

                    ClientConnectionManager.updateStatus(playerId, ConnectionStatus.ON_LINE);

                } else {
                    logger.debug(playerName + "重新连接，回到房间");
                }

                break;

            //选择角色
            case ConnectionStatus.CHOOSE_ROLE:

                logger.debug(playerName + "重新连接，回到选择角色");

                break;

            //在游戏中
            case ConnectionStatus.IN_THE_GAMEING:

                logger.debug(playerName + "重新连接，回到游戏");

                logger.debug("是否还存在当前房间" + RoomManager.containsKey(ClientConnectionManager.getRoomId(playerId)));
                if (RoomManager.containsKey(ClientConnectionManager.getRoomId(playerId))) {

                    if (!GameManager.getRoleInitData(ClientConnectionManager.getRoomId(playerId)).containsKey(playerId)) {
                        logger.debug("添加初始化掉线玩家判断" + playerId);
                        GameManager.getRoleInitData(ClientConnectionManager.getRoomId(playerId)).put(playerId, true);
                    }

                    Map<String, Object> data = new HashMap<>(16);

                    data.put("status", 0);
                    data.put("roomId", ClientConnectionManager.getRoomId(playerId));
                    NettyMessage nettyMessage = BuildResponseMessage.build(ProtoIds.RECONNECTION_READYINROOM, playerId, MessageStatus.SUCCESS, data);

                    ClientConnectionManager.sendMessage(playerId, nettyMessage);
                } else {
                    Map<String, Object> data = new HashMap<>(16);

                    data.put("status", 1);
                    NettyMessage nettyMessage = BuildResponseMessage.build(ProtoIds.RECONNECTION_READYINROOM, playerId, MessageStatus.SUCCESS, data);

                    ClientConnectionManager.sendMessage(playerId, nettyMessage);

                    ClientConnectionManager.updateStatus(playerId, ConnectionStatus.ON_LINE);

                }

                break;
            default:
                throw new RuntimeException("获取连接状态出错");
        }

    }

    /**
     * 黑名单验证
     *
     * @param channel
     * @return
     */
    public boolean blacklistVerification(Channel channel) {

        //获取玩家的ip地址
        InetSocketAddress address = (InetSocketAddress) channel.remoteAddress();

        String ip = address.getAddress().getHostAddress();

        return BlackManager.verify(ip);
    }

}
