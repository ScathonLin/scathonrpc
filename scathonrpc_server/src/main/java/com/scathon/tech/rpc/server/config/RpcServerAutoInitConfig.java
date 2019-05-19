package com.scathon.tech.rpc.server.config;

import com.scathon.tech.rpc.common.init.RpcCommonBeanConfig;
import com.scathon.tech.rpc.server.ServerModuleBeanScanFlag;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

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
@Import(RpcCommonBeanConfig.class)
public class RpcServerAutoInitConfig {
}
