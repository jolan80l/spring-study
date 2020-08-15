package com.jolan.test;

import com.jolan.bean.Person;
import com.jolan.com.jolan.dao.BookDao;
import com.jolan.com.jolan.service.BookService;
import com.jolan.config.MainConfigOfAutowired;
import com.jolan.config.MainConfigOfPropertyValues;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

/**
 * @author jolan80
 * @date 2020-08-15 15:16
 */
public class IOCTest_Autowired {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
    @Test
    public void test01(){
        BookService bookService = (BookService)applicationContext.getBean(BookService.class);
        bookService.print();

//        BookDao bookDao = (BookDao)applicationContext.getBean(BookDao.class);
//        System.out.println(bookDao);
    }

    private void printBeans(AnnotationConfigApplicationContext applicationContext){
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
