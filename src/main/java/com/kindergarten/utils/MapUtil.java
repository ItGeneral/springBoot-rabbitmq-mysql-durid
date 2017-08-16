package com.kindergarten.utils;

import org.springframework.beans.BeanWrapper;
import org.springframework.beans.PropertyAccessorFactory;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.Iterator;
import java.util.Map;

/**
 * @Date Created on 2017/8/16.
 * @Author SongJiuHua.
 * @description
 */
public class MapUtil {

    /**
     * 如果javaBean中的属性含有复杂类型（javaBean类型、List、Map），则继续遍历该类型的值，直到属性类型为基础类型为止
     * @param javaBean java对象
     * @param map Map集合
     * @param <T>
     * @Author SongJiuHua
     * @return
     */
    public static <T> T mapToJavaBean(T javaBean, Map map) {
        try {
            Iterator<Map.Entry<String, Object>> it = map.entrySet().iterator();
            while (it.hasNext()) {
                Map.Entry<String, Object> entry = it.next();
                BeanWrapper beanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(javaBean);
                if (entry.getValue() instanceof Map){
                    //获取字段的类型
                    Type type = null;
                    if (javaBean instanceof Class){
                        type = ((Class) javaBean).getDeclaredField(entry.getKey()).getGenericType();
                    }else{
                        type = javaBean.getClass().getDeclaredField(entry.getKey()).getGenericType();
                    }
                    //判断是否含有泛型
                    if(type instanceof ParameterizedType){
                        ParameterizedType pt = (ParameterizedType) type;
                        //获取属性类型
                        Class genericClazz = (Class)pt.getActualTypeArguments()[0];
                        //设置属性的值 key:属性名称 value:属性值
                        beanWrapper.setPropertyValue(entry.getKey(), mapToJavaBean(genericClazz.newInstance(), (Map) entry.getValue()));
                    }else{
                        Map subMap = (Map) entry.getValue();
                        Iterator<Map.Entry<String, Object>> subIt = subMap.entrySet().iterator();
                        Object object = ((Class)type).newInstance();
                        BeanWrapper subBeanWrapper = PropertyAccessorFactory.forBeanPropertyAccess(object);
                        while (subIt.hasNext()){
                            Map.Entry<String, Object> subEntry = subIt.next();
                            if (subEntry.getValue() instanceof Map){
                                //获取属性类型的实例
                                Object subObject = ((Class)object.getClass().getDeclaredField(subEntry.getKey()).getGenericType()).newInstance();
                                subBeanWrapper.setPropertyValue(subEntry.getKey(), mapToJavaBean(subObject, (Map) subEntry.getValue()));
                            }else{
                                subBeanWrapper.setPropertyValue(subEntry.getKey(), subEntry.getValue());
                            }
                        }
                        beanWrapper.setPropertyValue(entry.getKey(), object);
                    }
                }else{
                    beanWrapper.setPropertyValue(entry.getKey(), entry.getValue());
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return javaBean;
    }

}
