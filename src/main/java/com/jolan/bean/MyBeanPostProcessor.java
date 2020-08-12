package com.jolan.bean;

import com.sun.scenario.effect.impl.sw.sse.SSEBlend_SRC_OUTPeer;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * @author jolan80
 * @date 2020-08-12 20:59
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     * @param bean:刚刚创建的bean实例
     * @param beanName : bean实例在Spring容器中的id
     * @return 返回的对象
     * */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization..." + beanName + "=>" + bean);
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization..." + beanName + "=>" + bean);
        return bean;
    }
}
