package com.coderandyli.client.handler;

import com.coderandyli.common.RequestMessage;
import com.coderandyli.common.keepalive.KeepaliveOperation;
import io.netty.channel.ChannelHandler;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.ChannelInboundHandlerAdapter;
import io.netty.handler.timeout.IdleStateEvent;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description:  keepalive handler
 * @Date 2021/6/15 3:34 下午
 * @Created by lizhenzhen
 */
@Slf4j
@ChannelHandler.Sharable
public class KeepaliveHandler extends ChannelInboundHandlerAdapter {
    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt == IdleStateEvent.FIRST_WRITER_IDLE_STATE_EVENT) {
            log.info("write idle happen, so need to send keepalive to keep connection not closed by server.");
            KeepaliveOperation keepaliveOperation = new KeepaliveOperation();
            RequestMessage requestMessage = new RequestMessage(002l, keepaliveOperation);
            ctx.writeAndFlush(requestMessage);
        }

        super.userEventTriggered(ctx, evt);
    }
}
