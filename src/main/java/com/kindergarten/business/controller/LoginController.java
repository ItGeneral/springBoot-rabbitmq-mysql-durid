package com.kindergarten.business.controller;

import com.kindergarten.bootmain.base.BaseController;
import com.kindergarten.business.model.SysUser;
import com.kindergarten.business.service.SysUserService;
import com.kindergarten.common.ResponseEntity;
import com.kindergarten.utils.MD5Util;
import com.kindergarten.utils.ResultStatus;
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
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
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
    public ResponseEntity login(@RequestParam String userName, @RequestParam String password,
                                @RequestParam(defaultValue = "false") boolean rememberMe){
        ResponseEntity responseEntity = new ResponseEntity();
        Subject currentUser  = SecurityUtils.getSubject();
        UsernamePasswordToken token = new UsernamePasswordToken(userName, MD5Util.getEncryptedString(password));
        token.setRememberMe(rememberMe);
        try{
            currentUser.login(token);
            responseEntity.setMessage("登录成功");
        }catch (UnknownAccountException uae){
            logger.error("用户名不存在", uae);
            responseEntity.setErrorMessage("用户名不存在", ResultStatus.PARAMETER_EXCEPTION.getCode());
        }catch (IncorrectCredentialsException ice){
            logger.error("密码不正确", ice);
            responseEntity.setErrorMessage("密码不正确", ResultStatus.PARAMETER_EXCEPTION.getCode());
        }catch (AuthenticationException ae){
            logger.error("用户名或密码不正确", ae);
            responseEntity.setErrorMessage("用户名或密码不正确", ResultStatus.PARAMETER_EXCEPTION.getCode());
        }
        return responseEntity;
    }

    /**
     * 退出
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "logout")
    public ResponseEntity logout(){
        ResponseEntity responseEntity = new ResponseEntity();
        try{
            SecurityUtils.getSubject().logout();
            responseEntity.setMessage("安全退出");
        }catch (Exception e){
            logger.error("退出异常", e);
            responseEntity.setErrorMessage("退出异常", ResultStatus.INTERNAL_SERVER_ERROR.getCode());
        }
        return responseEntity;
    }

    /**
     * 注册
     * @param sysUser
     * @return
     */
    @ResponseBody
    @RequestMapping(value = "register", method = RequestMethod.POST)
    public ResponseEntity register(@RequestBody SysUser sysUser){
        ResponseEntity responseEntity = new ResponseEntity();
        if (StringUtils.isEmpty(sysUser.getUserName())){
            responseEntity.setErrorMessage("用户名不能为空", ResultStatus.PARAMETER_EXCEPTION.getCode());
            return responseEntity;
        }
        if(StringUtils.isEmpty(sysUser.getPassword())){
            responseEntity.setErrorMessage("密码不能为空", ResultStatus.PARAMETER_EXCEPTION.getCode());
            return responseEntity;
        }
        try{
            sysUser.setPassword(MD5Util.getEncryptedString(sysUser.getPassword()));
            sysUserService.insertUser(sysUser);
            responseEntity.setMessage("注册成功");
        }catch (Exception e){
            logger.error("注册失败", e);
            responseEntity.setErrorMessage("注册失败，请稍后再试", ResultStatus.INTERNAL_SERVER_ERROR.getCode());
        }
        return responseEntity;
    }
}
