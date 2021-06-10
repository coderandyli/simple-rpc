package com.coderandyli.server.codec;

import com.coderandyli.common.RequestMessage;
import com.coderandyli.common.ResponseMessage;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToMessageDecoder;
import io.netty.handler.codec.MessageToMessageEncoder;

import java.util.List;

/**
 * @description: 【二次解码】ByteBuf -> RequestMessage（转化）
 * @Date 2021/6/10 3:50 下午
 * @Created by lizhenzhen
 */
public class RpcProtocolDecode extends MessageToMessageDecoder<ByteBuf> {

    @Override
    protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> out) throws Exception {
        RequestMessage requestMessage = new RequestMessage();
        requestMessage.decode(byteBuf);

        // 将转化后的，传递出去
        out.add(requestMessage);
    }
}
