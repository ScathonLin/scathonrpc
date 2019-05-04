package com.scathon.tech.rpc.client.cache;

import com.scathon.tech.rpc.common.entity.ResponseMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * 远程服务调用结果缓存.
 *
 * @ClassName RpcResponseCache.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/4
 * @Version 1.0
 */
public class RpcResponseCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(RpcResponseCache.class);
    private static final Map<String, ResponseMessage> CACHE = new ConcurrentHashMap<>();

    public static void push(ResponseMessage rspMsg) {
        String uuid = rspMsg.getRequestUUID();
        if (StringUtils.isEmpty(uuid)) {
            LOGGER.error("reques uuid is empty!! please check!!");
            return;
        }
        CACHE.put(uuid, rspMsg);
    }

    public static ResponseMessage get(String reqUUID) {
        return CACHE.getOrDefault(reqUUID, null);
    }

    public static ResponseMessage remove(String reqUUID) {
        return CACHE.remove(reqUUID);
    }

}
