package com.jolan.test;

import com.jolan.config.MainConfigLifeCycle;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author jolan80
 * @date 2020-08-11 20:39
 */
public class IOCTest_LifeCycle {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);
        System.out.println("容器创建完成...");

        applicationContext.getBean("car");
        //关闭容器
        applicationContext.close();
    }
}
