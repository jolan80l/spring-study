package com.jolan.config;

import com.jolan.bean.Color;
import com.jolan.bean.ColorFactoryBean;
import com.jolan.bean.Person;
import com.jolan.bean.Red;
import com.jolan.condition.LinuxCondition;
import com.jolan.condition.MyImportBeanDefinitionRegistrar;
import com.jolan.condition.MyImportSelector;
import com.jolan.condition.WindowsCondition;
import org.springframework.context.annotation.*;


/**
 * @author jolan80
 * @date 2020-08-08 16:32
 */
//满足当前条件，这个类中配置的所有bean注册才能生效
//@Conditional({LinuxCondition.class})
@Configuration
//@Import(Color.class)//导入Color组件，组件id默认是全类名
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {
    //默认是单实例的
    @Bean("person")
    /**
     *  prototype : 多实例
     *  singleton : 单实例（默认值）
     *  request : 同一次请求创建一个实例
     *  session : 同一个session创建一个实例
     * */
//    @Scope("prototype")
//    @Lazy
    public Person person(){
        return new Person("zhangsan", 25);
    }

    /**
     * @Conditional({Conditiion}) : 按照一定的条件进行判断，满足条件给容器中注册bean
     * 如果系统是windows，给容器中注册bill
     * 如果是linux系统，给容器中注册linus
     */
    @Conditional({WindowsCondition.class})
    @Bean("bill")
    public Person pserson01(){
        return new Person("Bill Gates", 62);
    }

    @Conditional({LinuxCondition.class})
    @Bean("linus")
    public Person pserson02(){
        return new Person("linus", 48);
    }

    @Bean
    public ColorFactoryBean colorFactoryBean(){
        return new ColorFactoryBean();
    }
}
