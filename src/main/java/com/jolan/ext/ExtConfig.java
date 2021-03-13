package com.jolan.ext;



import com.jolan.bean.Blue;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


/**
 * 扩展原理：
 *  BeanPostProcessor：bean后置处理器，bean创建对象初始化前后拦截工作的
 *  BeanFactoryPostProcessor：beanFactory的后置处理器
 *      在BeanFactory标准初始化之后调用：所有的bean定义已经保存加载到BeanFactory，但是Bean的实例还没未创建
 *
 *
 *  1） ioc容器创建对象
 *  2）invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessor
 *      如何找到所有的BeanFactoryPostProcessor并执行他们的方法
 *          ① 直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并且执行他们的方法
 *          ② 在初始化创建其他组件前面执行
 * */
@Configuration
@ComponentScan("com.jolan.ext")
public class ExtConfig {

    @Bean
    public Blue blue(){
        return new Blue();
    }
}
