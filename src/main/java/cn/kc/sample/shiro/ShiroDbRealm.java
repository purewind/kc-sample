package cn.kc.sample.shiro;

import cn.kc.platform.User;
import cn.kc.platform.account.entity.Member;
import cn.kc.platform.account.service.AccountService;
import org.apache.shiro.authc.*;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;

/**
 * Created by dhm on 14-12-13.
 */
public class ShiroDbRealm extends AuthorizingRealm {


    protected AccountService accountService;

    /**
     * 认证回调函数,登录时调用.
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authcToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authcToken;
        Member member = accountService.findUserByLoginName(token.getUsername());
        if (member != null) {
//            if ("disabled".equals(member.getStatus())) {
//                throw new DisabledAccountException();
//            }

            return new SimpleAuthenticationInfo(new User(member.getEmail(), member.getEmail()), member.getPassword(),
                    getName());
        } else {
            return null;
        }
    }

    /**
     * 授权查询回调函数, 进行鉴权但缓存中无用户的授权信息时调用.
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
        User shiroUser = (User) principals.getPrimaryPrincipal();
        Member member = accountService.findUserByLoginName(shiroUser.loginName);

        SimpleAuthorizationInfo info = new SimpleAuthorizationInfo();
//        for (Role role : member.getRoleList()) {
//            // 基于Role的权限信息
//            info.addRole(role.getName());
//            // 基于Permission的权限信息
//            info.addStringPermissions(role.getPermissionList());
//        }
        return info;
    }


    public void setAccountService(AccountService accountService) {
        this.accountService = accountService;
    }

}

