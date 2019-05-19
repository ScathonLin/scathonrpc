package com.scathon.tech.rpc.client.initializer;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.annotation.ScannedGenericBeanDefinition;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.type.classreading.MetadataReader;
import org.springframework.core.type.classreading.SimpleMetadataReaderFactory;

import java.io.IOException;
import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * TODO Function The Class Is.
 *
 * @ClassName RpcBeanScanner.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
public class RpcBeanScanner {

    private static final Logger LOGGER = LoggerFactory.getLogger(RpcBeanScanner.class);

    /**
     * 在指定的路径下面，扫描被特定注解修饰的类.
     *
     * @param pathPattern      路径表达式.
     * @param targetAnnotation 目标注解.
     * @return 目标注解修饰的类的bean定义.
     */
    public static List<BeanDefinition> doScan(String pathPattern, Class targetAnnotation) {
        try {
            Set<BeanDefinition> candidates = new LinkedHashSet<>();
            PathMatchingResourcePatternResolver resPatnRslvr =
                    new PathMatchingResourcePatternResolver();
            Resource[] resources = resPatnRslvr.getResources(pathPattern);
            SimpleMetadataReaderFactory metaRdrFactory = new SimpleMetadataReaderFactory();
            for (Resource resource : resources) {
                LOGGER.info("resource is : {}", resource);
                MetadataReader metadataReader = metaRdrFactory.getMetadataReader(resource);
                ScannedGenericBeanDefinition sbd =
                        new ScannedGenericBeanDefinition(metadataReader);
                sbd.setResource(resource);
                sbd.setSource(resource);
                candidates.add(sbd);
            }

            return candidates.stream().filter(beanDefinition -> {
                String className = beanDefinition.getBeanClassName();
                Annotation annotation;
                try {
                    annotation = Class.forName(className).getAnnotation(targetAnnotation);
                    return annotation != null;
                } catch (ClassNotFoundException e) {
                    LOGGER.warn("error while scan class decorated by {} in the path: {}",
                            targetAnnotation.getSimpleName(), pathPattern);
                }
                return false;
            }).collect(Collectors.toList());


        } catch (IOException e) {
            LOGGER.error("error while scan class files in the path: {}", pathPattern);
        }
        return new ArrayList<>(0);
    }
}
