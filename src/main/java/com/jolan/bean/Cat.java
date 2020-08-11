package com.jolan.bean;

import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.stereotype.Component;

/**
 * @author jolan80
 * @date 2020-08-11 20:59
 */
@Component
public class Cat implements InitializingBean, DisposableBean {
    public Cat(){
        System.out.println("cat constructor...");
    }

    public void destroy() throws Exception {
        System.out.println("cat ...destroy...");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("cat ...afterPropertiesSet...");
    }
}
