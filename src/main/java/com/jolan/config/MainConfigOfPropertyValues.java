package com.jolan.config;

import com.jolan.bean.Person;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

/**
 * @author jolan80
 * @date 2020-08-13 10:45
 */
@PropertySource(value={"classpath:/person.properties"})//使用@PropertySource读取外部配置文件的属性，保存到运行的环境变量中
@Configuration
public class MainConfigOfPropertyValues {
    @Bean
    public Person person(){
        return new Person();
    }
}
