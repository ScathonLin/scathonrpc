package com.scathon.tech.rpc.common.conf;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;

import java.io.IOException;
import java.util.Properties;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RpcConfig.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/4
 * @Version 1.0
 */
public class RpcConfig {
    private static final Properties PROP = new Properties();

    static {
        try {
            PropertiesLoaderUtils.fillProperties(PROP, new ClassPathResource("scathonrpc.properties"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private static final RpcConfig INSTANCE = new RpcConfig();

    public static RpcConfig getINSTANCE() {
        return INSTANCE;
    }

    public String getProperty(String propKey) {
        return PROP.getProperty(propKey, StringUtils.EMPTY);
    }
}
