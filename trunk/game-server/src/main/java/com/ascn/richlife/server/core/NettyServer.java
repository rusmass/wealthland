package com.ascn.richlife.server.core;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import org.apache.log4j.Logger;

import java.util.ResourceBundle;

/**
 * 服务端
 *
 * Created by zhangpengxiang on 17/3/10.
 * @author zhangpengxiang
 */
public class NettyServer {
    private static final Logger logger = Logger.getLogger(NettyServer.class);

    public void run(int port) throws Exception {

        EventLoopGroup bossGroup = new NioEventLoopGroup();
        EventLoopGroup workerGroup = new NioEventLoopGroup();

        try {

            ServerBootstrap b = new ServerBootstrap();
            b.group(bossGroup, workerGroup);

            b.channel(NioServerSocketChannel.class);
            //客户端最大连接数1024
            b.option(ChannelOption.SO_BACKLOG, 1024);
            //通过NoRelay禁用Nagle,使消息立即发出去，不用等到一定数量才发出去
            b.option(ChannelOption.TCP_NODELAY, true);
            //保持长连接
            b.childOption(ChannelOption.SO_KEEPALIVE, true);
            //启动日志
            b.handler(new LoggingHandler(LogLevel.INFO));
            b.childHandler(new ServerChannelInitializer());

            //同步等待绑定端口成功
            ChannelFuture f = b.bind(port).sync();
            if (f.isSuccess()) {
                logger.info("服务端已启动,端口号:" + port);
            }
            //等待服务端监听端口关闭
            f.channel().closeFuture().sync();
        } finally {
            //优雅的退出
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    public static void init() throws Exception {
        logger.info("启动游戏服务器.....");
        int port = 0;
        try {
            ResourceBundle bundle = ResourceBundle.getBundle("serverConfig");
            port = Integer.parseInt(bundle.getString("server_port"));
        } catch (NumberFormatException e) {
            throw new Exception("port端口类型转换异常");
        } catch (Exception e) {
            throw new Exception("serverConfig文件不存在,或者没有server_port字段");
        }
        new NettyServer().run(port);
    }

}
