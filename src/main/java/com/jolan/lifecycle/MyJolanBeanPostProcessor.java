package com.jolan.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

@Component
public class MyJolanBeanPostProcessor implements BeanPostProcessor {

    public MyJolanBeanPostProcessor() {
        super();
        System.out.println("BeanPostProcessor实现类的构造器。。。");
    }

    public Object postProcessAfterInitialization(Object arg0, String arg1) throws BeansException {
        System.out.println("调用BeanPostProcessor的postProcessAfterInitialization方法对属性进行更改。。。");
        return arg0;
    }

    public Object postProcessBeforeInitialization(Object arg0, String arg1) throws BeansException {
        System.out.println("调用BeanPostProcessor接口的方法postProcessBeforeInitialization对属性进行更改。。。");
        return arg0;
    }
}