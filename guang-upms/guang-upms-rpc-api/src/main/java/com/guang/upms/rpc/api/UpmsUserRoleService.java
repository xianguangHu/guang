package com.guang.upms.rpc.api;


import com.guang.common.base.BaseService;
import com.guang.upms.dao.model.UpmsUserRole;
import com.guang.upms.dao.model.UpmsUserRoleExample;

public interface UpmsUserRoleService extends BaseService<UpmsUserRole,UpmsUserRoleExample> {
    int role(int id, String[] roleIds);
}
