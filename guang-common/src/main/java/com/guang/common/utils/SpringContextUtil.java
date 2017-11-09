package com.guang.common.utils;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;

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
}
