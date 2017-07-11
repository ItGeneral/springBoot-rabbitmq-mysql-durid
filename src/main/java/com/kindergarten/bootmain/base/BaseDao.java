package com.kindergarten.bootmain.base;

import org.mybatis.spring.support.SqlSessionDaoSupport;

import java.util.List;

/**
 * Created on 2016/11/4.
 * @Author SongJiuHua.
 * @description
 */
public class BaseDao extends SqlSessionDaoSupport {

    /**
     * 插入
     * @param sqlId
     * @param param 可以传入model，map
     * @return
     */
    public int insert(String sqlId, Object param) {
        return getSqlSession().insert(sqlId, param);
    }

    /**
     * 删除
     * @param sqlId
     * @param param 可以传入model，map，int，string
     * @return
     */
    public int delete(String sqlId, Object param) {
        return getSqlSession().delete(sqlId, param);
    }

    /**
     * 更新
     * @param sqlId
     * @param param 可以传入model，map
     * @return
     */
    public int update(String sqlId, Object param) {
        return getSqlSession().update(sqlId, param);
    }

    /**
     * 更新
     * @param sqlId
     * @return
     */
    public int update(String sqlId) {
        return update(sqlId, null);
    }

    /**
     * 查询列表
     * @param sqlId
     * @param param 可以传入model，map，int，string
     * @param <T>
     * @return
     */
    public <T> List<T> selectList(String sqlId, Object param) {
        return getSqlSession().selectList(sqlId, param);
    }

    /**
     * 查询列表
     * @param sqlId
     * @param <T>
     * @return
     */
    public <T> List<T> selectList(String sqlId) {
        return getSqlSession().selectList(sqlId, null);
    }

    /**
     * 查询单条记录
     * @param sqlId
     * @param param 可以传入model，map，int，string
     * @param <T>
     * @return
     */
    public <T> T selectOne(String sqlId, Object param) {
        return (T)getSqlSession().selectOne(sqlId, param);
    }

    /**
     * 查询记录数
     * @param sqlId
     * @param param 可以传入model，map，int，string
     * @return
     */
    public int count(String sqlId, Object param) {
        return (int)getSqlSession().selectOne(sqlId, param);
    }

    /**
     * 删除 不带参数
     * @param sqlId
     */
    public void delete(String sqlId) {
        delete(sqlId, null);
    }
}
