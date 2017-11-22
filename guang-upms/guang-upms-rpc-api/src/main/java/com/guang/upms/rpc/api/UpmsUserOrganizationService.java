package com.guang.upms.rpc.api;


import com.guang.common.base.BaseService;
import com.guang.upms.dao.model.UpmsUserOrganization;
import com.guang.upms.dao.model.UpmsUserOrganizationExample;

/**
 * @author huxianguang
 */
public interface UpmsUserOrganizationService extends BaseService<UpmsUserOrganization,UpmsUserOrganizationExample> {

    int organization(int id, String[] organizationIds);
}
