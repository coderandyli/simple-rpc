package com.coderandyli.server.handler;

import com.coderandyli.common.Operation;
import com.coderandyli.common.OperationResult;
import com.coderandyli.common.RequestMessage;
import com.coderandyli.common.ResponseMessage;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import lombok.extern.slf4j.Slf4j;

/**
 * @Description: 业务处理器
 * @Date 2021/6/10 3:58 下午
 * @Created by lizhenzhen
 */
@Slf4j
public class RpcServerProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage requestMessage) throws Exception {
        Operation operation = requestMessage.getMessageBody();
        OperationResult operationResult = operation.execute();

        ResponseMessage responseMessage = new ResponseMessage();
        responseMessage.setMessageHeader(requestMessage.getMessageHeader());
        responseMessage.setMessageBody(operationResult);

        if (ctx.channel().isActive() && ctx.channel().isWritable()){
            ctx.writeAndFlush(responseMessage);
        }else {
            log.error("not writable now, message dropped");
        }

    }
}
