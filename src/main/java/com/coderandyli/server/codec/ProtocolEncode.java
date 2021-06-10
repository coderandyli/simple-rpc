package com.coderandyli.server.codec;

import com.coderandyli.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 *【二次编码】ResponseMessage -> ByteBuf（转化）
 * @Date 2021/6/10 3:50 下午
 * @Created by lizhenzhen
 */
public class ProtocolEncode extends MessageToMessageEncoder<ResponseMessage> {
    @Override
    protected void encode(ChannelHandlerContext ctx, ResponseMessage responseMessage, List out) throws Exception {
        // 创建bytebuf，不能使用ByteBufAllocator.DEFAULT.buffer(), 因为堆外内存与堆内切换是就不会生效
        ByteBuf buffer = ctx.alloc().buffer();

        responseMessage.encode(buffer);

        // 将转化后的，传递出去
        out.add(buffer);
    }
}
