package com.scathon.tech.rpc.server.netty;

import com.scathon.tech.rpc.common.codec.MessageDecoder;
import com.scathon.tech.rpc.common.codec.MessageEncoder;
import com.scathon.tech.rpc.common.entity.RequestMessage;
import com.scathon.tech.rpc.common.entity.ResponseMessage;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * channel initializer.
 *
 * @ClassName RpcServerChannelInitializer.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/2
 * @Version 1.0
 */
public class RpcServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerChannelInitializer.class);

    private static final RpcServerChannelInitializer INSTANCE = new RpcServerChannelInitializer();

    /**
     * 私有构造方法，保持单例.
     */
    private RpcServerChannelInitializer() {
    }

    public static RpcServerChannelInitializer getInstance() {
        return INSTANCE;
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        LOGGER.info("start init channel...");
        channel.pipeline()
                .addLast(new MessageDecoder(RequestMessage.class))
                .addLast(new MessageEncoder(ResponseMessage.class))
                .addLast(new RpcRequestProcessHandler());
        LOGGER.info("complete init channel...");
    }
}
