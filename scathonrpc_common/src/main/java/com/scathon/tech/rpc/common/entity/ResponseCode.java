package com.scathon.tech.rpc.common.entity;

import java.util.function.Function;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ResponseCode.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
public enum ResponseCode {

    SERVICE_NOT_FOUND(100),
    FUNC_NOT_FOUND(101),
    UNKNOWN_ERROR(102),
    PARAMS_NOT_LEGAL(103),
    ACCESS_ERROR(104),
    SUCCESS(200);


    private int code;

    ResponseCode(int code) {
        this.code = code;
    }
    public static final class CodeMsgMapping{
        //public static final Map<String, Function<>>
    }
}
