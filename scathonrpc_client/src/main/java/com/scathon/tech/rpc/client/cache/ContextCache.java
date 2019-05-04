package com.scathon.tech.rpc.client.cache;

import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ContextCache.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/4
 * @Version 1.0
 */
public class ContextCache {
    private static final ApplicationContext CONTEXT = new AnnotationConfigApplicationContext();
    static {
    }
}
