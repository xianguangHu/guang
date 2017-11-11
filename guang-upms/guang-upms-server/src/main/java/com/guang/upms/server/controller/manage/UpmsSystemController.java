package com.guang.upms.server.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huxianguang
 * @create 2017-11-11-下午10:14
 **/
@Controller
@RequestMapping("/manage/system")
public class UpmsSystemController {

    @RequestMapping(value = "/index" ,method = RequestMethod.GET)
    public String index() {
        return "/manage/system/index.jsp";
    }


    public Object list() {

        return "";
    }
}
