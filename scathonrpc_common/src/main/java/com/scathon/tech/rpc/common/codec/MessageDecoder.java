package com.scathon.tech.rpc.common.codec;

import com.scathon.tech.rpc.common.proto.RequestMsgEntity;
import com.scathon.tech.rpc.common.proto.ResponseMsgEntity;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;

/**
 * TODO Function The Class Is.
 *
 * @ClassName MessageDecoder.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/2
 * @Version 1.0
 */
public class MessageDecoder {

    /**
     * 服务端解码客户端的服务请求数据.
     */
    public static class RequestMsgDecoder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            // 获取能读取到的字节数.
            int bytesCanRead = byteBuf.readableBytes();
            // 申请对应的内存存储请求数据.
            byte[] dataBuf = new byte[bytesCanRead];
            byteBuf.readBytes(dataBuf);
            // 反序列化请求数据.
            RequestMsgEntity.RequestMessage reqMsg = RequestMsgEntity.RequestMessage.parseFrom(dataBuf);
            list.add(reqMsg);
        }
    }

    /**
     * 客户端解码服务端的响应数据.
     */
    public static class ResponseMsgDecocder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) throws Exception {
            int bytesCanRead = byteBuf.readableBytes();
            byte[] dataBuf = new byte[bytesCanRead];
            byteBuf.readBytes(dataBuf);
            ResponseMsgEntity.ResponseMessage respMsg = ResponseMsgEntity.ResponseMessage.parseFrom(dataBuf);
            list.add(respMsg);
        }
    }
}
