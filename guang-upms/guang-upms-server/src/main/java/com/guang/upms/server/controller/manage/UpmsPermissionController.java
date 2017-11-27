package com.guang.upms.server.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.guang.common.validator.LengthValidator;
import com.guang.upms.common.constant.UpmsResult;
import com.guang.upms.common.constant.UpmsResultConstant;
import com.guang.upms.dao.model.UpmsPermission;
import com.guang.upms.dao.model.UpmsPermissionExample;
import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;
import com.guang.upms.rpc.api.UpmsPermissionService;
import com.guang.upms.rpc.api.UpmsSystemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author huxianguang
 * @create 2017-11-26-下午2:42
 **/
@Controller
@RequestMapping("/manage/permission")
public class UpmsPermissionController {

    @Autowired
    private UpmsPermissionService upmsPermissionService;

    @Autowired
    private UpmsSystemService upmsSystemService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/permission/index.jsp";
    }

    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false, defaultValue = "0", value = "offset") int offset,
            @RequestParam(required = false, defaultValue = "10", value = "limit") int limit,
            @RequestParam(required = false, defaultValue = "", value = "search") String search,
            @RequestParam(required = false, value = "sort") String sort,
            @RequestParam(required = false, value = "order") String order
    ) {
        UpmsPermissionExample upmsPermissionExample = new UpmsPermissionExample();
        if (StringUtils.isNotBlank(sort) && StringUtils.isNotBlank(order)) {
            upmsPermissionExample.setOrderByClause(sort + " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            upmsPermissionExample.or()
                    .andNameLike(search);
        }
        List<UpmsPermission> upmsPermissions = upmsPermissionService.selectByExampleForOffsetPage(upmsPermissionExample,offset,limit);
        int count = upmsPermissionService.countByExample(upmsPermissionExample);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("rows",upmsPermissions);
        result.put("total",count);
        return result;
    }

    @RequestMapping(value = "/create" ,method = RequestMethod.GET)
    public String create(ModelMap modelMap) {
        UpmsSystemExample example = new UpmsSystemExample();
        example.createCriteria()
                .andStatusEqualTo((byte) 1);
        List<UpmsSystem> upmsSystems = upmsSystemService.selectByExample(example);
        modelMap.put("upmsSystems",upmsSystems);
        return "/manage/permission/create.jsp";
    }

    @RequestMapping(value = "/create" ,method = RequestMethod.POST)
    @ResponseBody
    public Object create(UpmsPermission upmsPermission) {
        //校验
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsPermission.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH,result.getErrors());
        }
        long time = System.currentTimeMillis();
        upmsPermission.setOrders(time);
        int count = upmsPermissionService.insertSelective(upmsPermission);
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }


    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public String update(@PathVariable("id") int id,ModelMap modelMap) {
        UpmsPermission upmsPermission = upmsPermissionService.selectByPrimaryKey(id);
        UpmsSystemExample example = new UpmsSystemExample();
        example.createCriteria()
                .andStatusEqualTo((byte) 1);
        List<UpmsSystem> upmsSystems = upmsSystemService.selectByExample(example);
        modelMap.put("upmsSystems",upmsSystems);
        modelMap.put("permission",upmsPermission);
        return "/manage/permission/update.jsp";
    }

    @RequestMapping(value = "/update/{id}" , method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UpmsPermission upmsPermission) {
        //校验
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsPermission.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH,result.getErrors());
        }
        upmsPermission.setPermissionId(id);
        int count = upmsPermissionService.updateByPrimaryKeySelective(upmsPermission);
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }

    @RequestMapping(value = "/delete/{ids}" , method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        int count = 0;
        String[] split = ids.split("-");
        for (String id : split) {
            count += upmsPermissionService.deleteByPrimaryKey(Integer.parseInt(id));
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }

    @RequestMapping(value = "/role/{id}",method = RequestMethod.POST)
    public Object role(@PathVariable("id") int id) {
        upmsPermissionService.getTreeByRoleId(id);
        return null;
    }
}
