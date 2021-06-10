package com.coderandyli.client.codec;

import com.coderandyli.common.RequestMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @description: 【二次编码】RequestMessage -> ByteBuf（转化）
 * @author: lizhenzhen
 * @date: 2021-05-08 14:49
 **/
public class RpcProtocolEncode extends MessageToMessageEncoder<RequestMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, RequestMessage requestMessage, List<Object> out) throws Exception {
        ByteBuf buffer = ctx.alloc().buffer();

        requestMessage.encode(buffer);

        // 将转化后的，传递出去
        out.add(buffer);
    }
}
