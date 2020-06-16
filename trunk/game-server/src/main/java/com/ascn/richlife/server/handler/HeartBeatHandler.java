package com.ascn.richlife.server.handler;

import com.ascn.richlife.server.message.NettyMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerAdapter;
import io.netty.channel.ChannelHandlerContext;

import io.netty.handler.timeout.IdleState;
import io.netty.handler.timeout.IdleStateEvent;
import org.apache.log4j.Logger;
import org.springframework.stereotype.Component;

/**
 * 心跳工具类
 */
@Component("heartBeatHandler")
public class HeartBeatHandler extends ChannelHandlerAdapter {

    private Logger logger = Logger.getLogger(HeartBeatHandler.class);

    /**
     * 心跳消息检测主要做断线检测
     * @param ctx
     * @param evt
     * @throws Exception
     */
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            IdleStateEvent e = (IdleStateEvent) evt;
            if (e.state() == IdleState.READER_IDLE) {
                ctx.close();
                logger.debug("READER_IDLE 读超时");
            } else if (e.state() == IdleState.WRITER_IDLE) {
                ByteBuf buff = ctx.alloc().buffer();
                buff.writeBytes("ascn test".getBytes());
                ctx.writeAndFlush(buff);
                logger.debug("WRITER_IDLE 写超时");
            }
        }
    }

    /**
     * 心跳消息不处理打印日志
     * @param ctx
     * @param message
     */
    public void heartBeat(ChannelHandlerContext ctx, NettyMessage message) {

        /*logger.debug("心跳消息:"+message.toString());*/

    }

}
