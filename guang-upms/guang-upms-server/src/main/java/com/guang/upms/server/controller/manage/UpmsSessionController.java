package com.guang.upms.server.controller.manage;

import com.guang.upms.client.shiro.session.UpmsSessionDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;

/**
 * @author huxianguang
 * @create 2017-11-26-下午3:56
 **/
@Controller
@RequestMapping("/manage/session")
public class UpmsSessionController {

    @Autowired
    private UpmsSessionDao sessionDAO;


    @RequestMapping(value = "/index" , method = RequestMethod.GET)
    public String index() {
        return "/manage/session/index.jsp";
    }

    public Object list( @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
                        @RequestParam(required = false, defaultValue = "10", value = "limit") int limit) {

        return sessionDAO.getActiveSessions(offset,limit);
    }
}
