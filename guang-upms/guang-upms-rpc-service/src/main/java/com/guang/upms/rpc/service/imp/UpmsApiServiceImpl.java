package com.guang.upms.rpc.service.imp;

import com.guang.upms.dao.mapper.UpmsUserMapper;
import com.guang.upms.dao.model.UpmsPermission;
import com.guang.upms.dao.model.UpmsUser;
import com.guang.upms.dao.model.UpmsUserExample;
import com.guang.upms.rpc.api.UpmsApiService;

import com.guang.upms.rpc.mapper.UpmsApiMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private static Logger _log = LoggerFactory.getLogger(UpmsApiServiceImpl.class);

    @Autowired
    private UpmsUserMapper upmsUserMapper;

    @Autowired
    private UpmsApiMapper upmsApiMapper;
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


    /**
     * 根据用户id获取所拥有的权限
     * @param upmsUserId
     * @return
     */
    @Override
    public List<UpmsPermission> selectUpmsPermissionByUpmsUserId(Integer upmsUserId) {
        //查询用户
        UpmsUser user = upmsUserMapper.selectByPrimaryKey(upmsUserId);
        //判断用户是否存在或锁定
        if (null == user || 1 == user.getLocked()) {
            _log.info("selectUpmsPermissionByUpmsUserId : upmsUserId={}",upmsUserId);
            return null;
        }
        //获取权限 role权限联合upmsUser权限
        List<UpmsPermission> upmsPermissions = upmsApiMapper.selectUpmsPermissionByUpmsUserId(user.getUserId());
        return upmsPermissions;
    }
}
