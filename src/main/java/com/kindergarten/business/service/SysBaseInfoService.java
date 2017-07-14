package com.kindergarten.business.service;

import com.kindergarten.bootmain.base.BaseService;
import com.kindergarten.business.model.SysBaseInfo;
import com.kindergarten.common.BaseQueryEntity;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @Date Created on 2017/7/14.
 * @Author SongJiuHua.
 * @description
 */
@Service
public class SysBaseInfoService extends BaseService<SysBaseInfo> {

    public void insert(SysBaseInfo sysBaseInfo){
        this.dao.insert("insert", sysBaseInfo);
    }

    public void updateById(SysBaseInfo sysBaseInfo){
        this.dao.update("updateById", sysBaseInfo);
    }

    public List<SysBaseInfo> querySysBaseInfo(SysBaseInfo sysBaseInfo){
        BaseQueryEntity<SysBaseInfo> baseQueryEntity = new BaseQueryEntity<>(sysBaseInfo);
        return this.dao.selectList("query", baseQueryEntity);
    }

}
