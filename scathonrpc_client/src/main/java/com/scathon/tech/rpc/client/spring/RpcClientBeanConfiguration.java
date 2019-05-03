package com.scathon.tech.rpc.client.spring;

import com.scathon.tech.rpc.client.ClientModuleBeanScanFlag;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;

/**
 * @author linhd
 */
@Configuration
@ComponentScan(basePackageClasses = {ClientModuleBeanScanFlag.class})
@Import(value = {RpcProxyInitializer.class})
public class RpcClientBeanConfiguration {
}
