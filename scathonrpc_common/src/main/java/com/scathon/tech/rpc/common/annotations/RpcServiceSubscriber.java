package com.scathon.tech.rpc.common.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rpc Service Subscriber Annotation..
 *
 * @author linhd
 * @since 2019-04-27
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.FIELD})
public @interface RpcServiceSubscriber {
    String name();
}
