package com.kindergarten.business.service;

import com.kindergarten.bootmain.base.BaseService;
import com.kindergarten.business.model.SysUser;
import com.kindergarten.common.BaseQueryDto;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

/**
 * @Date Created on 2017/6/26.
 * @Author SongJiuHua.
 * @description
 */
@Service
public class SysUserService extends BaseService<SysUser> {

    private final static Logger logger = LoggerFactory.getLogger(SysUserService.class);

    /**
     *  根据用户名查询用户
     * @param userName
     * @return
     */
    public SysUser getByUserName(String userName){
        SysUser sysUser = null;
        try{
            sysUser = selectOne("queryByUserName", userName);
        }catch (Exception e){
            logger.error("根据用户名查询用户异常", e);
        }
        return sysUser;
    }

    /**
     * 查询所有用户信息
     * @return
     */
    public List<SysUser> queryAllUser(){
        List<SysUser> sysUserList = new ArrayList<>();
        try{
            BaseQueryDto<SysUser> baseQueryDto = new BaseQueryDto<>(new SysUser());
            sysUserList = (List<SysUser>) selectList("queryAllUser", baseQueryDto);
        }catch (Exception e){
            logger.error("查询所有用户信息异常", e);
        }
        return sysUserList;
    }

    /**
     * 插入用户信息
     * @param sysUser
     */
    public void insertUser(SysUser sysUser){
        try{
            insert("insert", sysUser);
        }catch (Exception e){
            logger.error("用户信息入库异常", e);
        }
    }

}
