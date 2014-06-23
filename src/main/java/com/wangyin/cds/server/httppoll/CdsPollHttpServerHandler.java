/**
 * 
 */
package com.wangyin.cds.server.httppoll;

import static io.netty.handler.codec.http.HttpHeaders.is100ContinueExpected;
import static io.netty.handler.codec.http.HttpHeaders.isKeepAlive;
import static io.netty.handler.codec.http.HttpHeaders.setContentLength;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONNECTION;
import static io.netty.handler.codec.http.HttpHeaders.Names.CONTENT_TYPE;
import static io.netty.handler.codec.http.HttpMethod.GET;
import static io.netty.handler.codec.http.HttpResponseStatus.BAD_REQUEST;
import static io.netty.handler.codec.http.HttpResponseStatus.CONTINUE;
import static io.netty.handler.codec.http.HttpResponseStatus.FORBIDDEN;
import static io.netty.handler.codec.http.HttpResponseStatus.OK;
import static io.netty.handler.codec.http.HttpVersion.HTTP_1_1;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.wangyin.cds.biz.CdsEventService;
import com.wangyin.cds.biz.PollService;
import com.wangyin.cds.common.constant.CdsConstant;
import com.wangyin.cds.common.event.CdsEvent;
import com.wangyin.cds.common.event.EventCode;
import com.wangyin.cds.service.dal.MyBatisTestMain;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.Unpooled;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.DefaultFullHttpResponse;
import io.netty.handler.codec.http.FullHttpRequest;
import io.netty.handler.codec.http.FullHttpResponse;
import io.netty.handler.codec.http.HttpHeaders.Values;
import io.netty.handler.codec.http.HttpRequest;
import io.netty.util.CharsetUtil;

/**
 * 轮询处理Handler
 * 
 * @author wymaoxiaoliang
 * 
 */
public class CdsPollHttpServerHandler extends SimpleChannelInboundHandler<Object> {
    private static Logger       logger        = LoggerFactory.getLogger(CdsPollHttpServerHandler.class);
    private static final byte[] EMPTY_CONTENT = { '[', ']' };
    private HttpRequest         request;

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, Object msg) throws Exception {
        if (msg instanceof FullHttpRequest) {
            logger.info(">>>>FullHttpReq/from:" + ctx.channel().remoteAddress().toString() + "<<<<");
            HttpRequest request = this.request = (HttpRequest) msg;
            handleHttpRequest(ctx, (FullHttpRequest) msg);
        } else if (msg instanceof HttpRequest) {
            logger.info(">>>>HttpRequest/from:" + ctx.channel().remoteAddress().toString() + "<<<<");
            HttpRequest request = this.request = (HttpRequest) msg;
            handleHttpRequest(ctx, (HttpRequest) msg);
        }
    }

    /**
     * 处理请求
     * 
     * @param ctx
     * @param req
     * @throws Exception
     */
    private void handleHttpRequest(ChannelHandlerContext ctx, HttpRequest req) throws Exception {
        //logger.info(">>>>BEGIN:handleHttpRequest<<<<");
        // Handle a bad request.
        if (!req.getDecoderResult().isSuccess()) {
            logger.warn(">>>>FullHttpReq/HTTP_STATUS:" + BAD_REQUEST + "<<<<");
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, BAD_REQUEST));
            return;
        }

        // Allow only GET methods.
        if (req.getMethod() != GET) {
            logger.warn(">>>>FullHttpReq/HTTP_STATUS:" + FORBIDDEN + "<<<<");
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, FORBIDDEN));
            return;
        }

        // Head status continue.
        if (is100ContinueExpected(req)) {
            logger.warn(">>>>FullHttpReq/HTTP_STATUS:" + CONTINUE + "<<<<");
            sendHttpResponse(ctx, req, new DefaultFullHttpResponse(HTTP_1_1, CONTINUE));
            return;
        }

        // 轮询响应
        byte[] resContent = EMPTY_CONTENT;
        if (req.headers().contains(CdsConstant.HEAD_CDS_POLL_SESSION_ID)) {
            String cdsPollSessionId = req.headers().get(CdsConstant.HEAD_CDS_POLL_SESSION_ID);
            resContent = getCdsEvent(cdsPollSessionId);
        }
        FullHttpResponse res = new DefaultFullHttpResponse(HTTP_1_1, OK, Unpooled.wrappedBuffer(resContent));

        // Set SESSION ID
        if (req.headers().contains(CdsConstant.HEAD_CDS_POLL_SESSION_ID)) {
            String cdsPollSessionId = req.headers().get(CdsConstant.HEAD_CDS_POLL_SESSION_ID);
            logger.info(">>>>FullHttpReq/ClientPollSessionId:" + cdsPollSessionId + "<<<<");
            if (cdsPollSessionId != null && cdsPollSessionId != "") {
                res.headers().set(CdsConstant.HEAD_CDS_POLL_SESSION_ID, cdsPollSessionId);
            } else {
                res.headers().set(CdsConstant.HEAD_CDS_POLL_SESSION_ID, System.currentTimeMillis());
                logger.info(">>>>FullHttpReq/O_NULL/N PollSessionId:" + res.headers().get(CdsConstant.HEAD_CDS_POLL_SESSION_ID) + "<<<<");
            }
        } else {
            res.headers().set(CdsConstant.HEAD_CDS_POLL_SESSION_ID, System.currentTimeMillis());
            logger.info(">>>>FullHttpReq/New ClientPollSessionId:" + res.headers().get(CdsConstant.HEAD_CDS_POLL_SESSION_ID) + "<<<<");
        }

        // Write response.
        sendHttpResponse(ctx, req, res);
        //logger.info(">>>>END:handleHttpRequest<<<<");
    }

    private byte[] getCdsEvent(String sessionId) {
        return PollService.getCdsEventListJson(sessionId);
    }

    /**
     * 处理响应
     * 
     * @param ctx
     * @param req
     * @param res
     * @throws InterruptedException 
     */
    private static void sendHttpResponse(ChannelHandlerContext ctx, HttpRequest req, FullHttpResponse res)
                                                                                                          throws InterruptedException {
        res.headers().set(CONNECTION, Values.KEEP_ALIVE);
        res.headers().set(CONTENT_TYPE, "text/plain; charset=UTF-8");

        // Generate an error page if response getStatus code is not OK (200).
        if (res.getStatus().code() != OK.code()) {
            ByteBuf buf = Unpooled.copiedBuffer(res.getStatus().toString(), CharsetUtil.UTF_8);
            res.content().writeBytes(buf);
            buf.release();
            setContentLength(res, res.content().readableBytes());
        }

        setContentLength(res, res.content().readableBytes());
        // Send the response and close the connection if necessary.
        ChannelFuture f = ctx.channel().writeAndFlush(res);
        if (!isKeepAlive(req) || res.getStatus().code() != 200) {
            f.addListener(ChannelFutureListener.CLOSE);
        }
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        logger.error(">>>>Server handler caugt exception<<<<", cause);
        ctx.close();
        logger.error(">>>>Chandler exception channel closed/from:" + ctx.channel().remoteAddress().toString() + "<<<<");
    }

}
