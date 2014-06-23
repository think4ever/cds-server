/**
 * 
 */
package com.wangyin.cds.server.httppoll;

import java.io.IOException;

import org.codehaus.jackson.map.ObjectMapper;
import org.codehaus.jackson.util.ByteArrayBuilder;

import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;

import com.wangyin.cds.biz.CdsEventService;
import com.wangyin.cds.biz.PollService;
import com.wangyin.cds.common.event.CdsEvent;
import com.wangyin.cds.common.event.EventCode;
import com.wangyin.cds.service.model.MasterOrSlaveSwitchReslt;
import com.wangyin.cds.service.resource.DbInfoResource;

/**
 * CDS轮询服务(http)
 * 
 * @author wymaoxiaoliang
 * 
 */
public class CdsPollHttpServer {
    private final int port;

    public CdsPollHttpServer(int port) {
        this.port = port;
    }

    public void run() throws Exception {
        // Configure the server.
        EventLoopGroup bossGroup = new NioEventLoopGroup(1);
        EventLoopGroup workerGroup = new NioEventLoopGroup();
        try {
            ServerBootstrap b = new ServerBootstrap();
            b.option(ChannelOption.SO_BACKLOG, 1024);
            b.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(new CdsPollHttpServerInitializer());

            Channel ch = b.bind(port).sync().channel();
            ch.closeFuture().sync();
        } finally {
            bossGroup.shutdownGracefully();
            workerGroup.shutdownGracefully();
        }
    }

    /**
     * @param args 端口
     */
    public static void main(String[] args) throws Exception {
        // init test event cache
        CdsEventService.pushCdsEvent(new CdsEvent(EventCode.DEPOTS_RULE_CHANGE));
        CdsEventService.pushCdsEvent(buildSwitchEvent());
        
        int port;
        if (args.length > 0) {
            port = Integer.parseInt(args[0]);
        } else {
            port = 8080;
        }
        new CdsPollHttpServer(port).run();
    }
    
    private static CdsEvent buildSwitchEvent(){
    	DbInfoResource dbinfo = new DbInfoResource();
    	CdsEvent switchEvent = new CdsEvent(EventCode.DB_MASTER_SLAVE_SWTICH);
        MasterOrSlaveSwitchReslt result = dbinfo.getSwithMasterOrSlaveByGroupId(11);
        if(result.isSuccess()){
        	switchEvent.putExtField("groupId", result.getGroupId().toString());
        	switchEvent.putExtField("groupName",  result.getGroupName());
        	switchEvent.putExtField("masterDbIp",  result.getMasterOrSlaveDbInfo().get("Master") .getIp());
        	switchEvent.putExtField("masterDbPort",  result.getMasterOrSlaveDbInfo().get("Master") .getPort());
        	switchEvent.putExtField("masterDbName",  result.getMasterOrSlaveDbInfo().get("Master") .getDbName());
        }
        
        return switchEvent;
    }
    
    public static String toJsonStr(Object bean) {
        try {
            ObjectMapper objectMapper = new ObjectMapper();
            ByteArrayBuilder json = new ByteArrayBuilder();
            objectMapper.writeValue(json, bean);
            return json.toString();
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }

}
