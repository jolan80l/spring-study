package com.jolan.order;

import org.springframework.core.annotation.Order;
import org.springframework.stereotype.Component;

@Component
@Order(30)
public class HandlerImplThree implements IHandler{
    public boolean handle() {
        System.out.println("I am handler three");
        return true;
    }
}
