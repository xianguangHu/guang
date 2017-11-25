package com.guang.upms.server.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.guang.common.validator.LengthValidator;
import com.guang.upms.common.constant.UpmsResult;
import com.guang.upms.common.constant.UpmsResultConstant;
import com.guang.upms.dao.model.*;
import com.guang.upms.rpc.api.UpmsPermissionService;
import com.guang.upms.rpc.api.UpmsRolePermissionService;
import com.guang.upms.rpc.api.UpmsRoleService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huxianguang
 * @create 2017-11-25-下午8:56
 **/
@Controller
@RequestMapping("/manage/role")
public class UpmsRoleController {

    @Autowired
    private UpmsRoleService upmsRoleService;


    @Autowired
    private UpmsRolePermissionService upmsRolePermissionService;

    @Autowired
    private UpmsPermissionService upmsPermissionService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/role/index.jsp";
    }


    @RequestMapping(value = "/permission/{id}", method = RequestMethod.GET)
    public String permission(@PathVariable("id") int id,ModelMap modelMap) {
        UpmsRole role = upmsRoleService.selectByPrimaryKey(id);
        modelMap.put("role", role);
        return "/manage/role/permission.jsp";
    }

    @RequestMapping(value = "/permission/{id}", method = RequestMethod.POST)
    public String permission(@PathVariable("id") int id, HttpServletRequest request) {
        String datas = request.getParameter("datas");

        return null;
    }

    @RequestMapping(value = "/list", method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order
    ) {
        UpmsRoleExample upmsRoleExample = new UpmsRoleExample();
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            upmsRoleExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            upmsRoleExample.or()
                    .andTitleLike(search);
        }
        List<UpmsRole> upmsRoles = upmsRoleService.selectByExample(upmsRoleExample);
        int count = upmsRoleService.countByExample(upmsRoleExample);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("rows",upmsRoles);
        result.put("total",count);
        return result;
    }

    @RequestMapping(value = "/create" , method = RequestMethod.GET)
    public String create() {
        return "/manage/role/create.jsp";
    }

    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    @ResponseBody
    public Object create(UpmsRole upmsRole) {
        //验证
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsRole.getName(), new LengthValidator(5, 20, "名称"))
                .on(upmsRole.getTitle(), new LengthValidator(5, 20, "标题"))
                .on(upmsRole.getDescription(), new LengthValidator(5, 20, "描述"))
                .doValidate()
                .result(ResultCollectors.toComplex());

        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH,result.getErrors());
        }
        long time = System.currentTimeMillis();
        upmsRole.setCtime(time);
        upmsRole.setOrders(time);
        int count = upmsRoleService.insertSelective(upmsRole);
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }


    @RequestMapping(value = "/update/{id}" , method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsRole upmsRole = upmsRoleService.selectByPrimaryKey(id);
        modelMap.put("role",upmsRole);
        return "/manage/role/update.jsp";
    }


    @RequestMapping(value = "/update/{id}" , method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id,UpmsRole upmsRole) {
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsRole.getName(), new LengthValidator(5, 20, "名称"))
                .on(upmsRole.getTitle(), new LengthValidator(5, 20, "标题"))
                .on(upmsRole.getDescription(), new LengthValidator(5, 20, "描述"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH,result.getErrors());
        }
        upmsRole.setRoleId(id);
        int count = upmsRoleService.updateByPrimaryKeySelective(upmsRole);
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }

    @RequestMapping(value = "/delete/{ids}" , method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        String[] split = ids.split("-");
        int count = 0;
        for (String id : split) {
            count += upmsRoleService.deleteByPrimaryKey(Integer.parseInt(id));
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }
}
