package com.guang.upms.rpc.service.imp;

import com.guang.common.annotation.BaseService;
import com.guang.common.base.BaseServiceImpl;
import com.guang.upms.dao.mapper.UpmsUserMapper;
import com.guang.upms.dao.model.UpmsUser;
import com.guang.upms.dao.model.UpmsUserExample;
import com.guang.upms.rpc.api.UpmsUserService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author huxianguang
 * @create 2017-11-17-上午9:40
 **/
@Service
@Transactional
@BaseService
public class UpmsUserServiceImpl extends BaseServiceImpl<UpmsUserMapper,UpmsUser,UpmsUserExample> implements UpmsUserService{
    private UpmsUserMapper upmsUserMapper;

    @Override
    public UpmsUser createUpmsUser(UpmsUser upmsUser) {
        UpmsUserExample example = new UpmsUserExample();
        example.createCriteria()
                .andUsernameEqualTo(upmsUser.getUsername());
        int count = countByExample(example);
        if (count > 0) {
            return null;
        }
        insert(upmsUser);
        return upmsUser;
    }
}
