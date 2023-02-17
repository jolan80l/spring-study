package com.jolan.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(20)
public class HandlerImplTwo implements IHandler{
    public boolean handle() {
        System.out.println("I am handler two");
        return true;
    }
}
