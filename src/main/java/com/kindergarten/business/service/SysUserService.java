package com.kindergarten.business.service;

import com.kindergarten.bootmain.base.BaseService;
import com.kindergarten.business.model.SysUser;
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
public class SysUserService extends BaseService<SysUser> {

    private final static Logger logger = LoggerFactory.getLogger(SysUserService.class);

    /**
     *  根据用户名查询用户
     * @param userName
     * @return
     */
    public SysUser getByUserName(String userName){
        SysUser sysUser  = dao.selectOne("queryByUserName", userName);
        return sysUser;
    }

    /**
     * 查询所有用户信息
     * @return
     */
    public List<SysUser> queryAllUser(BaseQueryEntity<SysUser> baseQueryEntity){
        List<SysUser> sysUserList = dao.selectList("queryAllUser", baseQueryEntity);
        return sysUserList;
    }

    /**
     * 插入用户信息
     * @param sysUser
     */
    public void insertUser(SysUser sysUser){
        dao.insert("insert", sysUser);
    }

}
