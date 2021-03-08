package com.jolan.tx;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;

import javax.sql.DataSource;

/**
 * @author jolan80
 * @date 2021-03-08 21:25
 *
 * 环境搭建：
 *  1、导入相关以来
 *      数据源、数据库驱动、springjdbc模块
 *  2、配置数据源、JdbcTemplate（Spring提供简化数据库操作工具）操作数据库
 */
@ComponentScan("com.jolan.tx")
@Configuration
public class TxConfig {

    //数据源
    @Bean
    public DataSource dataSource() throws Exception{
        ComboPooledDataSource datasource = new ComboPooledDataSource();
        datasource.setUser("root");
        datasource.setPassword("123456");
        datasource.setDriverClass("com.mysql.jdbc.Driver");
        datasource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        return datasource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception{
        //Spring对@Configuration类会特殊处理：给容器中加组件的方法，多次调用都只是从容器中找组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }
}
