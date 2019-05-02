package com.scathon.tech.rpc.common.codec;

import com.google.protobuf.AbstractMessageLite;
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
public class MessageEncoder extends MessageToByteEncoder<AbstractMessageLite> {

    @Override
    protected void encode(ChannelHandlerContext ctx, AbstractMessageLite msg, ByteBuf out) throws Exception {
        byte[] serialBytes = msg.toByteArray();
        out.writeInt(serialBytes.length);
        out.writeBytes(serialBytes);
    }
}
