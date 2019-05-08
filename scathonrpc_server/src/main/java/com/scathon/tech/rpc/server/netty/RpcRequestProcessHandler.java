package com.scathon.tech.rpc.server.netty;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.scathon.tech.rpc.common.entity.*;
import com.scathon.tech.rpc.common.entity.CodeMsgMapping;
import com.scathon.tech.rpc.common.proto.RequestMsgEntity;
import com.scathon.tech.rpc.common.proto.ResponseMsgEntity;
import com.scathon.tech.rpc.common.utils.ProtostuffCodecUtils;
import com.scathon.tech.rpc.common.utils.ReflectionUtils;
import com.scathon.tech.rpc.server.registry.RpcServicePublishedHolder;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.xml.ws.Response;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Map;
import java.util.function.Function;

import static com.scathon.tech.rpc.common.entity.ResponseCode.*;


/**
 * 数据处理handler，核心.
 *
 * @ClassName RpcRequestProcessHandler.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/2
 * @Version 1.0
 */
public final class RpcRequestProcessHandler extends SimpleChannelInboundHandler<RequestMsgEntity.RequestMessage> {


    private static final Logger LOGGER = LoggerFactory.getLogger(RpcRequestProcessHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMsgEntity.RequestMessage msg) throws Exception {
        LOGGER.info("received one request : {}", msg.toString());
        // 处理请求，获取响应结果.
        ResponseMsgEntity.ResponseMessage respMsg = process(msg);

        LOGGER.info("complete process requst, id is : {}", msg.getRequestUUID());
        ctx.writeAndFlush(respMsg).addListener(ChannelFutureListener.CLOSE);
        LOGGER.info("completely write and flush data to next handler from process handler....");
    }

    private ResponseMsgEntity.ResponseMessage process(RequestMsgEntity.RequestMessage msg) {
        // 获取调用的参数信息.
        String serviceName = msg.getServiceName();
        String methodName = msg.getMethodName();

        // 解析请求的方法的参数类型.
        ReqParamTypes parameterTypes = ProtostuffCodecUtils.deserialize(msg.getParamTypes().getValue(),
                ReqParamTypes.class).orElse(new ReqParamTypes());

        // 参数类型列表转换为参数类型数组，用于反射调用方法.
        List<Class> paramTypes = parameterTypes.getParams();
        Class<?>[] paramTypesArr = new Class<?>[paramTypes.size()];
        for (int i = 0; i < paramTypes.size(); i++) {
            paramTypesArr[i] = paramTypes.get(i);
        }

        // 反序列化请求参数列表.
        ReqParams parameters = ProtostuffCodecUtils.deserialize(msg.getParamObjs().getValue(),
                ReqParams.class).orElse(new ReqParams());
        // 参数列表转换为数组，用于反射调用.
        Object[] params = parameters.getParams().toArray();

        // 获取服务名称和服务实例对象的映射关系.
        Map<String, Object> serviceRegisMap = RpcServicePublishedHolder.getServiceRegisMap();
        // 获取服务实例对象.
        Object targetService = serviceRegisMap.get(serviceName);
        ResponseMsgEntity.ResponseMessage.Builder respMsgBuilder = ResponseMsgEntity.ResponseMessage.newBuilder();
        // 取出错误码和错误消息模板的映射关系.
        Map<ResponseCode, Function<Object[], String>> codeMsgMap = CodeMsgMapping.MAP;
        // 判断服务实例对象是不是存在，不存在那么就设置错误码和错误说明，直接返回，不进行后续调用.
        if (targetService == null) {
            return respMsgBuilder.setErrorCode(SERVICE_NOT_FOUND.getCode())
                    .setErrMsg(codeMsgMap.get(SERVICE_NOT_FOUND).apply(new Object[]{serviceName})).build();
        }

        try {
            LOGGER.info("start calling service: {}#{}", serviceName, methodName);
            // 调用方法，返回结果.
            Object invokeResult = ReflectionUtils.invokeMethod(targetService, methodName, paramTypesArr, params);

            // 调用成功，设置errorCode以及errMsg.
            // 获取msg 模板Function.
            Function<Object[], String> successTemplate = codeMsgMap.get(SUCCESS);
            respMsgBuilder.setErrorCode(SUCCESS.getCode()).setErrMsg(successTemplate.apply(new Object[]{serviceName,
                    methodName}));
            respMsgBuilder.setRequestId(msg.getRequestUUID());

            // 构建响应body.
            ResponseBody rspBody = new ResponseBody();
            // 设置响应体内容.
            rspBody.setBody(invokeResult);
            byte[] rspBodyBytes = ProtostuffCodecUtils.serialize(rspBody, ResponseBody.class).orElse(new byte[0]);

            // Protostuff序列化响应体内容设置到Response的Any对象中.
            respMsgBuilder.setResponseBody(Any.newBuilder().setValue(ByteString.copyFrom(rspBodyBytes)));
            LOGGER.info("successfully calling service:{}#{}", serviceName, methodName);

        } catch (NoSuchMethodException e) {
            // 方法没找到
            Function<Object[], String> funcNotFoundTpl = codeMsgMap.get(FUNC_NOT_FOUND);
            respMsgBuilder.setErrorCode(FUNC_NOT_FOUND.getCode()).setErrMsg(funcNotFoundTpl.apply(new Object[]{methodName}));
        } catch (InvocationTargetException e) {
            // 未知异常.
            Function<Object[], String> unknowErrorTpl = codeMsgMap.get(UNKNOWN_ERROR);
            respMsgBuilder.setErrorCode(UNKNOWN_ERROR.getCode()).setErrMsg(unknowErrorTpl.apply(new Object[]{serviceName,
                    methodName}));
        } catch (IllegalAccessException e) {
            // 非法访问异常.
            Function<Object[], String> accessErrorTpl = codeMsgMap.get(ACCESS_ERROR);
            respMsgBuilder.setErrorCode(ACCESS_ERROR.getCode()).setErrMsg(accessErrorTpl.apply(new Object[]{serviceName,
                    methodName}));
        }

        return respMsgBuilder.build();
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("unexcepted exception found, error msg is : {}", cause.getMessage());
        ctx.close();
    }

}
