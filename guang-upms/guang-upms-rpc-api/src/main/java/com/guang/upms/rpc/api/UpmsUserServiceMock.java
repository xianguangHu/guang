package com.guang.upms.rpc.api;


import com.guang.common.base.BaseServiceMock;
import com.guang.upms.dao.mapper.UpmsSystemMapper;
import com.guang.upms.dao.mapper.UpmsUserMapper;
import com.guang.upms.dao.model.UpmsSystem;
import com.guang.upms.dao.model.UpmsSystemExample;
import com.guang.upms.dao.model.UpmsUser;
import com.guang.upms.dao.model.UpmsUserExample;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;


/**
* 降级实现UpmsUserService接口
* Created by huxianguang on 2017/11/17.
*/
public class UpmsUserServiceMock extends BaseServiceMock<UpmsUserMapper,UpmsUser,UpmsUserExample> implements UpmsUserService {
    private static Logger _log = LoggerFactory.getLogger(UpmsUserServiceMock.class);


    @Override
    public UpmsUser createUpmsUser(UpmsUser upmsUser) {
        return null;
    }
}
