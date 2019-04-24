package com.winshare.edu.modules.frame.realm;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import com.winshare.edu.modules.system.entity.AccountInfo;
import com.winshare.edu.modules.system.service.AccountInfoService;

public class UserRealm extends AuthorizingRealm {

    @Autowired
    private AccountInfoService accountInfoService;

    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
    	AccountInfo accountInfo = (AccountInfo) SecurityUtils.getSubject().getSession().getAttribute("WINSHARE_USER");
        SimpleAuthorizationInfo authorizationInfo = new SimpleAuthorizationInfo();
        authorizationInfo.setRoles(accountInfoService.findRoleAuthByAccountId(accountInfo.getId()));
        authorizationInfo.setStringPermissions(accountInfoService.findPermissionsByAccountId(accountInfo.getId()));
        return authorizationInfo;
    }

    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {

        String loginName = (String)token.getPrincipal();

        AccountInfo accountInfo = accountInfoService.findByLoginName(loginName);

        if(accountInfo == null) {
            throw new UnknownAccountException();//没找到帐号
        }

        if(accountInfo.isDisabled()) {
            throw new DisabledAccountException(); //帐号禁用
        }

        String account = "";
        if(loginName.equals(accountInfo.getAccount())){
        	account = accountInfo.getAccount();
        }else{
        	account = accountInfo.getLoginName();
        }
         
        SimpleAuthenticationInfo authenticationInfo = new SimpleAuthenticationInfo(
        		account, //用户名
                accountInfo.getPassword(), //密码
                ByteSource.Util.bytes(accountInfo.getAccount()),//salt=account+salt
                getName()  //realm name
        );
        return authenticationInfo;
    }

    @Override
    public void clearCachedAuthorizationInfo(PrincipalCollection principals) {
        super.clearCachedAuthorizationInfo(principals);
    }

    @Override
    public void clearCachedAuthenticationInfo(PrincipalCollection principals) {
        super.clearCachedAuthenticationInfo(principals);
    }

    @Override
    public void clearCache(PrincipalCollection principals) {
        super.clearCache(principals);
    }

    public void clearAllCachedAuthorizationInfo() {
        getAuthorizationCache().clear();
    }

    public void clearAllCachedAuthenticationInfo() {
        getAuthenticationCache().clear();
    }

    public void clearAllCache() {
        clearAllCachedAuthenticationInfo();
        clearAllCachedAuthorizationInfo();
    }

}
