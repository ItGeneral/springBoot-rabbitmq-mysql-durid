package com.kindergarten.business.service;

import com.kindergarten.bootmain.base.BaseService;
import com.kindergarten.business.model.SysRole;
import com.kindergarten.common.BaseQueryEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
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
        SysRole sysRole  = dao.selectOne("queryByRoleEnName", roleEnName);
        return sysRole;
    }

    public List<SysRole> queryAllRoles(){
        BaseQueryEntity<SysRole> baseQueryEntity = new BaseQueryEntity<>(new SysRole());
        List<SysRole> sysRoleList = dao.selectList("queryAllUser", baseQueryEntity);
        return sysRoleList;
    }

    public void insertSysRole(SysRole sysRole){
        dao.insert("insert", sysRole);
    }

}
