package com.guang.upms.server.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.guang.common.validator.LengthValidator;
import com.guang.upms.common.constant.UpmsResult;
import com.guang.upms.common.constant.UpmsResultConstant;
import com.guang.upms.dao.model.UpmsOrganization;
import com.guang.upms.dao.model.UpmsOrganizationExample;
import com.guang.upms.rpc.api.UpmsOrganizationService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 组织
 * @author huxianguang
 * @create 2017-11-15-下午9:34
 **/
@Controller
@RequestMapping("/manage/organization")
public class UpmsOrganizationController {

    @Autowired
    private UpmsOrganizationService upmsOrganizationService;

    @RequestMapping(value = "/index", method = RequestMethod.GET)
    public String index() {
        return "/manage/organization/index.jsp";
    }

    @RequestMapping(value = "/list",method = RequestMethod.GET)
    @ResponseBody
    public Object list(
            @RequestParam(required = false,defaultValue = "0",value = "offset") int offset,
            @RequestParam(required = false,defaultValue = "10",value = "limit") int limit,
            @RequestParam(required = false,defaultValue = "",value = "search") String search,
            @RequestParam(required = false,value = "sort") String sort,
            @RequestParam(required = false,value = "order") String order
    ) {
        UpmsOrganizationExample upmsOrganizationExample = new UpmsOrganizationExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            upmsOrganizationExample.setOrderByClause(sort + " " +order);
        }
        if (StringUtils.isNotBlank(search)) {
            upmsOrganizationExample.or()
                    .andNameLike("%"+ search +"%");
        }
        List<UpmsOrganization> upmsOrganizations = upmsOrganizationService.selectByExampleForOffsetPage(upmsOrganizationExample,offset,limit);
        long count = upmsOrganizationService.countByExample(upmsOrganizationExample);
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("rows",upmsOrganizations);
        result.put("total",count);
        return result;
    }

    @RequestMapping(value = "/create" , method = RequestMethod.GET)
    public String create() {
        return "/manage/organization/create.jsp";
    }

    @RequestMapping(value = "/create" , method = RequestMethod.POST)
    @ResponseBody
    public Object create(UpmsOrganization upmsOrganization) {
        //校验
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsOrganization.getName(), new LengthValidator(1, 20, "名字"))
                .on(upmsOrganization.getDescription(), new LengthValidator(1, 20, "描述"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH,result.getErrors());
        }
        long time = System.currentTimeMillis();
        upmsOrganization.setCtime(time);
        int count = upmsOrganizationService.insertSelective(upmsOrganization);
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }

    @RequestMapping(value = "/update/{id}" , method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap)  {
        UpmsOrganization upmsOrganization = upmsOrganizationService.selectByPrimaryKey(id);
        modelMap.put("organization",upmsOrganization);
        return "/manage/organization/update.jsp";
    }

    @RequestMapping(value = "/update/{id}" , method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id,UpmsOrganization upmsOrganization)  {
        //校验
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsOrganization.getName(), new LengthValidator(1, 20, "名字"))
                .on(upmsOrganization.getDescription(), new LengthValidator(1, 20, "描述"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH,result.getErrors());
        }
        upmsOrganization.setOrganizationId(id);
        int count = upmsOrganizationService.updateByPrimaryKeySelective(upmsOrganization);
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }

    @RequestMapping(value = "/delete/{ids}" , method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        String[] split = ids.split("-");
        int count = 0;
        for (String id : split) {
            count += upmsOrganizationService.deleteByPrimaryKey(Integer.parseInt(id));
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }
}
