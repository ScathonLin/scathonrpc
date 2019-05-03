package com.scathon.tech.rpc.common.utils;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

/**
 * 反射工具类.
 *
 * @ClassName ReflectionUtils.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/3
 * @Version 1.0
 */
public final class ReflectionUtils {

    private ReflectionUtils() {
    }

    /**
     * 反射调用方法.
     *
     * @param obj            目标对象.
     * @param methodName     方法名称.
     * @param parameterTypes 方法参数类型.
     * @param parameters     方法列表.
     * @return
     */
    public static Object invokeMethod(Object obj, String methodName, Class<?>[] parameterTypes, Object[] parameters)
            throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {
        if (obj == null) {
            return null;
        }
        Class<?> clazz = obj.getClass();
        Method method = clazz.getDeclaredMethod(methodName, parameterTypes);
        return method.invoke(obj, parameters);
    }
}
