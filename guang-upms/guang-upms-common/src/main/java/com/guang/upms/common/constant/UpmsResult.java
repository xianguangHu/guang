package com.guang.upms.common.constant;

import com.guang.common.base.BaseResult;

/**
 * @author huxianguang
 * @create 2017-10-25-下午8:16
 * upms系统常量枚举类
 **/
public class UpmsResult extends BaseResult{

    public UpmsResult(UpmsResultConstant upmsResultConstant, Object data) {
        super(upmsResultConstant.getCode(),upmsResultConstant.getMessage(), data);
    }
}
