package com.scathon.tech.rpc.common.conf;

import org.apache.commons.lang3.StringUtils;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.support.PropertiesLoaderUtils;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Properties;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RpcProperties.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@Component
public class RpcProperties {
    private static final Properties RPC_PROPERTIES = new Properties();

    static {
        try {
            PropertiesLoaderUtils.fillProperties(RPC_PROPERTIES, new ClassPathResource("scathonrpc.properties"
            ));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private RpcProperties() {
    }

    /**
     * 根据配置项的key返回对应的value，如果查找不到返回空字符串.
     *
     * @param key 配置项的key.
     * @return 配置项的值.
     */
    public String getProperty(String key) {
        return RPC_PROPERTIES.getProperty(key, StringUtils.EMPTY);
    }
}
