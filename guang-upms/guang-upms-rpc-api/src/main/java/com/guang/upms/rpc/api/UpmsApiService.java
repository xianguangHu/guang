package com.guang.upms.rpc.api;

import com.guang.upms.dao.model.UpmsUser;

/**
 * upms系统接口
 */
public interface UpmsApiService {

    /**
     * 根据username来查询UmpsUser
     * @return
     */
    UpmsUser selectUpmsUserByUsername(String username);
}
