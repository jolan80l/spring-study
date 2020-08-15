package com.jolan.bean;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;

/**
 * @author jolan80
 * @date 2020-08-08 15:16
 */
public class Person {
    /**
     * 使用@Value给属性赋值
     * 1.基本类型
     * 2.使用SpEL?#{}
     * 3.使用${}获取配置文件中的属性
     * */

    @Value("张三")
    private String name;
    @Value("#{20 - 2}")
    private Integer age;
    @Value("${person.nickName}")
    private String nickName;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", nickName='" + nickName + '\'' +
                '}';
    }

    public Person(String name, Integer age) {
        this.name = name;
        this.age = age;
    }

    public Person() {

    }
}
