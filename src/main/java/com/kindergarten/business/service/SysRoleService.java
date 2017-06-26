package com.kindergarten.business.service;

import com.kindergarten.bootmain.base.BaseService;
import com.kindergarten.business.model.SysRole;
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
public class SysRoleService extends BaseService<SysRole> {

    private final static Logger logger = LoggerFactory.getLogger(SysRoleService.class);

    public SysRole queryRoleByRoleEnName(String roleEnName){
        SysRole sysRole = null;
        try{
            sysRole = selectOne("queryByRoleEnName", roleEnName);
        }catch (Exception e){
            logger.error("根据角色名查询角色异常", e);
        }
        return sysRole;
    }

    public List<SysRole> queryAllRoles(){
        List<SysRole> sysRoleList = new ArrayList<>();
        try{
            BaseQueryDto<SysRole> baseQueryDto = new BaseQueryDto<>(new SysRole());
            sysRoleList = (List<SysRole>) selectList("queryAllUser", baseQueryDto);
        }catch (Exception e){
            logger.error("查询所有角色信息异常", e);
        }
        return sysRoleList;
    }

    public void insertSysRole(SysRole sysRole){
        try{
            insert("insert", sysRole);
        }catch (Exception e){
            logger.error("角色信息入库异常", e);
        }
    }

}
