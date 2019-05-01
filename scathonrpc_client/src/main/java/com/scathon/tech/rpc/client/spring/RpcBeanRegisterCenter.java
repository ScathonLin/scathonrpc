package com.scathon.tech.rpc.client.spring;

import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;
import org.springframework.stereotype.Component;

@Component
public class RpcBeanRegisterCenter implements ImportBeanDefinitionRegistrar {
    @Override
    public void registerBeanDefinitions(AnnotationMetadata annotationMetadata,
                                        BeanDefinitionRegistry beanDefinitionRegistry) {
        //val annoAllAttributes = annotationMetadata.getAnnotationAttributes(RpcServiceSubscriber.class.getName());
        //val annoAttrs = AnnotationAttributes.fromMap(annoAllAttributes)
        //val scanner = new ClassPathBeanDefinitionScanner(beanDefinitionRegistry)
    }
}
