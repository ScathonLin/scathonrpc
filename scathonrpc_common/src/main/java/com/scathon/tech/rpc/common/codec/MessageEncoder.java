package com.scathon.tech.rpc.common.codec;

import com.scathon.tech.rpc.common.entity.RequestMessage;
import com.scathon.tech.rpc.common.entity.ResponseMessage;
import com.scathon.tech.rpc.common.utils.ProtostuffCodecUtils;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.MessageToByteEncoder;

/**
 * TODO Function The Class Is.
 *
 * @ClassName MessageEncoder.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/2
 * @Version 1.0
 */
public class MessageEncoder {

    /**
     * 请求消息编码器.
     */
    public static final class RequestMessageEncoder extends MessageToByteEncoder<RequestMessage> {
        @Override
        protected void encode(ChannelHandlerContext ctx, RequestMessage msg, ByteBuf out) throws Exception {
            ProtostuffCodecUtils.serialize(msg, RequestMessage.class).ifPresent(bytes -> {
                out.writeInt(bytes.length);
                out.writeBytes(bytes);
            });
        }
    }

    /**
     * 响应消息编码器.
     */
    public static final class ResponseMessageEncoder extends MessageToByteEncoder<ResponseMessage> {
        @Override
        protected void encode(ChannelHandlerContext ctx, ResponseMessage msg, ByteBuf out) throws Exception {
            ProtostuffCodecUtils.serialize(msg, ResponseMessage.class).ifPresent(bytes -> {
                out.writeInt(bytes.length);
                out.writeBytes(bytes);
            });
        }
    }
}
