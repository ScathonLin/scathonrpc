package com.scathon.tech.rpc.client.netty;

import com.scathon.tech.rpc.client.cache.RpcResponseCache;
import com.scathon.tech.rpc.common.entity.CodeMsgMapping;
import com.scathon.tech.rpc.common.entity.ResponseCode;
import com.scathon.tech.rpc.common.proto.RequestMsgEntity;
import com.scathon.tech.rpc.common.proto.ResponseMsgEntity;
import com.scathon.tech.rpc.registry.common.ServiceInfo;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelInitializer;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.SocketChannel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.handler.codec.protobuf.ProtobufDecoder;
import io.netty.handler.codec.protobuf.ProtobufEncoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32FrameDecoder;
import io.netty.handler.codec.protobuf.ProtobufVarint32LengthFieldPrepender;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.TimeUnit;
import java.util.function.Function;

/**
 * Rpc Client 请求客户端引导类.
 *
 * @ClassName RpcClientBootstrap.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/4
 * @Version 1.0
 */
public class RpcClientBootstrap {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientBootstrap.class);

    private static EventLoopGroup group = null;
    private static volatile Bootstrap bootstrap;

    private static final Object LOCK = new Object();
    private static final RpcClientBootstrap INSTANCE = new RpcClientBootstrap();

    public static RpcClientBootstrap getInstance() {
        return INSTANCE;
    }

    private RpcClientBootstrap() {
    }

    /**
     * 单例模式，复用Bootstrap.
     *
     * @return
     */
    public static Bootstrap getBootstrap() {
        if (bootstrap == null) {
            synchronized (LOCK) {
                if (bootstrap == null) {
                    // 先关闭有可能已经打开的group，防止句柄泄露.
                    closeGroupGracefully();
                    // 初始化netty客户端配置.
                    group = new NioEventLoopGroup();
                    bootstrap = new Bootstrap();
                    bootstrap.group(group)
                            .channel(NioSocketChannel.class)
                            .handler(new RpcClientCannnelInitializer())
                            .option(ChannelOption.SO_KEEPALIVE, true);
                }
            }
        }
        return bootstrap;
    }

    public static void closeGroupGracefully() {
        if (group != null) {
            group.shutdownGracefully();
            //optimize garbage collection.
            group = null;
        }
    }

    private static final class RpcClientCannnelInitializer extends ChannelInitializer<SocketChannel> {

        @Override
        protected void initChannel(SocketChannel channel) throws Exception {
            channel.pipeline()
                    .addLast(new ProtobufVarint32FrameDecoder())
                    .addLast(new ProtobufDecoder(ResponseMsgEntity.ResponseMessage.getDefaultInstance()))
                    .addLast(new ProtobufVarint32LengthFieldPrepender())
                    .addLast(new ProtobufEncoder())
                    .addLast(new ClientDataProcessHandler());
        }
    }

    /**
     * 发起远程服务调用.
     *
     * @param reqMsg      请求消息体.
     * @param serviceInfo service信息.
     * @return 响应消息体.
     */
    public ResponseMsgEntity.ResponseMessage callRemoteService(RequestMsgEntity.RequestMessage reqMsg,
                                                               ServiceInfo serviceInfo) {

        String uuid = reqMsg.getRequestUUID();
        try {
            Bootstrap bs = getBootstrap();
            String[] addressArr = serviceInfo.getServiceAddrList().split(",");
            String[] socketInfo = addressArr[0].split(":");
            String host = socketInfo[0];
            int port = Integer.parseInt(socketInfo[1]);
            ChannelFuture future = bs.connect(host, port);
            LOGGER.info("connect to rpc server endpoint {}:{} successfully! ^_^", host, port);
            // 发起rpc请求.
            LOGGER.info("start calling remote servive,req msg is : {}", reqMsg.toString());

            // 将请求写到channel中.
            future.channel().writeAndFlush(reqMsg).sync();

            // 缓存中加入reqMsg,利用请求的UUID作为key，创建一个阻塞队列，等待远程响应结果.
            RpcResponseCache.push(reqMsg);
            // 5秒钟的请求时间，超过五秒钟，返回.
            ResponseMsgEntity.ResponseMessage respMsg = RpcResponseCache.get(uuid).poll(5000, TimeUnit.MILLISECONDS);
            future.channel().closeFuture().sync();
            if (respMsg == null) {
                // 如果响应结果是null，设置错误信息.
                ResponseMsgEntity.ResponseMessage.Builder rspMsgBuilder =
                        ResponseMsgEntity.ResponseMessage.newBuilder();
                Function<Object[], String> timeoutErrorTpl = CodeMsgMapping.MAP.get(ResponseCode.TIMEOUT);
                rspMsgBuilder.setRequestId(reqMsg.getRequestUUID())
                        .setErrorCode(ResponseCode.TIMEOUT.getCode())
                        .setErrMsg(timeoutErrorTpl.apply(new Object[]{reqMsg.getServiceName(),
                                reqMsg.getMethodName()}));
                return rspMsgBuilder.build();
            }
            return respMsg;

        } catch (InterruptedException e) {
            LOGGER.error("interrupted exception occured, err msg is : {}", e.getMessage());
        } catch (Exception ex) {
            LOGGER.info("unknown exception found while calling remote service, msg is : {}", ex.getMessage());
        } finally {
            RpcResponseCache.remove(uuid);
        }
        return null;
    }
}
