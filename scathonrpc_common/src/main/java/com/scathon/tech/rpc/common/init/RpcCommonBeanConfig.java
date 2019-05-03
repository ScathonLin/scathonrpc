package com.scathon.tech.rpc.common.init;

import com.scathon.tech.rpc.common.CommonModuleBeanScanFlag;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * bean注册类+配置读取类.
 *
 * @ClassName RpcCommonBeanConfig.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@Configuration
@ComponentScan(basePackageClasses = {CommonModuleBeanScanFlag.class})
@PropertySource(value = "scathonrpc.properties")
public class RpcCommonBeanConfig {
}
