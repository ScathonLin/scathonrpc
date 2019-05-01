package com.scathon.tech.rpc.common.annotations;

import org.springframework.stereotype.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Rpc Service Publisher Annotation..
 *
 * @author linhd
 * @since 2019-04-27
 */
@Component
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE})
public @interface RpcServicePublisher {
    String name();
}
