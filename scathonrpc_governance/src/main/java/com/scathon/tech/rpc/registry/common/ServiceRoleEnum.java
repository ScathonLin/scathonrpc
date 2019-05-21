package com.scathon.tech.rpc.registry.common;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ServiceRoleEnum.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
public enum ServiceRoleEnum {
    /**
     * 服务消费者.
     */
    SERVICE_CONSUMER("CONSUMER"),
    /**
     * 服务生产者.
     */
    SERVICE_PROVIDER("PROVIDER");

    /**
     * 服务角色.
     */
    private String roleName;

    ServiceRoleEnum(String roleName) {
        this.roleName = roleName;
    }
}
