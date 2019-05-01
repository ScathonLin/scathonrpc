package com.scathon.tech.rpc.registry.init;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * TODO Function The Class Is.
 *
 * @ClassName CommonInitConfig.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@Configuration
@ComponentScan(basePackages = "com.scathon.tech.rpc.registry")
@PropertySource(value = "scathonrpc.properties")
public class RegistryInitConfig {
    @Bean
    public ZookeeperInit zookeeperInit() {
        return new ZookeeperInit();
    }
}
