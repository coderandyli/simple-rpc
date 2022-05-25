package com.coderandyli.client;

import com.coderandyli.client.codec.RpcFrameDecode;
import com.coderandyli.client.codec.RpcFrameEncode;
import com.coderandyli.client.codec.RpcProtocolDecode;
import com.coderandyli.client.codec.RpcProtocolEncode;
import com.coderandyli.client.handler.ClientIdleCheckHandler;
import com.coderandyli.client.handler.KeepaliveHandler;
import com.coderandyli.client.handler.dispatcher.OperationResultFuture;
import com.coderandyli.client.handler.dispatcher.RequestPendingCenter;
import com.coderandyli.client.handler.dispatcher.ResponseDispatcherHandler;
import com.coderandyli.common.OperationResult;
import com.coderandyli.common.RequestMessage;
import com.coderandyli.common.order.OrderOperation;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.ExecutionException;

/**
 * @description: 客户端
 * @author: lizhenzhen
 * @date: 2021-06-07 11:42
 **/
@Slf4j
public class Client {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);
        KeepaliveHandler keepaliveHandler = new KeepaliveHandler();
        RequestPendingCenter requestPendingCenter = new RequestPendingCenter();

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new LoggingHandler(LogLevel.INFO))
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();

                        pipeline.addLast(loggingHandler);

                        pipeline.addLast("clientIdleCheck", new ClientIdleCheckHandler()); // 入站

                        pipeline.addLast("frameDecode", new RpcFrameDecode()); // 入站
                        pipeline.addLast("frameEncode", new RpcFrameEncode()); // 出站
                        pipeline.addLast("protocolEncode", new RpcProtocolEncode()); // 出站
                        pipeline.addLast("protocolDecode", new RpcProtocolDecode()); // 入站

                        pipeline.addLast("requestPendCenter", new ResponseDispatcherHandler(requestPendingCenter));

                        pipeline.addLast("keepalive" ,keepaliveHandler);
                    }
                });
        ChannelFuture f = bootstrap.connect("127.0.0.1", 8080).sync();

        // 请求参数
        Long streamId = 001l;
        RequestMessage requestMessage = new RequestMessage(streamId, new OrderOperation(1001, "tudou"));

        // 添加operationResultFuture
        OperationResultFuture operationResultFuture = new OperationResultFuture();
        requestPendingCenter.add(streamId, operationResultFuture);

        // 发送请求
        f.channel().writeAndFlush(requestMessage);

        // 获取响应结果
        OperationResult operationResult = operationResultFuture.get();
        log.info("result: {}", operationResult.toString());

        f.channel().closeFuture().sync().get();
    }
}
