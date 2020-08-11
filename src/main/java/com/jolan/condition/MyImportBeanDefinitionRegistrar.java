package com.jolan.condition;

import com.jolan.bean.RainBow;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanNameGenerator;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.annotation.ImportBeanDefinitionRegistrar;
import org.springframework.core.type.AnnotationMetadata;

/**
 * @author jolan80
 * @date 2020-08-09 10:43
 */
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * importingClassMetadata : 当前类的注解信息
     * registry ： bean定义的注册类
     *
     * 把所有需要添加到容器中的bean : 调用BeanDefinitionRegistry.registerBeanDefinitions，手动注册
     */
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean redFlag = registry.containsBeanDefinition("com.jolan.bean.Red");
        boolean blueFlag = registry.containsBeanDefinition("com.jolan.bean.Blue");
        if(redFlag && blueFlag){
            //指定bean
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            registry.registerBeanDefinition("rainBow", rootBeanDefinition);
        }
    }
}
