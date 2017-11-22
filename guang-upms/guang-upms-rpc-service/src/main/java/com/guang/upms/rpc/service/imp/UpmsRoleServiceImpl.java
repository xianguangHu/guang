package com.guang.upms.rpc.service.imp;

import com.guang.common.annotation.BaseService;
import com.guang.common.base.BaseServiceImpl;
import com.guang.upms.dao.mapper.UpmsRoleMapper;
import com.guang.upms.dao.model.UpmsRole;
import com.guang.upms.dao.model.UpmsRoleExample;
import com.guang.upms.rpc.api.UpmsRoleService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxianguang
 * @create 2017-11-20-下午10:09
 **/
@Service
@Transactional
@BaseService
public class UpmsRoleServiceImpl extends BaseServiceImpl<UpmsRoleMapper,UpmsRole,UpmsRoleExample> implements UpmsRoleService{

    private UpmsRoleMapper upmsRoleMapper;

}
