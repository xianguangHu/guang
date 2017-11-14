package com.guang.upms.rpc.api;

import com.guang.upms.dao.model.UpmsPermission;
import com.guang.upms.dao.model.UpmsRole;
import com.guang.upms.dao.model.UpmsUser;

import java.util.List;

/**
 * upms系统接口
 */
public interface UpmsApiService {

    /**
     * 根据username来查询UmpsUser
     * @return
     */
    UpmsUser selectUpmsUserByUsername(String username);

    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId);

    /**
     * 根据用户id获取所拥有的
     * @param upmsUserId
     * @return
     */
    List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId);
}
