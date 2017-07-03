package com.kindergarten.bootmain.base;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.proxy.Enhancer;
import org.springframework.cglib.proxy.MethodInterceptor;
import org.springframework.cglib.proxy.MethodProxy;
import org.springframework.stereotype.Service;

import java.lang.reflect.Method;
import java.lang.reflect.ParameterizedType;

/**
 * Created on 2016/11/4.
 * @Author SongJiuHua.
 * @description
 */
@Service
public abstract class BaseService<T> {

    @Autowired
    private BaseDao baseDao;

    protected BaseDao dao;

    private String namespace;

    public BaseService() {
        // 找到直接继承BaseService的类获取泛型
        Class targetClazz = getClass();
        while (true) {
            if (targetClazz.getSuperclass().equals(BaseService.class)) {
                break;
            }
            targetClazz = targetClazz.getSuperclass();
        }

        Class<T> clazz = (Class<T>) ((ParameterizedType) targetClazz.getGenericSuperclass()).getActualTypeArguments()[0];
        namespace = clazz.getSimpleName();

        // 动态代理dao，使用dao不在需要sqlId
        Enhancer e = new Enhancer();
        e.setSuperclass(BaseDao.class);
        e.setCallback(new MethodInterceptor() {
            @Override
            public Object intercept(Object obj, Method method, Object[] args,
                                    MethodProxy proxy) throws Throwable {
                Object retValFromSuper = null;
                try {
                    args[0] = sqlId((String) args[0]);
                    retValFromSuper = method.invoke(baseDao, args);
                } catch (Throwable t) {
                    throw t.fillInStackTrace();
                }
                return retValFromSuper;
            }
        });
        dao = (BaseDao) e.create();
    }

    private String sqlId(String sqlId) {
        return new StringBuffer(namespace).append(".").append(sqlId).toString();
    }

}

