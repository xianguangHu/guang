package com.guang.upms.rpc;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.support.ClassPathXmlApplicationContext;

import java.io.IOException;

/**
 * 通用用户权限系统
 * upmsRpsService服务启动类
 * @author huxianguang
 * @create 2017-11-07-上午10:50
 **/
public class GuangUpmsRpsServiceApplication {

    //初始化日志对象，打印会带有类名
    private static Logger _log = LoggerFactory.getLogger(GuangUpmsRpsServiceApplication.class);


    public static void main(String[] args) throws IOException {
        _log.info(">>>>>> guang-upms-rps-service 正在启动 <<<<<<");
        new ClassPathXmlApplicationContext("classpath:META-INF/spring/*.xml");
        _log.info(">>>>>> guang-upms-rps-service 启动完成 <<<<<<");
        System.in.read();

    }
}
