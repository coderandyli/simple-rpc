package com.coderandyli.client;

import com.coderandyli.client.codec.RpcFrameDecode;
import com.coderandyli.client.codec.RpcFrameEncode;
import com.coderandyli.client.codec.RpcProtocolDecode;
import com.coderandyli.client.codec.RpcProtocolEncode;
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

import java.util.concurrent.ExecutionException;

/**
 * @description: 客户端
 * @author: lizhenzhen
 * @date: 2021-06-07 11:42
 **/
public class Client {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        LoggingHandler loggingHandler = new LoggingHandler(LogLevel.INFO);

        Bootstrap bootstrap = new Bootstrap();
        bootstrap.channel(NioSocketChannel.class)
                .group(new NioEventLoopGroup())
                .handler(new LoggingHandler(LogLevel.DEBUG))
                .handler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel ch) throws Exception {
                        ChannelPipeline pipeline = ch.pipeline();
                        pipeline.addLast(loggingHandler);

                        pipeline.addLast(new RpcFrameDecode()); // 入站
                        pipeline.addLast(new RpcFrameEncode()); // 出站
                        pipeline.addLast(new RpcProtocolEncode()); // 出站
                        pipeline.addLast(new RpcProtocolDecode()); // 入站

                    }
                });
        ChannelFuture f = bootstrap.connect("127.0.0.1", 8080).sync();

        // 发送一个请求
        RequestMessage requestMessage = new RequestMessage(001l, new OrderOperation(1001, "tudou"));
        f.channel().writeAndFlush(requestMessage);

        f.channel().closeFuture().sync().get();
    }
}
