package com.guang.upms.rpc.mapper;


import com.guang.upms.dao.model.UpmsPermission;
import com.guang.upms.dao.model.UpmsRole;

import java.util.List;

/**
 *  用户VoMapper
 */
public interface UpmsApiMapper {

    /**
     * 根据用户id来获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId);

    List<UpmsRole> selectUpmsRoleByUpmsUserId(Integer upmsUserId);
}
