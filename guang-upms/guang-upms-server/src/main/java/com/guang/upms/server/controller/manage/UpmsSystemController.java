package com.guang.upms.server.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollectors;
import com.guang.common.validator.LengthValidator;
import com.guang.upms.common.constant.UpmsResult;
import com.guang.upms.common.constant.UpmsResultConstant;
import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;
import com.guang.upms.rpc.api.UpmsSystemService;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/** 实现BaseService
 * 通过反射获取mapperd
 * @author huxianguang
 * @create 2017-11-11-下午10:14
 **/
@Controller
@RequestMapping("/manage/system")
public class UpmsSystemController {

    @Autowired
    private UpmsSystemService upmsSystemService;

    @RequestMapping(value = "/index" ,method = RequestMethod.GET)
    public String index() {
        return "/manage/system/index.jsp";
    }

    @RequestMapping(value = "/list" , method = RequestMethod.GET)
    @ResponseBody
    public Object list(@RequestParam(required = false,defaultValue = "0",value = "offset") int offset,
                       @RequestParam(required = false,defaultValue = "10",value = "limit") int limit,
                       @RequestParam(required = false,defaultValue = "",value = "search") String search,
                       @RequestParam(required = false, value = "sort") String sort,
                       @RequestParam(required = false, value = "order") String order) {
        UpmsSystemExample example = new UpmsSystemExample();
        if (!StringUtils.isBlank(sort) && !StringUtils.isBlank(order)) {
            example.setOrderByClause(sort+ " " + order);
        }
        if (StringUtils.isNotBlank(search)) {
            example.or().andTitleLike("%" + search + "%");
        }
        List<UpmsSystem> upmsSystems = upmsSystemService.selectByExampleForOffsetPage(example, offset, limit);
        long total = upmsSystems.size();
        Map<String,Object> result = new HashMap<String, Object>();
        result.put("rows",upmsSystems);
        result.put("total",total);
        return result;
    }

    @RequestMapping(value = "/create",method = RequestMethod.GET)
    public String create() {
        return "/manage/system/create.jsp";
    }

    @RequestMapping(value = "/create",method = RequestMethod.POST)
    @ResponseBody
    public Object create(UpmsSystem upmsSystem) {
        //fluent-validator校验字段
        ComplexResult result = FluentValidator.checkAll()
                .on(upmsSystem.getTitle(), new LengthValidator(1, 20, "标题"))
                .on(upmsSystem.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()//当执行到这一步才会真正的验证
                .result(ResultCollectors.toComplex());//带详细错误信息的rusult

        if (!result.isSuccess()) {
            //验证失败
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH,result.getErrors());
        }
        long time = System.currentTimeMillis();
        upmsSystem.setCtime(time);
        upmsSystem.setOrders(time);
        int count = upmsSystemService.insertSelective(upmsSystem);
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }

    /**
     * @PathVariable 映射 URL 绑定的占位符
     * @param id
     * @param modelMap
     * @return
     */
    @RequestMapping(value = "/update/{id}",method = RequestMethod.GET)
    public String update(@PathVariable("id") int id, ModelMap modelMap) {
        UpmsSystem upmsSystem = upmsSystemService.selectByPrimaryKey(id);
        modelMap.put("system",upmsSystem);
        return "/manage/system/update.jsp";
    }

    @RequestMapping(value = "/update/{id}",method = RequestMethod.POST)
    @ResponseBody
    public Object update(@PathVariable("id") int id, UpmsSystem system) {
        //校验
        ComplexResult result = FluentValidator.checkAll()
                .on(system.getTitle(), new LengthValidator(1, 20, "标题"))
                .on(system.getName(), new LengthValidator(1, 20, "名称"))
                .doValidate()
                .result(ResultCollectors.toComplex());
        if (!result.isSuccess()) {
            return new UpmsResult(UpmsResultConstant.INVALID_LENGTH,result.getErrors());
        }
        system.setSystemId(id);
        //验证成功
        int count = upmsSystemService.updateByPrimaryKeySelective(system);
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }

    @RequestMapping(value = "/delete/{ids}" ,method = RequestMethod.GET)
    @ResponseBody
    public Object delete(@PathVariable("ids") String ids) {
        String[] splitId = ids.split("-");
        int count = 0;
        for (String id : splitId) {
            count += upmsSystemService.deleteByPrimaryKey(Integer.parseInt(id));
        }
        return new UpmsResult(UpmsResultConstant.SUCCESS,count);
    }
}
