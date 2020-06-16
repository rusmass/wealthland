package com.ascn.richlife.server.core;

import com.ascn.richlife.server.codec.MessageDecoder;
import com.ascn.richlife.server.codec.MessageEncoder;
import com.ascn.richlife.server.handler.HeartBeatHandler;
import com.ascn.richlife.server.handler.NettyServerHandler;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;
import io.netty.handler.timeout.IdleStateHandler;

import java.util.concurrent.TimeUnit;

/**
 * netty服务初始化
 *
 * Created by zhangpengxiang on 17/3/10.
 */
public class ServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    /**
     * 读操作空闲12秒
     */
    private final static int READER_IDLE_TIME_SECONDS = 12;

    /**
     * 写操作空闲0秒
     */
    private final static int WRITER_IDLE_TIME_SECONDS = 0;

    /**
     * 读写全部空闲0秒
     */
    private final static int ALL_IDLE_TIME_SECONDS = 0;

    /**
     * 解决分包粘包问题,使用$_ 进行内容分割
     */
    ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());

    @Override
    protected void initChannel(SocketChannel ch) throws Exception {

        ch.pipeline().addLast(new IdleStateHandler(READER_IDLE_TIME_SECONDS,WRITER_IDLE_TIME_SECONDS,ALL_IDLE_TIME_SECONDS,TimeUnit.SECONDS)).addLast(new HeartBeatHandler());

        ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535,delimiter));
        //解码
        ch.pipeline().addLast(new MessageDecoder());
        //编码
        ch.pipeline().addLast(new MessageEncoder());
        //业务处理
        ch.pipeline().addLast(new NettyServerHandler());

        /*ch.pipeline().addLast(new ReadTimeoutHandler(10)); */     //超时检测

    }
}
