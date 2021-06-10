package com.coderandyli.server.codec;

import io.netty.handler.codec.LengthFieldBasedFrameDecoder;

/**
 * @description: 【一次解码】解决tcp半包、粘包问题
 * @Date 2021/6/10 3:40 下午
 * @Created by lizhenzhen
 */
public class RpcFrameDecode extends LengthFieldBasedFrameDecoder {
    /**
     * 解码：解出了一个没有粘包和半包问题的byteBuf
     */
    public RpcFrameDecode() {
        /**
         * int maxFrameLength：最大长度
         * int lengthFieldOffset：长度字段的位移
         * int lengthFieldLength, length字段长度：2
         * int lengthAdjustment,：长度调整
         * int initialBytesToStrip：去掉头字段：2 ( Length filed(字段)占用两个字节，这里一般设置成2，去除length字段, 否则会解析失败)
         */
        super(Integer.MAX_VALUE, 0, 2, 0, 2);
    }
}
