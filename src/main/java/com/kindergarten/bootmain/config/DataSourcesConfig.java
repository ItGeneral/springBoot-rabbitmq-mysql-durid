package com.kindergarten.bootmain.config;

import com.alibaba.druid.pool.DruidDataSource;
import com.alibaba.druid.support.http.StatViewServlet;
import com.alibaba.druid.support.http.WebStatFilter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.embedded.FilterRegistrationBean;
import org.springframework.boot.context.embedded.ServletRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.context.annotation.PropertySource;

/**
 * Created on 2016/10/31.
 * @Author SongJiuHua.
 * @description druid数据源和监控配置
 */
@Configuration
@PropertySource(value = {"classpath:druid-jdbc.properties", "classpath:druid-monitor.properties"})
public class DataSourcesConfig {

    @Value("${druid.jdbc.driverClassName}")
    private String driverClassName;

    @Value("${druid.jdbc.url}")
    private String url;

    @Value("${druid.jdbc.username}")
    private String userName;

    @Value("${druid.jdbc.password}")
    private String password;

    @Value("${druid.jdbc.maxActive}")
    private Integer maxActive;

    @Value("${druid.jdbc.maxWait}")
    private Integer maxWait;

    @Value("${druid.jdbc.initSize}")
    private Integer initSize;

    @Value("${druid.monitor.urlMapping}")
    private String urlMapping;

    @Value("${druid.monitor.allowIp}")
    private String allowIp;

    @Value("${druid.monitor.userName}")
    private String monitorUserName;

    @Value("${druid.monitor.password}")
    private String monitorPassword;

    @Value("${druid.monitor.UrlPatterns}")
    private String urlPatterns;

    @Value("${druid.monitor.exclusions}")
    private String exclusions;

    /**
     * 配置数据源  @Primary 表示如果是多数据源，则默认使用该数据源配置
     * @return
     */
    @Primary
    @Bean(name = "dataSource", destroyMethod = "close")
    public DruidDataSource dataSource(){
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName(driverClassName);
        dataSource.setUrl(url);
        dataSource.setUsername(userName);
        dataSource.setPassword(password);
        dataSource.setMaxActive(maxActive);
        dataSource.setMaxWait(maxWait);
        dataSource.setInitialSize(initSize);
        return dataSource;
    }


    /**
     * druid监控配置 访问地址：http://localhost:8080/druid/index.html
     * @return
     */
    @Bean
    public ServletRegistrationBean druidServlet(){
        ServletRegistrationBean servlet = new ServletRegistrationBean();
        servlet.setServlet(new StatViewServlet());
        servlet.addUrlMappings(urlMapping);
        servlet.addInitParameter("allow", allowIp);
        servlet.addInitParameter("loginUsername", monitorUserName);
        servlet.addInitParameter("loginPassword", monitorPassword);
        return servlet;
    }


    /**
     * druid 监控过滤器
     * @return
     */
    @Bean
    public FilterRegistrationBean druidFilter(){
        FilterRegistrationBean filter = new FilterRegistrationBean();
        filter.setFilter(new WebStatFilter());
        filter.addUrlPatterns(urlPatterns);
        filter.addInitParameter("exclusions", exclusions);
        return filter;
    }

}
