package com.songjiuhua.bootmain.interceptor;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Created on 2016/10/20.
 * @Author SongJiuHua.
 * @description 定义自己的拦截器
 */
public class MyInterceptor extends HandlerInterceptorAdapter {


    /**
     * 拦截器预处理
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //登录拦截、性能监控，只有返回true时，才会执行下面的postHandle方法
        System.out.println("--------preHandle--------");
        return true;
    }


    /**
     * 拦截器后处理器
     * @param request
     * @param response
     * @param handler
     * @param modelAndView
     * @throws Exception
     */
    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        System.out.println("--------postHandle--------");
    }

    /**
     * 拦截器处理完后的回调
     * @param request
     * @param response
     * @param handler
     * @param ex
     * @throws Exception
     */
    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        System.out.println("--------afterCompletion--------");
    }

}
