package com.jolan.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.config.BeanFactoryPostProcessor;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.stereotype.Component;

@Component
public class MyJolanBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

    public MyJolanBeanFactoryPostProcessor() {
        super();
        System.out.println("BeanFactoryPostProcessor实现类的构造器。。。");
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory arg0)
            throws BeansException {
        System.out.println("调用BeanFactoryPostProcessor的postProcessBeanFactory方法。。。");
        BeanDefinition bd = arg0.getBeanDefinition("person");
        bd.getPropertyValues().addPropertyValue("age", 20);
    }

}