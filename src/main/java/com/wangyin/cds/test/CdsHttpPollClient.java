/**
 * 
 */
package com.wangyin.cds.test;

import static io.netty.handler.codec.http.HttpHeaders.Names.ACCEPT_ENCODING;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.HOST;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.http.DefaultFullHttpRequest;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpMethod;
import io.netty.handler.codec.http.HttpVersion;

import java.net.URI;

import com.wangyin.cds.common.constant.CdsConstant;

/**
 * Cds轮询服务测试客户端
 * 
 * @author wymaoxiaoliang
 *
 */
public class CdsHttpPollClient {
    private static String SRV_HOST;
    private static int    SRV_PORT;
    private static URI    SRV_URI;
    public static String  POLL_SESSION_ID;

    public CdsHttpPollClient(URI srvUri) {
        SRV_HOST = srvUri.getHost() == null ? "localhost" : srvUri.getHost();
        String scheme = srvUri.getScheme() == null ? "http" : srvUri.getScheme();
        SRV_PORT = srvUri.getPort();
        if (SRV_PORT == -1) {
            if ("http".equalsIgnoreCase(scheme)) {
                SRV_PORT = 8080;
            }
        }

        if (!"http".equalsIgnoreCase(scheme)) {
            System.err.println("Only HTTP is supported.");
            return;
        }

        SRV_URI = srvUri;
    }

    public void run() throws Exception {

        // Configure the client.
        EventLoopGroup group = new NioEventLoopGroup();
        try {
            Bootstrap b = new Bootstrap();
            b.group(group).channel(NioSocketChannel.class).handler(new CdsHttpPollClientInitializer());

            // Make the connection attempt.
            Channel ch = b.connect(SRV_HOST, SRV_PORT).sync().channel();

            // do get request
            //doGetRequest(ch);

            do {
                CdsHttpPollClient.doGetRequest(ch);

                // sleep some time
                try {
                    System.out.println("sleep some time, current time:" + System.currentTimeMillis() / 1000);
                    Thread.currentThread().sleep(1000 * 10);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            } while (true);

            // Wait for the server to close the connection.
            //ch.closeFuture().sync();
        } finally {
            // Shut down executor threads to exit.
            group.shutdownGracefully();
        }
    }

    public static void doGetRequest(Channel ch) {
        // check
        if (SRV_HOST == null || SRV_HOST == "" || SRV_PORT == 0 || SRV_URI == null) {
            System.err.println("Please init Client!");
        }

        // Prepare the HTTP request.
        FullHttpRequest request = new DefaultFullHttpRequest(HttpVersion.HTTP_1_1, HttpMethod.GET, SRV_URI.getRawPath());
        request.headers().set(HOST, SRV_HOST);
        request.headers().set(CONNECTION, Values.KEEP_ALIVE);
        request.headers().set(ACCEPT_ENCODING, HttpHeaders.Values.GZIP);
        
        // 设置HTTP POLL 自定义HEAD
        if (POLL_SESSION_ID != null && POLL_SESSION_ID != "") {
            request.headers().set(CdsConstant.HEAD_CDS_POLL_SESSION_ID, POLL_SESSION_ID);
        }
        request.headers().set(CdsConstant.HEAD_CDS_REQ_TYPE, CdsConstant.REQ_TYPE_CHECK_EVENT);

        // Send the HTTP request.
        ch.writeAndFlush(request);

    }

    public static void main(String[] args) throws Exception {
        URI uri;
        if (args.length != 1) {
            uri = new URI("http://localhost:8080/");
        } else {
            uri = new URI(args[0]);
        }

        new CdsHttpPollClient(uri).run();
    }
}
