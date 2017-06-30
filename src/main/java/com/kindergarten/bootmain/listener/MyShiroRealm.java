package com.kindergarten.bootmain.listener;

import com.kindergarten.business.model.SysUser;
import com.kindergarten.business.service.SysUserService;
import com.kindergarten.utils.MD5Util;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.session.Session;
import org.apache.shiro.subject.PrincipalCollection;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * @Date Created on 2017/6/26.
 * @Author SongJiuHua.
 * @description
 */
public class MyShiroRealm extends AuthorizingRealm {

    @Autowired
    private SysUserService sysUserService;

    /**
     * 权限认证，为当前登录的Subject授予角色和权限
     * 本例中该方法的调用时机为需授权资源被访问时
     * 并且每次访问需授权资源时都会执行该方法中的逻辑，这表明本例中默认并未启用AuthorizationCache
     * 如果连续访问同一个URL（比如刷新），该方法不会被重复调用，Shiro有一个时间间隔（也就是cache时间，在ehcache-shiro.xml中配置），超过这个时间间隔再刷新页面，该方法会被执行
     * @param principalCollection
     * @return
     */
    @Override
    protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principalCollection) {
        //获取当前登录用户名
        String loginName = (String) super.getAvailablePrincipal(principalCollection);
        // 实际项目中，这里可以根据实际情况做缓存，如果不做，Shiro自己也是有时间间隔机制，2分钟内不会重复执行该方法
        SysUser sysUser = sysUserService.getByUserName(loginName);
        //把principals放session中 key=userId value=principals
        SecurityUtils.getSubject().getSession().setAttribute(sysUser.getUserName(),SecurityUtils.getSubject().getPrincipals());
        if (sysUser != null){
            //权限信息对象info,用来存放查出的用户的所有的角色（role）及权限（permission）
            SimpleAuthorizationInfo simpleAuthorizationInfo = new SimpleAuthorizationInfo();
            //查询当前用户的角色
            Set<String> roles = new HashSet<>();
            simpleAuthorizationInfo.setRoles(roles);
            //查询当前用户所有的权限url
            List<String> permissionList = new ArrayList<>();
            for (String permission : permissionList){
                simpleAuthorizationInfo.addStringPermission(permission);
            }
            return simpleAuthorizationInfo;
        }
        return null;
    }

    /**
     * 登录认证
     * @param authenticationToken
     * @return
     * @throws AuthenticationException
     */
    @Override
    protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken authenticationToken) throws AuthenticationException {
        UsernamePasswordToken token = (UsernamePasswordToken) authenticationToken;
        //查询数据库中是否有此用户
        SysUser sysUser = sysUserService.getByUserName(token.getUsername());
        if(sysUser !=null){
            // 若存在，将此用户存放到登录认证info中，无需自己做密码对比，Shiro会为我们进行密码对比校验
            //设置用户session
            Session session = SecurityUtils.getSubject().getSession();
            session.setAttribute("sysUser", sysUser);
            return new SimpleAuthenticationInfo(sysUser.getUserName(), sysUser.getPassword().toCharArray(), getName());
        }
        return null;
    }
}
