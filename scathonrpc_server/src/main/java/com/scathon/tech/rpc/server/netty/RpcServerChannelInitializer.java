package com.scathon.tech.rpc.server.netty;

import com.scathon.tech.rpc.common.codec.MessageDecoder;
import com.scathon.tech.rpc.common.codec.MessageEncoder;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * channel initializer.
 *
 * @ClassName RpcServerChannelInitializer.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/2
 * @Version 1.0
 */
@Component
public class RpcServerChannelInitializer extends ChannelInitializer<SocketChannel> {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServerChannelInitializer.class);

    /**
     * 私有构造方法，保持单例.
     */
    private RpcServerChannelInitializer() {
    }

    @Override
    protected void initChannel(SocketChannel channel) throws Exception {
        LOGGER.info("start init channel...");
        channel.pipeline()
                // 解码handler1(in1)
                .addLast(new MessageDecoder.RequestMsgDecoder())
                // 数据处理handler1(in2)
                .addLast(new RpcRequestProcessHandler())
                // 编码器(out1)
                .addLast(new MessageEncoder.ResponseMessageEncoder());
        LOGGER.info("complete init channel...");
    }
}
