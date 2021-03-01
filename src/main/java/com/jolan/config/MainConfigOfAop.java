package com.jolan.config;

import com.jolan.aop.LogAspects;
import com.jolan.aop.MathCalculator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author jolan80
 * @date 2021-02-21 10:35
 *
 * AOP:
 *  指在程序运行期间动态的将某段代码切入到指定方法指定位置进行运行的变成方式；
 *
 *  1、导入Aop模块（POM）；Spring AOP：（spring-aspects）
 *  2、定义一个业务逻辑类：MathCalculator。在业务逻辑运行时将日志打印（方法运行之前，方法结束，方法异常）
 *  3、定义一个日志切面类（LogAspects）:切面类里面的方法感知MathCalculator.div运行的情况
 *      通知方法：
 *          前置通知(@Before)：logStart：在目标方法运行之前执行
 *          后置通知(@After)：logEnd：在目标方法运行结束之后运行。无论方法正常结束还是异常结束
 *          返回通知(@AfterReturning)：logReturn：在目标方法正常返回之后运行
 *          异常通知(@AfterThrowing)：logException：在目标方法出现异常之后运行
 *          环绕通知（@Around）：动态代理，手动推进方法运行（joinPoint.proceed）
 *  4、给切面类的目标方法标注何时何地运行（通知注解）
 *  5、将切面类和业务逻辑类（目标方法所在类）都加入到容器中
 *  6、告诉spring哪个类是切面。给切面类加注解：@Aspects。
 *  7、给配置类中加@EnableAspectJAutoProxy【开启基于注解的aop模式】
 *      在Spring中有很多的@Enablexxx注解，其作用就是开启某个功能。
 *
 *   三个步骤：
 *      1） 将业务逻辑组件和切面类都加入到容器中，告诉Spring哪个是切面类（@Aspects）
 *      2） 在切面类上的每一个通知方法上标注通知注解，告诉Spring何时何地运行（切入点表达式）
 *      3） 开启基于注解的Aop模式：@EnableAspectJAutoProxy
 *
 *   AOP原理：【给容器中注册了什么组件，这个组件什么时候工作，这个组件的功能是什么】
 *      1.@EnableAspectJAutoProxy：
 *          @Import({AspectJAutoProxyRegistrar.class})：给容器中导入AspectJAutoProxyRegistrar
 *              利用AspectJAutoProxyRegistrar自定义给容器中注册bean：AnnotationAwareAspectJAutoProxyCreator
 *      2.AnnotationAwareAspectJAutoProxyCreator:
 *          ->AspectJAwareAdvisorAutoProxyCreator
 *              ->AbstractAdvisorAutoProxyCreator
 *                  ->AbstractAutoProxyCreator
 *                      implements SmartInstantiationAwareBeanPostProcessor, BeanFactoryAware
 *                      关注后置处理器（在bean初始化完成后做的事情）、自动装配BeanFactory
 *
 *        AbstractAutoProxyCreator.setBeanFactory()
 *        AbstractAutoProxyCreator.后置处理器的逻辑：
 *
 *        AbstractAdvisorAutoProxyCreator.setBeanFactory()->initBeanFactory()
 *
 *        AnnotationAwareAspectJAutoProxyCreator.initBeanFactory()
 *
 */
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAop {

    @Bean
    public MathCalculator calculator(){
        return new MathCalculator();
    }

    @Bean
    public LogAspects logAspects(){
        return new LogAspects();
    }
}
