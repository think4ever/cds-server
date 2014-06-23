/*
 * Copyright 2012 The Netty Project
 *
 * The Netty Project licenses this file to you under the Apache License,
 * version 2.0 (the "License"); you may not use this file except in compliance
 * with the License. You may obtain a copy of the License at:
 *
 *   http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS, WITHOUT
 * WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied. See the
 * License for the specific language governing permissions and limitations
 * under the License.
 */
package com.wangyin.cds.test;

import com.wangyin.cds.common.constant.CdsConstant;

import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.handler.codec.http.HttpContent;
import io.netty.handler.codec.http.HttpHeaders;
import io.netty.handler.codec.http.HttpObject;
import io.netty.handler.codec.http.HttpResponse;
import io.netty.handler.codec.http.LastHttpContent;
import io.netty.util.CharsetUtil;

public class CdsHttpPollClientHandler extends SimpleChannelInboundHandler<HttpObject> {

    @Override
    public void channelRead0(ChannelHandlerContext ctx, HttpObject msg) throws Exception {
        if (msg instanceof HttpResponse) {
            HttpResponse response = (HttpResponse) msg;

            System.out.println("HTTP_STATUS: " + response.getStatus() + " / " + "HTTP_VERSION: " + response.getProtocolVersion());
            System.out.println();

            if (!response.headers().isEmpty()) {
                for (String name : response.headers().names()) {
                    for (String value : response.headers().getAll(name)) {
                        System.out.println("HEADER: " + name + " = " + value);
                    }
                }
                System.out.println();
            }
            
            if(response.headers().contains(CdsConstant.HEAD_CDS_POLL_SESSION_ID)){
                String cdsPollSessionId = response.headers().get(CdsConstant.HEAD_CDS_POLL_SESSION_ID);
                if (cdsPollSessionId != null && cdsPollSessionId != "") {
                    CdsHttpPollClient.POLL_SESSION_ID = cdsPollSessionId;
                }
            }

            if (HttpHeaders.isTransferEncodingChunked(response)) {
                System.out.println("CHUNKED CONTENT {");
            } else {
                System.out.println("CONTENT {");
            }
        }
        if (msg instanceof HttpContent) {
            HttpContent content = (HttpContent) msg;

            System.out.print(content.content().toString(CharsetUtil.UTF_8));
            System.out.flush();

            if (content instanceof LastHttpContent) {
                System.out.println("} END OF CONTENT");
            }
        }
    }

    // 当连接建立成功之后会调用这个方法初始化channel
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("active");
        System.out.println("connect / " + System.currentTimeMillis() / 1000);
        System.out.println();
    }

    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        System.out.println("inactive");
        System.out.println("disconnect / " + System.currentTimeMillis() / 1000);
        System.out.println();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        cause.printStackTrace();
        ctx.close();
    }
}
