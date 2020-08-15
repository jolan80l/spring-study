package com.jolan.config;

import com.jolan.com.jolan.dao.BookDao;
import com.sun.org.apache.xerces.internal.impl.dv.xs.BooleanDV;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

/**
 * @author jolan80
 * @date 2020-08-15 15:12
 *
 * 自动装配：
 *  Spring利用依赖注入（DI），完成对IOC容器中各个组件的依赖关系赋值；
 *      1）@Autoware：自动注入。
 *          1.默认有限按照类型去容器中找对应的组件，applicationContext.getBean(BookDao.class)
 *          2.如果找到多个相同类型的组件，再将属性的名称作为组件的id去勇气中查找，applicationContext.getBean("bookDao")
 *          3.@Qualifier("bookDao"):使用@Qualifier指定需要装配组件的id，而不是使用属性名。
 *          4.自动装配默认一定要将属性赋值好，没有就会报错。
 *          5.@Primary:让spring自动装配的时候，默认使用首选的bean。也可以继续使用@Qualifier指定需要装配的bean的名字
 *      2）spring还支持使用@Resource(JSR250)和@Inject(JSP330)
 *          1.@Resource:可以和@Autowired一样实现自动装配功能，默认是按照组件名称进行装配，也可以用name属性重新指定：@Resource(name="bookDao2")。
 *          不支持@Primary，也没有required属性。
 *          2.@Inject：需要导入javax.inject的包，和Autowired的功能一样。没有required属性。
 *      AutowiredAnnotationBeanPostProccessor：解析完成自动装配功能
 *
 *      3）@Autowired：构造器，参数，方法，属性。
 */
@Configuration
@ComponentScan({"com.jolan.com.jolan.controller", "com.jolan.com.jolan.service", "com.jolan.com.jolan.dao"})
public class MainConfigOfAutowired {
    @Primary
    @Bean("bookDao2")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.setLabel("2");
        return bookDao;
    }
}
