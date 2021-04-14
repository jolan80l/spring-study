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
 *  3) BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *      postProcessBeanDefinitionRegistry();
 *      在所有bean定义信息将要被加载，bean实例还未创建
 *      优先于BeanFactoryPostProcessor执行，利用BeanDefinitionRegistryPostProcessor给容器中再添加一些组件。
 *
 *  原理
 *      1） ioc创建对象
 *      2） refresh() - > invokeBeanFactoryPostProcessors
 *      3）从容器中获取得所有的BeanDefinitionRegistryPostProcessor组件。
 *          ① 依次触发所有的postProcessBeanDefinitionRegistry()方法
 *          ② 再来触发postProcessBeanFactory()方法的BeanFactoryPostProcessor
 *      4）再从容器中找到BeanFactoryPostProcessor组件，然后依次触发postProcessFactory()方法
 * */
@Configuration
@ComponentScan("com.jolan.ext")
public class ExtConfig {

    @Bean
    public Blue blue(){
        return new Blue();
    }
}
