package com.scathon.tech.rpc.server.netty;

import com.scathon.tech.rpc.common.conf.RpcProperties;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * netty rpc server.
 *
 * @ClassName RpcBootstrapServer.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@SuppressWarnings("SpringJavaAutowiredFieldsWarningInspection")
@Component
public class RpcBootstrapServer {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcBootstrapServer.class);

    @Autowired
    private RpcProperties rpcProperties;

    private RpcServerChannelInitializer channelInitializer = RpcServerChannelInitializer.getInstance();

    /**
     * netty 服务引导.
     */
    public void bootstrap() {
        NioEventLoopGroup bossGroup = new NioEventLoopGroup();
        NioEventLoopGroup workerGroup = new NioEventLoopGroup();
        ServerBootstrap bootstrap = new ServerBootstrap();

        // 初始化netty配置.
        bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                .childHandler(channelInitializer).option(ChannelOption.SO_BACKLOG, 128)
                .childOption(ChannelOption.SO_KEEPALIVE, true);
        String serverAddress = rpcProperties.getServerProviderIpsAddrs();
        String[] socketInfo = serverAddress.split(":");
        String host = socketInfo[0];
        int port = Integer.parseInt(socketInfo[1]);

        // 绑定IP和端口.
        try {
            ChannelFuture future = bootstrap.bind(host, port).sync();
            future.channel().closeFuture().sync();
            LOGGER.info("Netty rpc server start successfully! ip and port binded is : {}:{}", host, port);
        } catch (InterruptedException e) {
            LOGGER.error("failed to start netty rpc server engine, please check settings, the error msg is : {}",
                    e.getMessage());
        } finally {
            // release resource.
            workerGroup.shutdownGracefully();
            bossGroup.shutdownGracefully();
            LOGGER.error("############netty service has been shutdown gracefully!############");
        }

    }

}
