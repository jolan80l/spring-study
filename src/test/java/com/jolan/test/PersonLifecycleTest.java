package com.jolan.test;


import com.jolan.config.MainPersonLifeCycle;
import com.jolan.lifecycle.Person;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;


public class PersonLifecycleTest {
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainPersonLifeCycle.class);


        Person person = (Person)applicationContext.getBean("person");
        System.out.println(person);
        //关闭容器
        applicationContext.close();
    }
}