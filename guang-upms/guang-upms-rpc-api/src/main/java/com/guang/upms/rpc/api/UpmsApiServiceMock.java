package com.guang.upms.rpc.api;

import com.guang.upms.dao.model.UpmsUser;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;


/**
 * 降级实现UpmsApiService接口
 * Created by shuzheng on 2017/2/14.
 */
public class UpmsApiServiceMock implements UpmsApiService {

    private static Logger _log = LoggerFactory.getLogger(UpmsApiServiceMock.class);



    @Override
    public UpmsUser selectUpmsUserByUsername(String username) {
        _log.info("UpmsApiServiceMock => selectUpmsUserByUsername");
        return null;
    }


}
