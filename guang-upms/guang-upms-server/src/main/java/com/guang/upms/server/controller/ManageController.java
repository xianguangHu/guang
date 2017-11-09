package com.guang.upms.server.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huxianguang
 * @create 2017-10-25-下午2:01
 **/
@Controller
@RequestMapping("/manage")
public class ManageController {


    @RequestMapping("/index")
    public String index(){

        return "/manage/index.jsp";
    }
}
