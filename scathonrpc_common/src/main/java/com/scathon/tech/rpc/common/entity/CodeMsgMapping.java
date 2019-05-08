package com.scathon.tech.rpc.common.entity;

import org.apache.commons.collections4.map.HashedMap;

import java.util.Map;
import java.util.function.Function;

import static com.scathon.tech.rpc.common.entity.ResponseCode.*;

/**
 * 错误码和错误信息的映射关系.
 *
 * @ClassName CodeMsgMapping.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
public class CodeMsgMapping {
    public static final Map<ResponseCode, Function<Object[], String>> MAP = new HashedMap<>(1 << 4);

    static {
        MAP.put(SERVICE_NOT_FOUND, (vargs) -> String.format(" remote service : #{ %s }# is not found!",
                (Object[]) vargs));
        MAP.put(FUNC_NOT_FOUND, (vargs) -> String.format(" remote method : #{ %s }# is not found!",
                (Object[]) vargs));
        MAP.put(UNKNOWN_ERROR, (vargs) -> String.format(" unknown error while calling remote service : #{ %s#%s " +
                        "}#!please contact administrator.",
                (Object[]) vargs));
        MAP.put(SUCCESS, (vargs) -> String.format(" successfully calling remote service : #{ %s#%s }#!",
                (Object[]) vargs));
        MAP.put(ACCESS_ERROR, (vargs) -> String.format(" illegal access remote service : #{ %s#%s }#! please contact " +
                        "administrator.",
                (Object[]) vargs));
        MAP.put(TIMEOUT, (vargs) -> String.format(" timeout while calling remote service : #{ %s#%s }#! ",
                (Object[]) vargs));
    }

    public static void main(String[] args) {
        System.out.println(MAP.get(SERVICE_NOT_FOUND).apply(new Object[]{"userservice", 11}));
    }
}
