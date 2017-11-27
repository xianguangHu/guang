package com.guang.upms.server.controller;


import com.guang.common.utils.RedisUtil;
import com.guang.upms.client.shiro.session.UpmsSession;
import com.guang.upms.client.shiro.session.UpmsSessionDao;
import com.guang.upms.common.constant.UpmsResult;
import com.guang.upms.common.constant.UpmsResultConstant;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.UUID;

/**
 * @author huxianguang
 * @create 2017-10-25-下午8:03
 **/
@Controller
@RequestMapping("/sso")
public class SSOController {

    //全局会话key
    private final static String GUANG_UPMS_SERVER_SESSION_ID = "guang-upms-server-session-id";

    //全局会话key列表
    private final static String GUANG_UPMS_SERVER_SESSION_IDS = "guang-upms-server-session-ids";

    //全局code校验值
    private final static String GUANG_UPMS_SERVER_CODE = "guang-upms-server-code";
    @Autowired
    private UpmsSessionDao sessionDAO;

    @RequestMapping(value = "login", method = RequestMethod.POST)
    @ResponseBody
    public UpmsResult login(HttpServletRequest request, HttpServletResponse response, ModelMap modelMap) {
        String username = request.getParameter("username");
        String password = request.getParameter("password");
        String rememberMe = request.getParameter("rememberMe");
        if (StringUtils.isBlank(username)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_USERNAME, "账号不能为空");
        }
        if (StringUtils.isBlank(password)) {
            return new UpmsResult(UpmsResultConstant.EMPTY_PASSWORD, "密码不能为空");
        }
        Subject subject = SecurityUtils.getSubject();
        //获取会话 如果会话不存在将创建一个新的会话与之关联
        Session session = subject.getSession();
        String sessionId = session.getId().toString();
        //判断是否已登录，如果已经登录就回跳，防止重复登录
        String hasCode = RedisUtil.get(GUANG_UPMS_SERVER_SESSION_ID + "_" +sessionId);
        // code 校验值
        if (StringUtils.isBlank(hasCode)) {
            //说明redis中没有session缓存
            //使用shiro认证
            try {
                UsernamePasswordToken token = new UsernamePasswordToken(username, password);
                //判断是否自动登录
                if (BooleanUtils.toBoolean(rememberMe)) {
                    token.setRememberMe(true);
                } else {
                    token.setRememberMe(false);
                }
                subject.login(token);
            } catch (UnknownAccountException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_USERNAME, "帐号不存在！");
            } catch (IncorrectCredentialsException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_PASSWORD, "密码错误！");
            } catch (LockedAccountException e) {
                return new UpmsResult(UpmsResultConstant.INVALID_ACCOUNT, "帐号已锁定！");
            }

            //更新session
            sessionDAO.updateStatus(sessionId, UpmsSession.OnlineStatus.on_line);
            //全局会话sessionId列表，供会话列表使用
            RedisUtil.lpush(GUANG_UPMS_SERVER_SESSION_IDS,sessionId.toString());
            //默认验证账号密码正确
            String code = UUID.randomUUID().toString();
            // 全局会话的code
            RedisUtil.set(GUANG_UPMS_SERVER_SESSION_ID + "_" + sessionId, code, (int) subject.getSession().getTimeout() / 1000);
            // code校验值
            RedisUtil.set(GUANG_UPMS_SERVER_CODE + "_" + code, code, (int) subject.getSession().getTimeout() / 1000);
        }
        // 回跳登录前地址
        String backurl = request.getParameter("backurl");
        if (StringUtils.isBlank(backurl)) {
            return new UpmsResult(UpmsResultConstant.SUCCESS, "/success");
        } else {
            return new UpmsResult(UpmsResultConstant.SUCCESS, "/success");
        }
    }


    @RequestMapping("")
    public String index() {
        return "/sso/login.jsp";
    }

    @RequestMapping(value = "/logout", method = RequestMethod.GET)
    public String logout(HttpServletRequest request) {
        // shiro退出登录
        SecurityUtils.getSubject().logout();
        // 跳回原地址
        String redirectUrl = request.getHeader("Referer");
        if (null == redirectUrl) {
            redirectUrl = "/";
        }
        return "redirect:" + redirectUrl;
    }
}
