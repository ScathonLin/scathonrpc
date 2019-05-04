package com.scathon.tech.rpc.server.config;

import com.scathon.tech.rpc.server.ServerModuleBeanScanFlag;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ServerBeanConfig.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@Configuration
@ComponentScan(basePackageClasses = {ServerModuleBeanScanFlag.class})
public class ServerBeanConfig {
}
