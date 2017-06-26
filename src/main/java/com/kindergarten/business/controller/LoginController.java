package com.kindergarten.business.controller;

import com.kindergarten.bootmain.base.BaseController;
import com.kindergarten.business.model.SysUser;
import com.kindergarten.business.service.SysUserService;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * @Date Created on 2017/6/26.
 * @Author SongJiuHua.
 * @description
 */
@Controller
@RequestMapping(value = "")
public class LoginController extends BaseController{

    @Autowired
    private SysUserService sysUserService;

    /**
     * 登录
     * @param userName
     * @param password
     * @param rememberMe
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "login")
    public ResponseEntity login(@RequestParam String userName, @RequestParam String password,
           @RequestParam(defaultValue = "false") boolean rememberMe){
        Subject currentUser  = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(rememberMe);
        try{
            currentUser.login(token);
        }catch (UnknownAccountException uae){
            return badRequest(HttpStatus.BAD_REQUEST).put("message", "用户名不存在").build();
        }catch (IncorrectCredentialsException ice){
            return badRequest(HttpStatus.BAD_REQUEST).put("message", "密码不正确").build();
        }catch (AuthenticationException ae){
            return badRequest(HttpStatus.BAD_REQUEST).put("message", "用户名或密码不正确").build();
        }
        return ok().put("message", "登录成功").build();
    }

    /**
     * 退出
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "logout")
    public ResponseEntity logout(){
        SecurityUtils.getSubject().logout();
        return ok().put("message", "安全退出").build();
    }

    /**
     * 注册
     * @param sysUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "register")
    public ResponseEntity register(SysUser sysUser){
        if (StringUtils.isEmpty(sysUser.getUserName())){
            return badRequest(HttpStatus.BAD_REQUEST).put("message", "用户名不能为空").build();
        }
        if(StringUtils.isEmpty(sysUser.getPassword())){
            return badRequest(HttpStatus.BAD_REQUEST).put("message", "密码不能为空").build();
        }
        try{
            sysUserService.insertUser(sysUser);
        }catch (Exception e){
            return badRequest(HttpStatus.BAD_REQUEST).put("message", "程序异常，请稍后再试").build();
        }
        return ok().put("message", "注册成功").build();
    }
}
