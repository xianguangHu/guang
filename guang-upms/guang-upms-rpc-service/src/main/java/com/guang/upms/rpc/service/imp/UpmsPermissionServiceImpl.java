package com.guang.upms.rpc.service.imp;

import com.guang.common.annotation.BaseService;
import com.guang.common.base.BaseServiceImpl;
import com.guang.upms.dao.mapper.UpmsPermissionMapper;
import com.guang.upms.dao.model.UpmsPermission;
import com.guang.upms.dao.model.UpmsPermissionExample;
import com.guang.upms.rpc.api.UpmsPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxianguang
 * @create 2017-11-22-下午1:22
 **/
@Service
@Transactional
@BaseService
public class UpmsPermissionServiceImpl extends BaseServiceImpl<UpmsPermissionMapper,UpmsPermission,UpmsPermissionExample> implements UpmsPermissionService{


    /**
     * 根据roleid来查找权限
     * @param roleId
     * @return
     */
    @Override
    public Object getTreeByRoleId(int roleId) {
        return null;
    }
}
