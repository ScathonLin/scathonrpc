package com.scathon.tech.rpc.common.proto;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.google.protobuf.InvalidProtocolBufferException;
import com.scathon.tech.rpc.common.utils.ProtostuffCodecUtils;
import org.junit.Test;

import java.util.LinkedList;
import java.util.List;
import java.util.Optional;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ProtobufTest.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/7
 * @Version 1.0
 */
public class ProtobufTest {
    @Test
    public void anyTest() throws InvalidProtocolBufferException {

        RequestMsgEntity.RequestMessage.Builder builder = RequestMsgEntity.RequestMessage.newBuilder();

        ParamObj paramObj = new ParamObj();
        paramObj._1 = 1;
        paramObj._2 = "linhd";

        User user = new User();
        user.id = 222;
        user.name = "scathon";
        paramObj._3 = user;

        List<Object> objs = new LinkedList<>();
        objs.add(int.class);
        objs.add(String.class);
        objs.add(Object.class);

        paramObj.objs = objs;

        byte[] bytes = ProtostuffCodecUtils.serialize(paramObj, ParamObj.class).get();
        builder.setParamObjs(Any.newBuilder().setValue(ByteString.copyFrom(bytes)).build());
        builder.setRequestUUID("111uuuid");
        builder.setMethodName("findUserById");
        builder.setServiceName("userService");
        byte[] serialBytes = builder.build().toByteArray();
        System.out.println(serialBytes.length);
        RequestMsgEntity.RequestMessage.Builder newBuilder = builder.mergeFrom(serialBytes);
        System.out.println(newBuilder.getServiceName());
        System.out.println(newBuilder.getRequestUUID());
        System.out.println(newBuilder.getMethodName());
        System.out.println(newBuilder.getParamObjs());

        Optional<ParamObj> obj =
                ProtostuffCodecUtils.deserialize(newBuilder.getParamObjs().getValue().toByteArray(), ParamObj.class);
        System.out.println(obj.get());
        RequestMsgEntity.RequestMessage msg = newBuilder.build();
        ParamObj paramObj1 = ProtostuffCodecUtils.deserialize(msg.getParamObjs().getValue().toByteArray(),
                ParamObj.class).get();
        System.out.println(paramObj1);


    }
}
