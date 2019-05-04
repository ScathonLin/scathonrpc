package com.scathon.tech.rpc.client.cache;

import com.scathon.tech.rpc.common.entity.ResponseMessage;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * TODO Function The Class Is.
 *
 * @ClassName LockCache.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/4
 * @Version 1.0
 */
public class LockCache {
    private static final Logger LOGGER = LoggerFactory.getLogger(LockCache.class);
    private static final Map<String, Object> CACHE = new ConcurrentHashMap<>();

    public static void push(String uuid) {
        if (StringUtils.isEmpty(uuid)) {
            LOGGER.error("reques uuid is empty!! please check!!");
            return;
        }
        CACHE.put(uuid, new Object());
    }

    public static Object get(String reqUUID) {
        return CACHE.getOrDefault(reqUUID, null);
    }

    public static Object remove(String reqUUID) {
        return CACHE.remove(reqUUID);
    }
}
