package com.scathon.tech.rpc.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import lombok.experimental.Accessors;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RequestMessage.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
@Accessors(chain = true)
@ToString
public class RequestMessage {
    /**
     * 请求UUID.
     */
    @Getter
    @Setter
    private String requestUUID;
    /**
     * 请求的服务名称.
     */
    @Getter
    @Setter
    private String serviceName;
    /**
     * 请求的功能名称（方法名称）.
     */
    @Getter
    @Setter
    private String functionName;
    /**
     * 参数类型列表.
     */
    @Getter
    @Setter
    private Class<?>[] parameterTypes;

    /**
     * 参数列表.
     */
    @Getter
    @Setter
    private Object[] parameters;
}
