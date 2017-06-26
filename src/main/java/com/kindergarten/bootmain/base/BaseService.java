package com.kindergarten.bootmain.base;

import com.kindergarten.business.model.SysUser;
import com.kindergarten.common.BaseQueryDto;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * Created on 2016/11/4.
 * @Author SongJiuHua.
 * @description
 */
@Service
public class BaseService<T> {

    @Autowired
    private SqlSessionTemplate sqlSessionTemplate;

    /**
     * 新增
     * @param statementId
     * @param params
     * @return
     */
    public int insert(String statementId, Object params){
        return sqlSessionTemplate.insert(statementId, params);
    }

    /**
     * 删除
     * @param statementId
     * @param params
     * @return
     */
    public int delete(String statementId, Object params){
        return sqlSessionTemplate.delete(statementId, params);
    }

    /**
     * 更新
     * @param statementId
     * @param params
     * @return
     */
    public int update(String statementId, Object params){
        return sqlSessionTemplate.update(statementId, params);
    }

    /**
     * 查询单条数据
     * @param statementId
     * @param params
     * @return
     */
    public T selectOne(String statementId, Object params){
        return sqlSessionTemplate.selectOne(statementId, params);
    }

    /**
     * 查询多条数据
     * @param statementId
     * @param params
     * @return
     */
    public Object selectList(String statementId, BaseQueryDto<T> params){
        return sqlSessionTemplate.selectList(statementId, params);
    }

}

