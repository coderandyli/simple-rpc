package com.coderandyli.client.codec;

import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @description: 【一次编码】解决tcp半包、粘包问题
 * @author: lizhenzhen
 * @date: 2021-05-08 14:33
 **/
public class RpcFrameEncode extends LengthFieldPrepender {
    public RpcFrameEncode() {
        super(2);
    }
}
