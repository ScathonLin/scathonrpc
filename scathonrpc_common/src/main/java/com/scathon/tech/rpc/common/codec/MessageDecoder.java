package com.scathon.tech.rpc.common.codec;

import com.scathon.tech.rpc.common.utils.ProtostuffCodecUtils;
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
 * @Date 2019/5/18
 * @Version 1.0
 */
public class MessageDecoder extends ByteToMessageDecoder {

    private Class<?> targetType;

    public MessageDecoder(Class<?> targetType) {
        this.targetType = targetType;
    }

    @Override
    protected void decode(ChannelHandlerContext ctx, ByteBuf in, List<Object> out) throws Exception {
        int readableBytesNum = in.readableBytes();
        // 传输的报文的数据结构是：bytesNum+content，所以先判断报文的实际字节数，如果可读字节数<4说明，说明标识报文长度的4字节数据还没到，此时不做任何操作
        // ，直接返回，netty会等待更多的数据到来.
        if (readableBytesNum < 4) {
            return;
        }

        in.markReaderIndex();
        // 获取报文数据的长度.
        int contentLength = in.readInt();

        readableBytesNum = in.readableBytes();
        // 如果当前可读报文数据长度小于报文总长度，那么说明，数据还没有完全发过来，此时重置reader index(前文已经mark的那个位置).
        if (readableBytesNum < contentLength) {
            in.resetReaderIndex();
            return;
        }

        // 到了这一步，才算是报文数据全部到达了.
        byte[] buffer = new byte[contentLength];
        in.readBytes(buffer);

        Object resultObj = ProtostuffCodecUtils.deserialize(buffer, targetType).orElse(null);
        out.add(resultObj);

    }
}
