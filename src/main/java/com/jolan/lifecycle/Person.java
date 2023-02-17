package com.jolan.lifecycle;


import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.InitializingBean;

/**
 * @author qsk
 */
public class Person implements BeanFactoryAware, BeanNameAware,
        InitializingBean, DisposableBean {

    private String name;
    private int age ;

    private BeanFactory beanFactory;
    private String beanName;

    public Person(String name, int age) {
        System.out.println("Person的构造方法执行。。。");
        this.name = name;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        System.out.println("注入name属性。。。");
        this.name = name;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        System.out.println("注入age属性。。。");
        this.age = age;
    }

    public BeanFactory getBeanFactory() {
        return beanFactory;
    }

    // BeanFactoryAware接口方法
    public void setBeanFactory(BeanFactory arg0) throws BeansException {
        System.out.println("调用BeanFactoryAware的setBeanFactory方法。。。");
        this.beanFactory = arg0;
    }

    // BeanNameAware接口方法
    public void setBeanName(String arg0) {
        System.out.println("调用BeanNameAware的setBeanName方法。。。");
        this.beanName = arg0;
    }

    // InitializingBean接口方法
    public void afterPropertiesSet() throws Exception {
        System.out.println("调用InitializingBean的afterPropertiesSet方法。。。");
    }

    // DiposibleBean接口方法
    public void destroy() throws Exception {
        System.out.println("调用DiposibleBean的destory方法。。。");
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                '}';
    }

    public void init_method(){
        System.out.println("调用自定义的init-method。。。");
    }

    public void destroy_method(){
        System.out.println("调用自定义的destory-method。。。");
    }
}
