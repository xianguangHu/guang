package com.guang.upms.rpc.api;


import com.guang.common.base.BaseServiceMock;
import com.guang.upms.dao.mapper.UpmsUserOrganizationMapper;
import com.guang.upms.dao.model.UpmsUserOrganization;
import com.guang.upms.dao.model.UpmsUserOrganizationExample;

/**
 * @author huxianguang
 */
public class UpmsUserOrganizationServiceMock extends BaseServiceMock<UpmsUserOrganizationMapper,UpmsUserOrganization,UpmsUserOrganizationExample> implements UpmsUserOrganizationService{

    @Override
    public int organization(int id, String[] organizationIds) {
        return 0;
    }
}
