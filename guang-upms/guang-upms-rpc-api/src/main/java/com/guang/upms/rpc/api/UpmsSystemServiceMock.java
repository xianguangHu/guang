package com.guang.upms.rpc.api;


import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
* 降级实现UpmsSystemService接口
* Created by shuzheng on 2017/3/20.
*/
public class UpmsSystemServiceMock implements UpmsSystemService {
    private static Logger _log = LoggerFactory.getLogger(UpmsSystemServiceMock.class);

    @Override
    public List<UpmsSystem> selectByExample(UpmsSystemExample example) {
        _log.info("UpmsSystemServiceMock => selectByExample");
        return null;
    }

}
