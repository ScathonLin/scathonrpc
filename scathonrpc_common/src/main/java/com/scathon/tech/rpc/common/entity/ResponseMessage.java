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
 * @Date 2019/5/3
 * @Version 1.0
 */
@Accessors(chain = true)
public class ResponseMessage {
    /**
     * 请求UUID.
     */
    @Getter
    @Setter
    private String requestUUID;
    /**
     * 请求状态.
     */
    @Getter
    @Setter
    private String status;
    /**
     * 请求错误信息.
     */
    @Getter
    @Setter
    private String errMsg;

    /**
     * 请求响应错误码.
     */
    @Getter
    @Setter
    private ResponseCode errCode;

    /**
     * 请求返回体.
     */
    @Getter
    @Setter
    private Object respBody;
}
