package com.guang.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

import java.util.Map;

/**
 * Spring 资源文件读取工具
 *
 * @author huxianguang
 * @create 2017-11-08-下午2:22
 **/
public class SpringContextUtil implements ApplicationContextAware{

    private static ApplicationContext context = null;

    private SpringContextUtil() {
        super();
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        context = applicationContext;
    }

    public static <T> T getBean(Class<T> clazz) {
        T t = null;
        Map<String, T> map = context.getBeansOfType(clazz);
        for (Map.Entry<String, T> entry : map.entrySet()) {
            t = entry.getValue();
        }
        return t;
    }
}
