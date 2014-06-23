package com.wangyin.cds.server.container;

import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.http.HttpObjectAggregator;
import io.netty.handler.codec.http.HttpRequestDecoder;
import io.netty.handler.codec.http.HttpResponseEncoder;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Map;

import javax.ws.rs.core.Application;

import org.codehaus.jackson.jaxrs.JacksonJsonProvider;
import org.glassfish.jersey.jackson.JacksonFeature;
import org.glassfish.jersey.server.ContainerFactory;
import org.glassfish.jersey.server.ResourceConfig;

import com.wangyin.cds.server.Predefined;
import com.wangyin.cds.server.container.sample.FooResource;
import com.wangyin.cds.service.resource.DbClusterResource;
import com.wangyin.cds.service.resource.DbGroupResource;
import com.wangyin.cds.service.resource.DbInfoResource;
import com.wangyin.cds.service.resource.SplittingKeyResource;

public class NettyContainerInitializer extends
		ChannelInitializer<SocketChannel> {
	private MiniNettyContainer containerHandler;
	private Map<String, Object> configuration;
	public static final String PROP_APPLICATION="com.wangyin.cds.server.container.NettyContainerInitializer.application";
	private final int port;
	public NettyContainerInitializer(Map<String, Object> config){
		this.configuration = config;
		containerHandler = ContainerFactory.createContainer(MiniNettyContainer.class, initApplcation());
		this.port = (Integer) this.configuration.get("port");
	}
	@Override
	protected void initChannel(SocketChannel ch) throws Exception {
		
		ChannelPipeline pipeline = ch.pipeline();

        pipeline.addLast("encoder", new HttpResponseEncoder());
        pipeline.addLast("decoder", new HttpRequestDecoder());		
        pipeline.addLast("aggregator", new HttpObjectAggregator(65536));
        pipeline.addLast("handler", this.containerHandler);
	}
	
	private Application initApplcation() {
		ResourceConfig app = new ResourceConfig();
		this.configuration.put(PROP_APPLICATION, app);
		//app.packages("com.wangyin.cds.server.container.sample");
		//app.packages("com.wangyin.cds.service.resource");
		app.register(FooResource.class);
		
		//cds-server restful service
		app.register(SplittingKeyResource.class);
		app.register(DbInfoResource.class);
		// added by zy
		app.register(DbClusterResource.class);
		app.register(DbGroupResource.class);
		app.register(JacksonJsonProvider.class);
		
		app.register(ObjectMapperProvider.class);
		app.register(JacksonFeature.class);
		try {
			app.property(Predefined.PROP_BASE_URI, new URI("http://"+configuration.get("ip")+":"+port));
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return app;
	}
	public int getPort() {
		return port;
	}

}
