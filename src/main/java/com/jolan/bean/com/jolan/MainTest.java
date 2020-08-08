package com.jolan.bean.com.jolan;

import com.jolan.bean.Person;
import com.jolan.config.MainConfig;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * @author jolan80
 * @date 2020-08-08 15:19
 */
public class MainTest {
    public static void main(String[] args) {
//        ApplicationContext applcationContext = new ClassPathXmlApplicationContext("beans.xml");
//        Person bean = (Person)applcationContext.getBean("person");
//        System.out.println(bean);

        ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        Person bean = (Person)applicationContext.getBean(Person.class);
        System.out.println(bean);
        String[] names = applicationContext.getBeanNamesForType(Person.class);
        for (String name : names) {
            System.out.println(name);
        }
    }
}
