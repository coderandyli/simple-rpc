package com.coderandyli.server.codec;
import io.netty.handler.codec.LengthFieldPrepender;

/**
 * @description: 【一次编码】解决tcp半包、粘包问题
 * @Date 2021/6/10 3:42 下午
 * @Created by lizhenzhen
 */
public class FrameEncode extends LengthFieldPrepender {

    public FrameEncode() {
        super(2);
    }

}
