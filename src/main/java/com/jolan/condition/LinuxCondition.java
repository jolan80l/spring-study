package com.jolan.condition;

import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.context.annotation.Conditional;
import org.springframework.core.env.Environment;
import org.springframework.core.type.AnnotatedTypeMetadata;

import java.lang.annotation.Annotation;

/**
 * @author jolan80
 * @date 2020-08-09 9:23
 */
public class LinuxCondition implements Condition {
    /**
     * ConditionContext : 判断环境能使用的上下文（环境）
     * AnnotatedTypeMetadata : 注解信息
     */
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //是否为linux系统
        //1、能获取到ioc使用的beanFactory
        ConfigurableListableBeanFactory configurableListableBeanFactory = conditionContext.getBeanFactory();
        //2、获取类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        //3、获取当前环境信息
        Environment environment = conditionContext.getEnvironment();
        //4、获取到bean定义信息
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        String osName = environment.getProperty("os.name");
        if(osName.toUpperCase().contains("Linux".toUpperCase())){
            return true;
        }
        return false;
    }
}
