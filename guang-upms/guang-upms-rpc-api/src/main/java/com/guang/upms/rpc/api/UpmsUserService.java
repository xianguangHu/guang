package com.guang.upms.rpc.api;

import com.guang.common.base.BaseService;
import com.guang.upms.dao.model.UpmsUser;
import com.guang.upms.dao.model.UpmsUserExample;

public interface UpmsUserService extends BaseService<UpmsUser,UpmsUserExample>{

    UpmsUser createUpmsUser(UpmsUser upmsUser);
}
