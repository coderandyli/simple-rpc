package com.coderandyli.server;

import com.coderandyli.server.codec.RpcFrameDecode;
import com.coderandyli.server.codec.RpcFrameEncode;
import com.coderandyli.server.codec.RpcProtocolDecode;
import com.coderandyli.server.codec.RpcProtocolEncode;
import com.coderandyli.server.handler.RpcServerProcessHandler;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelPipeline;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.logging.LogLevel;
import io.netty.handler.logging.LoggingHandler;
import io.netty.util.concurrent.DefaultThreadFactory;
import io.netty.util.concurrent.UnorderedThreadPoolEventExecutor;

import java.util.concurrent.ExecutionException;

/**
 * @description: 服务端
 * @author: lizhenzhen
 * @date: 2021-06-07 11:43
 **/
public class Server {

    public static void main(String[] args) throws InterruptedException, ExecutionException {
        // 线程池
        NioEventLoopGroup bossGroup = new NioEventLoopGroup(1, new DefaultThreadFactory("boss"));
        NioEventLoopGroup workGroup = new NioEventLoopGroup(0, new DefaultThreadFactory("work"));
        UnorderedThreadPoolEventExecutor businessGroup = new UnorderedThreadPoolEventExecutor(10, new DefaultThreadFactory("business")); // 业务处理线程池

        ServerBootstrap bootstrap = new ServerBootstrap();
        bootstrap.channel(NioServerSocketChannel.class)
                .handler(new LoggingHandler(LogLevel.INFO))
                .group(bossGroup, workGroup)
                .childHandler(new ChannelInitializer<NioSocketChannel>() {
                    @Override
                    protected void initChannel(NioSocketChannel nioSocketChannel) throws Exception {
                        ChannelPipeline pipeline = nioSocketChannel.pipeline();

                        pipeline.addLast("loggingHandler", new LoggingHandler(LogLevel.DEBUG));

                        pipeline.addLast("frameDecoder",new RpcFrameDecode()); // 【入站】
                        pipeline.addLast("frameEncoder",new RpcFrameEncode()); // 【出站】
                        pipeline.addLast("protocolEncode",new RpcProtocolEncode()); // 【出站】
                        pipeline.addLast("protocolDecode",new RpcProtocolDecode()); // 【入站】

                        pipeline.addLast(businessGroup, new RpcServerProcessHandler()); // 入站
                    }
                });
        ChannelFuture future = bootstrap.bind(8080).sync();
        future.channel().closeFuture().sync().get();
    }

}
