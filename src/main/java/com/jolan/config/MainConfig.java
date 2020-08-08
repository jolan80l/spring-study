package com.jolan.config;

import com.jolan.bean.Person;
import com.jolan.com.jolan.service.BookService;
import org.springframework.context.annotation.*;
import org.springframework.stereotype.Controller;
import org.springframework.stereotype.Service;

/**
 * @author jolan80
 * @date 2020-08-08 15:25
 *
 * 配置类 = 配置文件
 * @ComponentScan value:指定要扫描的包
 * excludeFilters = Filter[] ： 指定扫描的时候按照什么规则排除哪些组件
 * includeFilters = Filter[] : 指定扫描的时候只需要包含哪些组件
 * FilterType.ANNOTATION : 按照注解
 * FilterType.ASSIGNABLE_TYPE : 按照给定的类型
 * FilterType.ASPECTJ : 使用ASPECTJ表达式
 * FilterType.REGEX : 使用正则表达式
 * FilterType.CUSTOM : 使用自定义规则
 */
@Configuration//告诉Spring这是一个配置类
//@ComponentScan(value="com.jolan", excludeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
//})
//@ComponentScan(value="com.jolan", includeFilters = {
//        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
//}, useDefaultFilters = false)
@ComponentScans(
        value = {
                @ComponentScan(value="com.jolan", includeFilters = {
//                        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
//                        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class}),
                        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
                }, useDefaultFilters = false)
        }
)
public class MainConfig {
    //给容器中注册一个bean;类型为返回值的类型;id默认是方法名
    @Bean
    public Person person(){
        return new Person("lisi", 20);
    }
}
