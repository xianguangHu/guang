package com.guang.upms.rpc.service.imp;

import com.guang.common.annotation.BaseService;
import com.guang.common.base.BaseServiceImpl;
import com.guang.upms.dao.mapper.UpmsUserRoleMapper;
import com.guang.upms.dao.model.UpmsUserRole;
import com.guang.upms.dao.model.UpmsUserRoleExample;
import com.guang.upms.rpc.api.UpmsUserRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxianguang
 * @create 2017-11-20-下午9:53
 **/
@Service
@Transactional
@BaseService
public class UpmsUserRoleServiceImpl extends BaseServiceImpl<UpmsUserRoleMapper,UpmsUserRole,UpmsUserRoleExample> implements UpmsUserRoleService{

    @Autowired
    private UpmsUserRoleMapper upmsUserRoleMapper;

    @Override
    public int role(int id, String[] roleIds) {
        int count = 0;
        //先删除
        UpmsUserRoleExample example = new UpmsUserRoleExample();
        example.createCriteria()
                .andUserIdEqualTo(id);
        upmsUserRoleMapper.deleteByExample(example);
        //在添加
        for (String roleId : roleIds) {
            if (null == roleId) {
                continue;
            }
            UpmsUserRole role = new UpmsUserRole();
            role.setUserId(id);
            role.setRoleId(Integer.parseInt(roleId));
            count += upmsUserRoleMapper.insertSelective(role);
        }
        return count;
    }
}
