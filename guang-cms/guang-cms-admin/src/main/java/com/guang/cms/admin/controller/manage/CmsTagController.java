package com.guang.cms.admin.controller.manage;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

/**
 * @author huxianguang
 * @create 2017-11-29-下午7:38
 **/
@Controller
@RequestMapping("/manage/tag")
public class CmsTagController {

    @RequestMapping("/index")
    public String index() {
        System.out.println("index============");
        return null;
    }

}
