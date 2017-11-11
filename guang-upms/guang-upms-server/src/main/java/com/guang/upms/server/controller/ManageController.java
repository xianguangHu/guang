package com.guang.upms.server.controller;

import com.guang.upms.dao.model.UpmsPermission;
import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;
import com.guang.upms.dao.model.UpmsUser;
import com.guang.upms.rpc.api.UpmsApiService;
import com.guang.upms.rpc.api.UpmsSystemService;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;


/**
 * @author huxianguang
 * @create 2017-10-25-下午2:01
 **/
@Controller
@RequestMapping("/manage")
public class ManageController {

    @Autowired
    private UpmsApiService upmsApiService;

    @Autowired
    private UpmsSystemService upmsSystemService;

    @RequestMapping("/index")
    public String index(ModelMap modelMap){
        //查询已注册的系统
        UpmsSystemExample example = new UpmsSystemExample();
        example.createCriteria().andStatusEqualTo((byte) 1);
        List<UpmsSystem> upmsSystems = upmsSystemService.selectByExample(example);
        modelMap.put("upmsSystems",upmsSystems);
        Subject subject = SecurityUtils.getSubject();
        String username = (String) subject.getPrincipal();
        UpmsUser user = upmsApiService.selectUpmsUserByUsername(username);
        List<UpmsPermission> upmsPermissions = upmsApiService.selectUpmsPermissionByUpmsUserId(user.getUserId());
        modelMap.put("upmsPermissions",upmsPermissions);
        return "/manage/index.jsp";
    }
}
