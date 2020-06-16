package com.ascn.richlife.server.test;

import com.ascn.richlife.server.codec.MessageDecoder;
import com.ascn.richlife.server.codec.MessageEncoder;
import com.ascn.richlife.server.core.ServerChannelInitializer;
import com.ascn.richlife.server.message.Header;
import com.ascn.richlife.server.message.NettyMessage;
import io.netty.bootstrap.Bootstrap;
import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.*;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.DelimiterBasedFrameDecoder;

import java.util.UUID;

/**
 * Created by zhangpengxiang on 17/6/5.
 */
public class NettyClient {

    public void connect(String host, int port) throws Exception {
        EventLoopGroup group = new NioEventLoopGroup();

        try {

            Bootstrap bootstrap = new Bootstrap();

            bootstrap.group(group).channel(NioSocketChannel.class)
                    .option(ChannelOption.TCP_NODELAY, true)
                    .handler(new ChannelInitializer<SocketChannel>() {
                        @Override
                        protected void initChannel(SocketChannel ch) throws Exception {

                            //解决分包粘包问题,使用$_ 进行内容分割
                            ByteBuf delimiter = Unpooled.copiedBuffer("$_".getBytes());

                            ch.pipeline().addLast(new DelimiterBasedFrameDecoder(65535, delimiter));

                            ch.pipeline().addLast(new MessageDecoder());            //解码

                            ch.pipeline().addLast(new MessageEncoder());            //编码

                            ch.pipeline().addLast(new ServerChannelInitializer());
                        }
                    });

            Channel c = bootstrap.connect(host, port).sync().channel();


    } finally

    {

        group.shutdownGracefully();
    }
}

    public static void main(String[] args) throws Exception {
        Runnable runnable = new Runnable() {
            @Override
            public void run() {
                try {
                    new NettyClient().connect("127.0.0.1", 8067);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        for (int i = 0; i < 1024; i++) {
            System.err.println("客户端" + i);
            Thread thread = new Thread(runnable);
            thread.start();
        }
        System.err.println(Thread.currentThread().getName());
    }

}
