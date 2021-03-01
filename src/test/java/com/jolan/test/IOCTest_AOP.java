package com.jolan.test;

import com.jolan.aop.MathCalculator;
import com.jolan.bean.Boss;
import com.jolan.bean.Car;
import com.jolan.bean.Color;
import com.jolan.com.jolan.service.BookService;
import com.jolan.config.MainConfigOfAop;
import com.jolan.config.MainConfigOfAutowired;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author jolan80
 * @date 2020-08-15 15:16
 */
public class IOCTest_AOP {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAop.class);

        //1.不要自己创建对象
        MathCalculator mathCalculator = new MathCalculator();
        mathCalculator.div(1, 1);

        MathCalculator mathCalculator1 = applicationContext.getBean(MathCalculator.class);
        mathCalculator1.div(1, 1);
        mathCalculator1.div(1, 0);

        applicationContext.close();
    }
}
