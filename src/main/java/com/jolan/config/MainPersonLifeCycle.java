package com.jolan.config;



import com.jolan.lifecycle.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;


@Configuration
@ComponentScan("com.jolan.lifecycle")
public class MainPersonLifeCycle {
    @Bean(initMethod = "init_method", destroyMethod = "destroy_method")
    public Person person(){
        return new Person("wql", 18);
    }
}