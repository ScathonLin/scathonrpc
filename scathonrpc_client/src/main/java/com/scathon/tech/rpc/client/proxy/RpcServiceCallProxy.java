package com.scathon.tech.rpc.client.proxy;

import com.google.protobuf.Any;
import com.google.protobuf.ByteString;
import com.scathon.tech.rpc.client.cache.LockCache;
import com.scathon.tech.rpc.client.netty.RpcClientBootstrap;
import com.scathon.tech.rpc.common.annotations.RpcService;
import com.scathon.tech.rpc.common.entity.ReqParamTypes;
import com.scathon.tech.rpc.common.entity.ReqParams;
import com.scathon.tech.rpc.common.entity.ResponseBody;
import com.scathon.tech.rpc.common.proto.RequestMsgEntity;
import com.scathon.tech.rpc.common.proto.ResponseMsgEntity;
import com.scathon.tech.rpc.common.utils.ProtostuffCodecUtils;
import com.scathon.tech.rpc.registry.ServiceRegister;
import com.scathon.tech.rpc.registry.common.ServiceInfo;
import com.scathon.tech.rpc.registry.zookeeper.ZookeeperServiceRegister;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;
import java.util.UUID;

/**
 * RPC 服务代理类.
 *
 * @ClassName RpcServiceCallProxy.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
public final class RpcServiceCallProxy implements MethodInterceptor {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcServiceCallProxy.class);
    private static final RpcServiceCallProxy INSTANCE = new RpcServiceCallProxy();
    private static final ServiceRegister REGISTER = ZookeeperServiceRegister.getInstance();

    private RpcServiceCallProxy() {
    }

    public static RpcServiceCallProxy getInstance() {
        return INSTANCE;
    }

    @Override
    public Object intercept(Object obj, Method method, Object[] params, MethodProxy methodProxy) throws Throwable {

        // 封装request对象.
        String methodName = method.getName();
        RequestMsgEntity.RequestMessage.Builder reqBuilder = RequestMsgEntity.RequestMessage.newBuilder();
        reqBuilder.setRequestUUID(UUID.randomUUID().toString());
        String serviceName = method.getDeclaringClass().getAnnotation(RpcService.class).name();
        reqBuilder.setServiceName(serviceName);
        reqBuilder.setMethodName(methodName);
        byte[] paramTypesBytes = ProtostuffCodecUtils.serialize(new ReqParamTypes().addAll(method.getParameterTypes()),
                ReqParamTypes.class).orElse(new byte[0]);

        reqBuilder.setParamTypes(Any.newBuilder().setValue(ByteString.copyFrom(paramTypesBytes)).build());

        byte[] paramsBytes =
                ProtostuffCodecUtils.serialize(new ReqParams().addAll(params), ReqParams.class).orElse(new byte[0]);
        reqBuilder.setParamObjs(Any.newBuilder().setValue(ByteString.copyFrom(paramsBytes)).build());

        RequestMsgEntity.RequestMessage reqMsg = reqBuilder.build();

        ServiceInfo serviceInfo = REGISTER.discoverService(serviceName);
        RpcClientBootstrap bootstrap = RpcClientBootstrap.getInstance();

        LockCache.push(reqMsg.getRequestUUID());
        ResponseMsgEntity.ResponseMessage rsp = bootstrap.callRemoteService(reqMsg, serviceInfo);
        LOGGER.error("*****************************");
        byte[] bodyBytes = rsp.getResponseBody().getValue().toByteArray();
        ResponseBody rspBody =
                ProtostuffCodecUtils.deserialize(bodyBytes, ResponseBody.class).orElse(new ResponseBody());
        Object bodyObj = rspBody.getBody();
        Class<?> returnType = method.getReturnType();
        if (returnType.isInstance(bodyObj)) {
            return returnType.cast(bodyObj);
        }
        return null;
    }

}
