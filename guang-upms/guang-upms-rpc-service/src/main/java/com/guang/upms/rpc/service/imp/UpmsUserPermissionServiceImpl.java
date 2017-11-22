package com.guang.upms.rpc.service.imp;

import com.guang.common.annotation.BaseService;
import com.guang.common.base.BaseServiceImpl;
import com.guang.upms.dao.mapper.UpmsUserPermissionMapper;
import com.guang.upms.dao.model.UpmsUserPermission;
import com.guang.upms.dao.model.UpmsUserPermissionExample;
import com.guang.upms.rpc.api.UpmsUserPermissionService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxianguang
 * @create 2017-11-22-下午1:32
 **/
@Service
@Transactional
@BaseService
public class UpmsUserPermissionServiceImpl extends BaseServiceImpl<UpmsUserPermissionMapper,UpmsUserPermission,UpmsUserPermissionExample> implements UpmsUserPermissionService{
}
