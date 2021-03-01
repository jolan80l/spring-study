package com.jolan.test;

import com.jolan.bean.Boss;
import com.jolan.bean.Car;
import com.jolan.bean.Color;
import com.jolan.com.jolan.service.BookService;
import com.jolan.config.MainConfigOfAutowired;
import com.jolan.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @author jolan80
 * @date 2020-08-15 15:16
 */
public class IOCTest_Profile {

    //1.使用命令行动态参数:在虚拟机参数位置增加 -Dspring.profiles.active=test
    //2.代码方式激活环境
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
        String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for(String dataSourceName : namesForType){
            System.out.println(dataSourceName);
        }
        applicationContext.close();
    }

    @Test
    public void test02(){
        //1.创建一个applicationContext对象
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
        //2.设置需要设置的环境
        applicationContext.getEnvironment().setActiveProfiles("test", "dev");
        //3.注册配置类
        applicationContext.register(MainConfigOfProfile.class);
        //4.启动刷新容器
        applicationContext.refresh();
        String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for(String dataSourceName : namesForType){
            System.out.println(dataSourceName);
        }
        applicationContext.close();
    }

}
