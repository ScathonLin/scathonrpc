package com.scathon.tech.rpc.server.netty;

import com.scathon.tech.rpc.common.proto.RequestMsgEntity;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
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
                // input-1
                .addLast(new ProtobufVarint32FrameDecoder())
                // input-2
                .addLast(new ProtobufDecoder(RequestMsgEntity.RequestMessage.getDefaultInstance()))
                // output-1
                .addLast(new ProtobufVarint32LengthFieldPrepender())
                // output-2
                .addLast(new ProtobufEncoder())
                // output-3
                .addLast(new RpcRequestProcessHandler());
        LOGGER.info("complete init channel...");
    }
}
