package com.coderandyli.client.handler;

import io.netty.handler.timeout.IdleStateHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 客户端空闲检测
 * @Date 2021/6/15 3:20 下午
 * @Created by lizhenzhen
 */
@Slf4j
public class ClientIdleCheckHandler extends IdleStateHandler {
    public ClientIdleCheckHandler() {
        super(0, 5, 0);
    }
}
