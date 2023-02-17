package com.jolan.order;

import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;
import javax.annotation.Resource;
import java.util.List;

@Component
public class HandlerChainInitialization {
    @Resource
    private List<IHandler> handlers;

    public void execute(){
        if (CollectionUtils.isEmpty(handlers)) {
            return;
        }
        for (IHandler handler : handlers) {
            boolean flag = handler.handle();
            if (!flag) {
                return;
            }
        }
    }

}
