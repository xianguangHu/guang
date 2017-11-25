package com.guang.upms.rpc.service.imp;

import com.guang.common.annotation.BaseService;
import com.guang.common.base.BaseServiceImpl;
import com.guang.upms.dao.mapper.UpmsRolePermissionMapper;
import com.guang.upms.dao.model.UpmsRolePermission;
import com.guang.upms.dao.model.UpmsRolePermissionExample;
import com.guang.upms.rpc.api.UpmsRolePermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxianguang
 * @create 2017-11-25-下午9:57
 **/
@Service
@Transactional
@BaseService
public class UpmsRolePermissionServiceImpl extends BaseServiceImpl<UpmsRolePermissionMapper,UpmsRolePermission,UpmsRolePermissionExample> implements UpmsRolePermissionService{
}
