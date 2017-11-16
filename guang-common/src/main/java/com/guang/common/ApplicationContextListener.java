package com.guang.common;

import com.guang.common.annotation.BaseService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Map;


/**
 * @author huxianguang
 * @create 2017-11-14-下午7:31
 **/
public class ApplicationContextListener implements ApplicationListener<ContextRefreshedEvent>{

    private static Logger _log = LoggerFactory.getLogger(ApplicationContextListener.class);

    @Override
    public void onApplicationEvent(ContextRefreshedEvent contextRefreshedEvent) {
        //context没有父类 context就是父类
        if (null == contextRefreshedEvent.getApplicationContext().getParent()) {
            _log.info(">>>>>>> Spring初始化完毕 <<<<<<<<");
            //扫描BaseService注解
            Map<String, Object> baseServices = contextRefreshedEvent.getApplicationContext().getBeansWithAnnotation(BaseService.class);
            for (Object service : baseServices.values()) {
                _log.info(">>>>>> {}.initMapper()",service.getClass().getName());
                try {
                    Method initMapper = service.getClass().getMethod("initMapper");
                    initMapper.invoke(service);
                } catch (Exception e) {
                    _log.error("初始化BaseService的initMapper方法异常",e);
                    e.printStackTrace();
                }
            }
        }
    }
}
