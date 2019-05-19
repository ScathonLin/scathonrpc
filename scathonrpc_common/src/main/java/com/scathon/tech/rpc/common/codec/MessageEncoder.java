package com.scathon.tech.rpc.common.codec;

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
 * @Date 2019/5/18
 * @Version 1.0
 */
public class MessageEncoder extends MessageToByteEncoder {

    private Class<?> targetType;

    public MessageEncoder(Class<?> targetType) {
        this.targetType = targetType;
    }

    @Override
    protected void encode(ChannelHandlerContext ctx, Object msg, ByteBuf out) throws Exception {
        if (targetType.isInstance(msg)) {
            byte[] bytes = ProtostuffCodecUtils.serialize(msg).orElse(new byte[]{});
            out.writeInt(bytes.length);
            out.writeBytes(bytes);
            return;
        }
        throw new Exception("msg is not the instance of " + targetType.getSimpleName());
    }
}
