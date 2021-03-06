package com.wangyin.cds.server;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;

import com.wangyin.cds.server.protocol.NodeProtocol.NRequest;

public class SlaveHandler extends SimpleChannelInboundHandler<NRequest>{
	private static final org.slf4j.Logger logger = org.slf4j.LoggerFactory.getLogger(SlaveHandler.class);
	@Override
	public void channelActive(ChannelHandlerContext ctx) throws Exception {
		logger.debug("slave connection was created");
	}
	@Override
	protected void channelRead0(ChannelHandlerContext ctx, NRequest msg) throws Exception {
		
	}
}
