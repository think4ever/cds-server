package com.wangyin.cds.server.modules;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import java.util.Map;

import com.wangyin.cds.server.httppoll.CdsPollHttpServerInitializer;

/**
 * HTTP轮询服务模块
 * <p>使用http协议进行事件的分发服务</p>
 * 
 * @author wymaoxiaoliang
 */
public class HttpPollModule extends ServerModule {
    private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(HttpPollModule.class);

    private String                        bindIp = "127.0.0.1";
    private int                           port   = 8888;
    private EventLoopGroup                bossGroup;
    private EventLoopGroup                workGroup;

    @Override
    public void configure(Map<String, Object> configuration) {
        // TODO Auto-generated method stub

    }

    @Override
    public void startup() throws Exception {
        // 开启HTTP轮询服务
        logger.info("try to start http poll service '" + getName() + "' on " + bindIp + ":" + port);
        bossGroup = new NioEventLoopGroup(1);
        workGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workGroup).channel(NioServerSocketChannel.class).childHandler(new CdsPollHttpServerInitializer());
            
            Channel ch = b.bind(port).sync().channel();
            ch.closeFuture().sync();
        } catch (InterruptedException e) {
            logger.error("fail to start http poll server on " + port, e);
        }
    }

    @Override
    public void shutdown() throws Exception {
        if (bossGroup != null){
            bossGroup.shutdownGracefully();
        }
        if (workGroup != null){
            workGroup.shutdownGracefully();
        }
    }

}
