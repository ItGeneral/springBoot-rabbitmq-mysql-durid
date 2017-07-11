package com.kindergarten.bootmain.config;

import com.kindergarten.bootmain.base.BaseDao;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.core.io.support.ResourcePatternResolver;
import org.springframework.http.converter.ByteArrayHttpMessageConverter;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.annotation.Resource;
import javax.sql.DataSource;

/**
 * Created on 2016/10/31.
 * @Author SongJiuHua.
 * @description mybatis配置类
 */
@Configuration
@EnableTransactionManagement //开启对注解式事物的支持
public class MybatisConfig implements TransactionManagementConfigurer{

    @Resource(name = "dataSource")
    DataSource dataSource;

    /**
     *  配置sqlSessionFactory
     * @return
     */
    @Bean(name = "sqlSessionFactory")
    public SqlSessionFactory sqlSessionFactory(){
        SqlSessionFactoryBean sqlFactory = new SqlSessionFactoryBean();
        sqlFactory.setDataSource(dataSource);
        ResourcePatternResolver resolver = new PathMatchingResourcePatternResolver();
        try {
            //设置xml扫描路径
            sqlFactory.setMapperLocations(resolver.getResources("classpath:mapper/*.xml"));
            return sqlFactory.getObject();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 用于实际查询的sql工具
     * @param sqlSessionFactory
     * @return
     */
    @Bean(name = "sqlSessionTemplate")
    public SqlSessionTemplate sqlSessionTemplate(SqlSessionFactory sqlSessionFactory){
        return new SqlSessionTemplate(sqlSessionFactory);
    }


    /**
     * 事务管理,具体使用在service层加入@Transactional注解
     * @return
     */
    @Override
    @Bean(name = "transactionManager")
    public PlatformTransactionManager annotationDrivenTransactionManager() {
        return new DataSourceTransactionManager(dataSource);
    }

    @Bean(name = "baseDao")
    public BaseDao baseDaoBean() {
        BaseDao baseDao = new BaseDao();
        baseDao.setSqlSessionFactory(sqlSessionFactory());
        return baseDao;
    }

    @Bean
    public ByteArrayHttpMessageConverter byteArrayHttpMessageConverter() {
        return new ByteArrayHttpMessageConverter();
    }
}
