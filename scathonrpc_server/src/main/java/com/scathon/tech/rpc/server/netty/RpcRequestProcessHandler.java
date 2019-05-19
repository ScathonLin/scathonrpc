package com.scathon.tech.rpc.server.netty;

import com.scathon.tech.rpc.common.entity.CodeMsgMapping;
import com.scathon.tech.rpc.common.entity.RequestMessage;
import com.scathon.tech.rpc.common.entity.ResponseCode;
import com.scathon.tech.rpc.common.entity.ResponseMessage;
import com.scathon.tech.rpc.common.utils.ReflectionUtils;
import com.scathon.tech.rpc.server.registry.RpcServicePublishedHolder;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.InvocationTargetException;
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
public final class RpcRequestProcessHandler extends SimpleChannelInboundHandler<RequestMessage> {


    private static final Logger LOGGER = LoggerFactory.getLogger(RpcRequestProcessHandler.class);

    @Override
    protected void channelRead0(ChannelHandlerContext ctx, RequestMessage msg) throws Exception {
        LOGGER.info("received one request : {}", msg.toString());
        // 处理请求，获取响应结果.
        ResponseMessage respMsg = process(msg);

        LOGGER.info("complete process requst, id is : {}", msg.getRequestUUID());
        ctx.writeAndFlush(respMsg).addListener(ChannelFutureListener.CLOSE);
        LOGGER.info("completely write and flush data to next handler from process handler....");
    }

    private ResponseMessage process(RequestMessage msg) {
        // 获取调用的参数信息.
        String serviceName = msg.getServiceName();
        String methodName = msg.getMethodName();

        Class<?>[] paramTypes = msg.getParamTypes();

        Object[] params = msg.getParamObjs();

        // 获取服务名称和服务实例对象的映射关系.
        Map<String, Object> serviceRegisMap = RpcServicePublishedHolder.getServiceRegisMap();
        // 获取服务实例对象.
        Object targetService = serviceRegisMap.get(serviceName);
        ResponseMessage respMessage = new ResponseMessage();
        // 取出错误码和错误消息模板的映射关系.
        Map<ResponseCode, Function<Object[], String>> codeMsgMap = CodeMsgMapping.MAP;
        // 判断服务实例对象是不是存在，不存在那么就设置错误码和错误说明，直接返回，不进行后续调用.
        if (targetService == null) {
            return respMessage.setErrorCode(SERVICE_NOT_FOUND)
                    .setErrMsg(codeMsgMap.get(SERVICE_NOT_FOUND).apply(new Object[]{serviceName}));
        }

        try {
            LOGGER.info("start calling service: {}#{}", serviceName, methodName);
            // 调用方法，返回结果.
            Object invokeResult = ReflectionUtils.invokeMethod(targetService, methodName, paramTypes, params);

            // 调用成功，设置errorCode以及errMsg.
            // 获取msg 模板Function.
            Function<Object[], String> successTemplate = codeMsgMap.get(SUCCESS);
            respMessage.setErrorCode(SUCCESS).setErrMsg(successTemplate.apply(new Object[]{serviceName,
                    methodName}));
            respMessage.setRequestUUID(msg.getRequestUUID());
            respMessage.setRespBody(invokeResult);

            LOGGER.info("successfully calling service:{}#{}", serviceName, methodName);

        } catch (NoSuchMethodException e) {
            // 方法没找到
            Function<Object[], String> funcNotFoundTpl = codeMsgMap.get(FUNC_NOT_FOUND);
            respMessage.setErrorCode(FUNC_NOT_FOUND).setErrMsg(funcNotFoundTpl.apply(new Object[]{methodName}));
        } catch (InvocationTargetException e) {
            // 未知异常.
            Function<Object[], String> unknowErrorTpl = codeMsgMap.get(UNKNOWN_ERROR);
            respMessage.setErrorCode(UNKNOWN_ERROR).setErrMsg(unknowErrorTpl.apply(new Object[]{serviceName,
                    methodName}));
        } catch (IllegalAccessException e) {
            // 非法访问异常.
            Function<Object[], String> accessErrorTpl = codeMsgMap.get(ACCESS_ERROR);
            respMessage.setErrorCode(ACCESS_ERROR).setErrMsg(accessErrorTpl.apply(new Object[]{serviceName,
                    methodName}));
        }

        return respMessage;
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) throws Exception {
        LOGGER.error("unexcepted exception found, error msg is : {}", cause.getMessage());
        ctx.close();
    }

}
