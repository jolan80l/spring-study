package com.jolan.test;

import com.jolan.config.MainConfigOfOrder;
import com.jolan.order.HandlerChainInitialization;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.Map;

/**
 * @author jolan80
 * @date 2020-08-08 15:42
 */

public class OrderTest {

    @SuppressWarnings("resource")
    @Test
    public void test04(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfOrder.class);
        HandlerChainInitialization initialization = (HandlerChainInitialization)applicationContext.getBean("handlerChainInitialization");
        initialization.execute();
    }


}
