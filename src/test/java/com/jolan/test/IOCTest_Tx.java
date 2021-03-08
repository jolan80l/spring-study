package com.jolan.test;

import com.jolan.aop.MathCalculator;
import com.jolan.config.MainConfigOfAop;
import com.jolan.tx.TxConfig;
import com.jolan.tx.UserService;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

/**
 * @author jolan80
 * @date 2020-08-15 15:16
 */
public class IOCTest_Tx {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.inserUser();
        applicationContext.close();
    }
}
