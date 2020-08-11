package com.jolan.config;

import com.jolan.bean.Car;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;

/**
 * @author jolan80
 * @date 2020-08-11 20:34
 *
 * bean的生命周期：
 *  bean创建——初始化——销毁
 *  我们可以自定义初始化和销毁方法：容器在bean进行到当前生命周期的时候，来调用我们自定义的初始化和销毁方法
 *
 *  构造（对象创建）
 *      单实例：在容器启动的时候创建对象
 *      多实例：在每次获取的时候创建对象
 *  初始化：
 *      对象创建完成，并赋值好，调用初始化方法。对于单实例来讲，容器启动时会进行对象创建及初始化；对于多实例来讲，只有在获取bean的时候才会调用构造方法创建对象和初始化方法
 *  销毁：
 *      单实例：容器关闭的时候进行销毁。
 *      多实例：容器不会管理这个bean，容器不会调用销毁方法，需要手工调用销毁方法
 *
 *  1）指定初始化和销毁方法
 *      指定init-method和destory-method
 *  2）通过让bean实现InitializingBean定义初始化逻辑
 *     通过让bean实现DisposableBean定义销毁逻辑
 */
@Configuration
@ComponentScan("com.jolan")
public class MainConfigLifeCycle {
    @Scope("prototype")
    @Bean(initMethod = "init", destroyMethod = "destroy")
    public Car car(){
        return new Car();
    }
}
