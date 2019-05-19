package com.scathon.tech.rpc.common.entity;

import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ResponseMessage.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/18
 * @Version 1.0
 */
@Accessors(chain = true)
public class ResponseMessage {
    @Getter
    @Setter
    private String requestUUID;

    @Getter
    @Setter
    private String status;

    @Getter
    @Setter
    private String errMsg;

    @Getter
    @Setter
    private ResponseCode errorCode;

    @Getter
    @Setter
    private Object respBody;
}
