package com.guang.upms.rpc.service.imp;

import com.guang.common.annotation.BaseService;
import com.guang.common.base.BaseServiceImpl;
import com.guang.upms.dao.mapper.UpmsUserOrganizationMapper;

import com.guang.upms.dao.model.UpmsUserOrganization;
import com.guang.upms.dao.model.UpmsUserOrganizationExample;
import com.guang.upms.rpc.api.UpmsUserOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxianguang
 * @create 2017-11-20-下午4:01
 **/
@Service
@Transactional
@BaseService
public class UpmsUserOrganizationServiceImpl extends BaseServiceImpl<UpmsUserOrganizationMapper,UpmsUserOrganization,UpmsUserOrganizationExample> implements UpmsUserOrganizationService{

    @Autowired
    private UpmsUserOrganizationMapper upmsUserOrganizationMapper;

    @Override
    public int organization(int id, String[] organizationIds) {
        int count = 0;
        //先删除
        UpmsUserOrganizationExample example = new UpmsUserOrganizationExample();
        example.createCriteria()
                .andUserIdEqualTo(id);
        upmsUserOrganizationMapper.deleteByExample(example);
        for (String organizationId : organizationIds) {
            if (null == organizationId) {
                continue;
            }
            UpmsUserOrganization upmsUserOrganization = new UpmsUserOrganization();
            upmsUserOrganization.setUserId(id);
            upmsUserOrganization.setOrganizationId(Integer.parseInt(organizationId));
            count += upmsUserOrganizationMapper.insertSelective(upmsUserOrganization);
        }

        return count;
    }
}
