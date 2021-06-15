package com.coderandyli.server.handler;

import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;
import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.TimeUnit;

/**
 * @Description: server idle check
 * @Date 2021/6/15 2:44 下午
 * @Created by lizhenzhen
 */
@Slf4j
public class ServerIdleCheckHandler extends IdleStateHandler {

    public ServerIdleCheckHandler() {
        super(10, 0, 0, TimeUnit.SECONDS);
    }

    @Override
    protected void channelIdle(ChannelHandlerContext ctx, IdleStateEvent evt) throws Exception {
        if (evt == IdleStateEvent.FIRST_READER_IDLE_STATE_EVENT) {
            log.info("idle check happen, so close the connection.");
            ctx.close();
            return;
        }

        super.channelIdle(ctx, evt);
    }
}
