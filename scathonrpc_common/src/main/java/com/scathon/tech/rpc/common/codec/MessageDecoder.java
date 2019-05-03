package com.scathon.tech.rpc.common.codec;

import com.scathon.tech.rpc.common.entity.RequestMessage;
import com.scathon.tech.rpc.common.entity.ResponseMessage;
import com.scathon.tech.rpc.common.utils.ProtostuffCodecUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;

import java.util.List;
import java.util.Optional;

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
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
            // 获取能读取到的字节数.
            int bytesCanRead = byteBuf.readableBytes();
            // 申请对应的内存存储请求数据.
            byte[] dataBuf = new byte[bytesCanRead];
            byteBuf.readBytes(dataBuf);
            // 反序列化请求数据.
            Optional<RequestMessage> reqMsgOptional =
                    ProtostuffCodecUtils.deserialize(dataBuf, RequestMessage.class);
            reqMsgOptional.ifPresent(list::add);
        }
    }

    /**
     * 客户端解码服务端的响应数据.
     */
    public static class ResponseMsgDecocder extends ByteToMessageDecoder {

        @Override
        protected void decode(ChannelHandlerContext channelHandlerContext, ByteBuf byteBuf, List<Object> list) {
            int bytesCanRead = byteBuf.readableBytes();
            byte[] dataBuf = new byte[bytesCanRead];
            byteBuf.readBytes(dataBuf);
            Optional<ResponseMessage> respMsgOptional = ProtostuffCodecUtils.deserialize(dataBuf,
                    ResponseMessage.class);
            respMsgOptional.ifPresent(list::add);
        }
    }
}
