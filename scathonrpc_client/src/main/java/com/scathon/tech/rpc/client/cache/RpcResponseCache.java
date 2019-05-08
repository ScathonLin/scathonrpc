package com.scathon.tech.rpc.client.cache;

import com.scathon.tech.rpc.common.proto.RequestMsgEntity;
import com.scathon.tech.rpc.common.proto.ResponseMsgEntity;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * 远程服务调用结果缓存.
 * 使用长度为1 的阻塞队列等待响应结果.
 *
 * @ClassName RpcResponseCache.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/4
 * @Version 1.0
 */
public class RpcResponseCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcResponseCache.class);
    private static final Map<String, LinkedBlockingQueue<ResponseMsgEntity.ResponseMessage>> CACHE =
            new ConcurrentHashMap<>();

    public static void push(RequestMsgEntity.RequestMessage reqMsg) {
        String uuid = reqMsg.getRequestUUID();
        if (StringUtils.isEmpty(uuid)) {
            LOGGER.error("reques uuid is empty!! please check!!");
            return;
        }
        CACHE.put(uuid, new LinkedBlockingQueue<>(1));
    }

    public static LinkedBlockingQueue<ResponseMsgEntity.ResponseMessage> get(String reqUUID) {
        return CACHE.getOrDefault(reqUUID, null);
    }

    public static LinkedBlockingQueue<ResponseMsgEntity.ResponseMessage> remove(String reqUUID) {
        return CACHE.remove(reqUUID);
    }

}
