package com.scathon.tech.rpc.registry.init;

import com.scathon.tech.rpc.registry.RegistryModuleBeanScanFlag;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * 服务注册初始化配置类.
 *
 * @ClassName CommonInitConfig.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@Configuration
@ComponentScan(basePackageClasses = RegistryModuleBeanScanFlag.class)
public class RegistryInitConfig {
    @Bean
    public ZookeeperInit zookeeperInit() {
        return new ZookeeperInit();
    }
}
