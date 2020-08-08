package com.jolan.config;

import com.jolan.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Scope;


import javax.jnlp.PersistenceService;

/**
 * @author jolan80
 * @date 2020-08-08 16:32
 */
@Configuration
public class MainConfig2 {
    //默认是单实例的
    @Bean("person")
    /**
     *  prototype : 多实例
     *  singleton : 单实例（默认值）
     *  request : 同一次请求创建一个实例
     *  session : 同一个session创建一个实例
     * */
    @Scope("prototype")
    public Person person(){
        return new Person("zhangsan", 25);
    }
}
