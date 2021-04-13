package com.jolan.ext;

import com.jolan.bean.Blue;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.*;
import org.springframework.stereotype.Component;

@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    //BeanDefinitionRegistry Bean定义信息的保存中心，以后的BeanFactory就是按照BeanDefinitionRegistry里面保存的每一个ban定义信息创建bean实例的。
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor...postProcessBeanDefinitionRegistry...bean的数量:" + beanDefinitionRegistry.getBeanDefinitionCount());
        //下面两种方法都可以创建一个bean定义信息
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionBuilder.rootBeanDefinition(Blue.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("hello", beanDefinition);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor...bean的数量:" + configurableListableBeanFactory.getBeanDefinitionCount());
    }
}
