package com.jolan.test;

import com.jolan.bean.Person;
import com.jolan.config.MainConfig;
import com.jolan.config.MainConfig2;
import com.jolan.ext.ExtConfig;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import java.util.Map;

/**
 * @author jolan80
 * @date 2020-08-08 15:42
 */
public class IOCTest_Ext {

    @SuppressWarnings("resource")
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

        applicationContext.close();
    }


}
