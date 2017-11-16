package com.guang.upms.rpc.service.imp;

import com.guang.common.annotation.BaseService;
import com.guang.common.base.BaseServiceImpl;
import com.guang.upms.dao.mapper.UpmsOrganizationMapper;
import com.guang.upms.dao.model.UpmsOrganization;
import com.guang.upms.dao.model.UpmsOrganizationExample;
import com.guang.upms.rpc.api.UpmsOrganizationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxianguang
 * @create 2017-11-15-下午9:46
 **/
@Service
@Transactional
@BaseService
public class UpmsOrganizationServiceImpl extends BaseServiceImpl<UpmsOrganizationMapper,UpmsOrganization,UpmsOrganizationExample> implements UpmsOrganizationService{

    @Autowired
    private UpmsOrganizationMapper upmsOrganizationMapper;
}
