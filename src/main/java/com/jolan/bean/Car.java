package com.jolan.bean;

import org.springframework.stereotype.Component;

/**
 * @author jolan80
 * @date 2020-08-11 20:36
 */
@Component
public class Car {
    public Car(){
        System.out.println("car constructor...");
    }

    public void init(){
        System.out.println("car ...init...");
    }

    public void destroy(){
        System.out.println("car ...destroy...");
    }
}
