package com.guang.upms.rpc.api;


import com.guang.common.base.BaseService;
import com.guang.common.base.BaseServiceMock;
import com.guang.upms.dao.mapper.UpmsUserRoleMapper;
import com.guang.upms.dao.model.UpmsUserRole;
import com.guang.upms.dao.model.UpmsUserRoleExample;

public class UpmsUserRoleServiceMock extends BaseServiceMock<UpmsUserRoleMapper,UpmsUserRole,UpmsUserRoleExample> implements UpmsUserRoleService{
    @Override
    public int role(int id, String[] roleIds) {
        return 0;
    }
}
