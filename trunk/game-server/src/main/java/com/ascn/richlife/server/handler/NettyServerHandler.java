package com.ascn.richlife.server.handler;

import com.ascn.richlife.server.core.BeanFactory;
import com.ascn.richlife.server.dispatcher.MessageDispatch;
import com.ascn.richlife.server.group.ClientConnectionManager;
import com.ascn.richlife.server.message.NettyMessage;
import com.ascn.richlife.server.protocol.ConnectionStatus;
import com.ascn.richlife.service.login.PlayerService;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;
import io.netty.util.AttributeKey;
import org.apache.log4j.Logger;

/**
 * netty框架连接断开处理类
 *
 * Created by zhangpengxiang on 17/3/10.
 * @author zhangpengxiang
 */
public class NettyServerHandler extends ChannelHandlerAdapter {

    /**
     * 日志
     */
    private Logger logger = Logger.getLogger(NettyServerHandler.class);

    private MessageDispatch messageDispatch = (MessageDispatch) BeanFactory.getBean("messageDispatch");

    private PlayerService playerService = (PlayerService) BeanFactory.getBean("playerService");



    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        logger.debug(ctx.channel().remoteAddress() + " 连接上服务端...");
    }

    @Override
    public void channelRead(ChannelHandlerContext ctx, Object msg) throws Exception {
    	logger.debug("channelRead_msg" + msg.toString());
        NettyMessage message = (NettyMessage) msg;

        //消息分发
        messageDispatch.dispatch(ctx, message);

    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {

        //获取玩家的id
        String playerId = (String) ctx.attr(AttributeKey.valueOf("playerId")).get();

        //获取玩家的状态
        int status;

        //获取当前的房间
        String roomId;

        try {

            status = ClientConnectionManager.getConnectionStatus(playerId);

            roomId = ClientConnectionManager.getRoomId(playerId);

        } catch (NullPointerException e) {
            logger.debug(ctx.channel().remoteAddress() + " 与服务器断开连接"+playerId);
            return;
        }

        //获取用户昵称
        String playerName = playerService.getPlayerName(playerId);

        switch (status) {

            //在线
            case ConnectionStatus.ON_LINE:

                logger.debug(playerName + "退出游戏");

                DisconnectedHandler.disconnectedOnLine(playerId);

                break;

            //在房间中
            case ConnectionStatus.IN_THE_ROOM:

                logger.debug(playerName + "从房间中断开游戏");

                DisconnectedHandler.disconnectedInRoom(roomId,playerId);

                break;

            //选择角色中
            case ConnectionStatus.CHOOSE_ROLE:

                logger.debug(playerName + "从选择角色中断开游戏");

                DisconnectedHandler.disconnectedChooseRole(roomId,playerId);

                break;

            //在游戏初始化中
            case ConnectionStatus.IN_THE_GAME:

                logger.debug(playerName + "从游戏初始化中断开游戏");

                DisconnectedHandler.disconnectedInGame(roomId,playerId);

                break;

            //在游戏当中
            case ConnectionStatus.IN_THE_GAMEING:

                logger.debug(playerName + "从游戏当中断开游戏");

                DisconnectedHandler.disconnectedInGameing(roomId,playerId);

                break;
            default:
                logger.debug("未知的状态!");

                break;
        }

    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        //输出异常
        cause.printStackTrace();
        //关闭客户端连接
        ctx.close();
    }

}
