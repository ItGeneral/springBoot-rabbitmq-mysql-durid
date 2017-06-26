package com.songjiuhua.bootmain.mvc.controller;

import com.songjiuhua.bootmain.base.BaseController;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
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
        Subject subject = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(rememberMe);
        try{
            subject.login(token);
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
}
