package com.scathon.tech.rpc.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RequestMessage.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/18
 * @Version 1.0
 */
@Accessors(chain = true)
public class RequestMessage {
    @Getter
    @Setter
    private String requestUUID;

    @Getter
    @Setter
    private String serviceName;

    @Getter
    @Setter
    private String methodName;

    @Getter
    @Setter
    private Class<?>[] paramTypes;

    @Getter
    @Setter
    private Object[] paramObjs;
}

