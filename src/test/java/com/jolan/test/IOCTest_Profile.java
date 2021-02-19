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

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
        String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for(String dataSourceName : namesForType){
            System.out.println(dataSourceName);
        }
        applicationContext.close();
    }

}
