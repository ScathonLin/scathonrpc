package com.scathon.tech.rpc.client.initializer;

import com.scathon.tech.rpc.client.ClientModuleBeanScanFlag;
import com.scathon.tech.rpc.common.init.RpcCommonBeanConfig;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author linhd
 */
@Configuration
@ComponentScan(basePackageClasses = {ClientModuleBeanScanFlag.class})
@Import(value = {RpcServiceProxyInitializer.class, RpcCommonBeanConfig.class})
public class RpcClientAutoInitConfig {
}
