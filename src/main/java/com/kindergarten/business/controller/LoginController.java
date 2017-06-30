package com.kindergarten.business.controller;

import com.kindergarten.bootmain.base.BaseController;
import com.kindergarten.business.model.SysUser;
import com.kindergarten.business.service.SysUserService;
import com.kindergarten.common.ResponseDto;
import com.kindergarten.utils.MD5Util;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.IncorrectCredentialsException;
import org.apache.shiro.authc.UnknownAccountException;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory.getLogger(LoginController.class);

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
    public ResponseDto login(@RequestParam String userName, @RequestParam String password,
           @RequestParam(defaultValue = "false") boolean rememberMe){
        ResponseDto responseDto = new ResponseDto();
        Subject currentUser  = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, password);
        token.setRememberMe(rememberMe);
        try{
            currentUser.login(token);
            responseDto.setMessage("登录成功");
        }catch (UnknownAccountException uae){
            responseDto.setErrorMessage("用户名不存在");
        }catch (IncorrectCredentialsException ice){
            responseDto.setErrorMessage("密码不正确");
        }catch (AuthenticationException ae){
            responseDto.setErrorMessage("用户名或密码不正确");
        }
        return responseDto;
    }

    /**
     * 退出
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "logout")
    public ResponseDto logout(){
        ResponseDto responseDto = new ResponseDto();
        try{
            SecurityUtils.getSubject().logout();
            responseDto.setCode(HttpStatus.OK);
            responseDto.setMessage("安全退出");
        }catch (Exception e){
            logger.error("退出异常");
            responseDto.setErrorMessage("退出异常");
        }
        return responseDto;
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
