package com.jolan.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(10)
public class HandlerImplOne implements IHandler{
    public boolean handle() {
        System.out.println("I am handler one");
        return true;
    }
}
