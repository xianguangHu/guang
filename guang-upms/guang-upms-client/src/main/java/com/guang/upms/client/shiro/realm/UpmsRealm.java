package com.guang.upms.client.shiro.realm;

import com.guang.common.utils.MD5Util;
import com.guang.upms.dao.model.UpmsUser;
import com.guang.upms.rpc.api.UpmsApiService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * @author huxianguang
 * @create 2017-11-02-上午9:04
 **/
public class UpmsRealm extends AuthorizingRealm{


    private static Logger _log = LoggerFactory.getLogger(UpmsRealm.class);

    @Autowired
    private UpmsApiService upmsApiService;


    /**
     * 认证
     * @param token
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
        String username = (String) token.getPrincipal();
        String password = new String((char[]) token.getCredentials());
        UpmsUser user = upmsApiService.selectUpmsUserByUsername(username);
        if (null == user) {
            throw new UnknownAccountException();
        }
        String md5pwd = MD5Util.MD5(password+user.getSalt());
        System.out.println(md5pwd);
        if (!user.getPassword().equals(md5pwd)) {
            //密码错误
            throw new IncorrectCredentialsException();
        }
        //状态 1 锁定 0 正常
        if (user.getLocked() == 1 ) {
            throw new LockedAccountException();
        }
        return new SimpleAuthenticationInfo(username,password,getName());
    }


    /**
     * 授权
     * @param principals
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        return null;
    }


}
