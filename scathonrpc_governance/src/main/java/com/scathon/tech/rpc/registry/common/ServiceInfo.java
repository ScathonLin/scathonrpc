package com.scathon.tech.rpc.registry.common;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Accessors;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ServiceInfo.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@Data
@Builder
@Accessors(chain = true)
public class ServiceInfo {
    private String serviceName;
    private String serviceAddrList;
    private ServiceRoleEnum serviceRole;
}
