package com.guang.upms.rpc.service.imp;

import com.guang.upms.dao.mapper.UpmsUserMapper;
import com.guang.upms.dao.model.UpmsUser;
import com.guang.upms.dao.model.UpmsUserExample;
import com.guang.upms.rpc.api.UpmsApiService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import java.util.List;

/**
 * upms接口实现类
 * @author huxianguang
 * @create 2017-11-07-上午10:33
 **/
@Service
@Transactional
public class UpmsApiServiceImpl implements UpmsApiService{

    @Autowired
    private UpmsUserMapper upmsUserMapper;

    /**
     * 根据username查询UpmsUser
     * @param username
     * @return
     */
    @Override
    public UpmsUser selectUpmsUserByUsername(String username) {
        UpmsUserExample example = new UpmsUserExample();
        example.createCriteria()
                .andUsernameEqualTo(username);
        List<UpmsUser> upmsUsers = upmsUserMapper.selectByExample(example);
        if (null != upmsUsers && upmsUsers.size() > 0) {
            return upmsUsers.get(0);
        }
        return null;
    }
}
