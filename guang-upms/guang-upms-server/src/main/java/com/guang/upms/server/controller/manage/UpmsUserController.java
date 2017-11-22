package com.guang.upms.server.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.guang.common.utils.MD5Util;
import com.guang.common.validator.LengthValidator;
import com.guang.common.validator.NotNullValidator;
import com.guang.upms.common.constant.UpmsResult;
import com.guang.upms.common.constant.UpmsResultConstant;
import com.guang.upms.dao.model.*;
import com.guang.upms.rpc.api.*;
import org.apache.commons.lang.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

/**
 * @author huxianguang
 * @create 2017-11-17-上午9:26
 **/
@Controller
@RequestMapping("/manage/user")
public class UpmsUserController {

    private Logger _log = LoggerFactory.getLogger(UpmsUserController.class);

    @Autowired
    private UpmsUserService upmsUserService;

    @Autowired
    private UpmsUserOrganizationService upmsUserOrganizationService;

    @Autowired
    private UpmsOrganizationService upmsOrganizationService;

    @Autowired
    private UpmsUserRoleService upmsUserRoleService;

    @Autowired
    private UpmsRoleService upmsRoleService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/user/index.jsp";
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, defaultValue = "", value = "sort") String sort,
            @RequestParam(required = false, defaultValue = "", value = "order") String order
    ) {
        UpmsUserExample upmsUserExample = new UpmsUserExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsUserExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            upmsUserExample.or().andUsernameLike("%" + search + "%");
        }
        List<UpmsUser> upmsUsers = upmsUserService.selectByExampleForOffsetPage(upmsUserExample, offset, limit);
        int count = upmsUserService.countByExample(upmsUserExample);
        Map<String, Object> result = new HashMap<String, Object>();
        result.put("rows", upmsUsers);
        result.put("total", count);
        return result;
    }

    @RequestMapping(value = "/create", method = RequestMethod.GET)
    public String create() {
        return "/manage/user/create.jsp";
    }

    @RequestMapping(value = "/create", method = RequestMethod.POST)
    @ResponseBody
    public Object create(UpmsUser upmsUser) {
        //校验
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsUser.getUsername(), new LengthValidator(1, 20, "账号"))
                .on(upmsUser.getPassword(), new LengthValidator(5, 20, "密码"))
                .on(upmsUser.getRealname(), new NotNullValidator("姓名"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH, result.getErrors());
        }
        long time = System.currentTimeMillis();
        String salt = UUID.randomUUID().toString().replaceAll("-", "");
        upmsUser.setSalt(salt);
        upmsUser.setPassword(MD5Util.MD5(upmsUser.getPassword() + salt));
        upmsUser.setCtime(time);
        UpmsUser user = upmsUserService.createUpmsUser(upmsUser);
        if (null == user) {
            return new UpmsResult(UpmsResultConstant.FAILED, "账号已存在");
        }
        _log.info("新增用户，用户主键：userId=>{}", user.getUserId());
        return new UpmsResult(UpmsResultConstant.SUCCESS, 1);
    }


    @RequestMapping(value = "/delete/{ids}", method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        String[] split = ids.split("-");
        int count = 0;
        for (String id : split) {
            count += upmsUserService.deleteByPrimaryKey(Integer.parseInt(id));
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsUser upmsUser = upmsUserService.selectByPrimaryKey(id);
        modelMap.put("user", upmsUser);
        return "/manage/user/update.jsp";
    }

    @RequestMapping(value = "/update/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UpmsUser upmsUser) {
        //校验
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsUser.getRealname(), new NotNullValidator("姓名"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH, result.getErrors());
        }
        upmsUser.setUserId(id);
        upmsUser.setPassword(null);
        //按条件跟新 如果字段为null就不更新
        int count = upmsUserService.updateByPrimaryKeySelective(upmsUser);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }

    @RequestMapping(value = "/organization/{id}", method = RequestMethod.GET)
    public String organization(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsOrganizationExample example = new UpmsOrganizationExample();
        List<UpmsOrganization> upmsOrganizations = upmsOrganizationService.selectByExample(example);
        UpmsUserOrganizationExample upmsUserOrganizationExample = new UpmsUserOrganizationExample();
        upmsUserOrganizationExample.createCriteria()
                .andUserIdEqualTo(id);
        List<UpmsUserOrganization> upmsUserOrganizations = upmsUserOrganizationService.selectByExample(upmsUserOrganizationExample);
        modelMap.put("upmsOrganizations", upmsOrganizations);
        modelMap.put("upmsUserOrganizations", upmsUserOrganizations);
        return "/manage/user/organization.jsp";
    }

    @RequestMapping(value = "/organization/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object organization(@PathVariable("id") int id, HttpServletRequest request) {
        String[] organizationIds = request.getParameterValues("organizationId");
        int organization = upmsUserOrganizationService.organization(id, organizationIds);
        return new UpmsResult(UpmsResultConstant.SUCCESS, organization);
    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.GET)
    public String role(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsUserRoleExample example = new UpmsUserRoleExample();
        example.createCriteria()
                .andUserIdEqualTo(id);
        List<UpmsUserRole> upmsUserRoles = upmsUserRoleService.selectByExample(example);
        UpmsRoleExample upmsRoleExample = new UpmsRoleExample();
        List<UpmsRole> upmsRoles = upmsRoleService.selectByExample(upmsRoleExample);
        modelMap.put("upmsRoles", upmsRoles);
        modelMap.put("upmsUserRoles", upmsUserRoles);
        return "/manage/user/role.jsp";
    }

    @RequestMapping(value = "/role/{id}", method = RequestMethod.POST)
    @ResponseBody
    public Object role(@PathVariable("id") int id, HttpServletRequest request) {
        String[] roleIds = request.getParameterValues("roleId");
        int count = upmsUserRoleService.role(id, roleIds);
        return new UpmsResult(UpmsResultConstant.SUCCESS, count);
    }

    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public String permission(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsUser upmsUser = upmsUserService.selectByPrimaryKey(id);
        modelMap.put("user",upmsUser);
        return "/manage/user/permission.jsp";
    }
}
