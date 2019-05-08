package com.scathon.tech.rpc.client.netty;

import com.scathon.tech.rpc.client.cache.RpcResponseCache;
import com.scathon.tech.rpc.common.proto.ResponseMsgEntity;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 客户端数据处理器.
 *
 * @ClassName ClientDataProcessHandler.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/4
 * @Version 1.0
 */
public class ClientDataProcessHandler extends SimpleChannelInboundHandler<ResponseMsgEntity.ResponseMessage> {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcClientBootstrap.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, ResponseMsgEntity.ResponseMessage msg) throws Exception {
        String requestUUID = msg.getRequestId();
        RpcResponseCache.get(requestUUID).put(msg);
        LOGGER.info("successfully get response from server, uuid is : {}", requestUUID);
    }

    @Override
    public void channelReadComplete(ChannelHandlerContext ctx) throws Exception {
        ctx.flush();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("error process the request....err msg is : {}", cause.getMessage());
        ctx.close();
    }

}
