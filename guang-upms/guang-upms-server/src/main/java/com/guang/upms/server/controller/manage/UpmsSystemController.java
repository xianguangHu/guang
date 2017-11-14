package com.guang.upms.server.controller.manage;

import com.baidu.unbiz.fluentvalidator.ComplexResult;
import com.baidu.unbiz.fluentvalidator.FluentValidator;
import com.baidu.unbiz.fluentvalidator.ResultCollector;
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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
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
}
