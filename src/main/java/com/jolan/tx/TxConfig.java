package com.jolan.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;

/**
 * @author jolan80
 * @date 2021-03-08 21:25
 *
 * 环境搭建：
 *  1、导入相关依赖
 *      数据源、数据库驱动、springjdbc模块
 *  2、配置数据源、JdbcTemplate（Spring提供简化数据库操作工具）操作数据库
 *  3、给方法上标注@Transcational表示当前方法是一个事物方法
 *  4、@EnableTransactionManagement开启基于注解的事物管理功能
 *  5、配置事物管理器管理事物
 */
@EnableTransactionManagement
@ComponentScan("com.jolan.tx")
@Configuration
public class TxConfig {

    //数据源
    @Bean
    public DataSource dataSource() throws Exception{
        ComboPooledDataSource datasource = new ComboPooledDataSource();
        datasource.setUser("111");
        datasource.setPassword("111");
        datasource.setDriverClass("com.mysql.jdbc.Driver");
        datasource.setJdbcUrl("jdbc:mysql://localhost:3358/test");
        return datasource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception{
        //Spring对@Configuration类会特殊处理：给容器中加组件的方法，多次调用都只是从容器中找组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager() throws Exception{
        return new DataSourceTransactionManager(dataSource());
    }
}
