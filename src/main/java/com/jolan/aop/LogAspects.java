package com.jolan.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

import java.util.Arrays;

/**
 * @author jolan80
 * @date 2021-02-21 10:41
 *
 * Aspect注解:告诉SpringLogAspects是一个切面类
 */
@Aspect
public class LogAspects {

    //抽取公共的切入点表达式
    //1、本类引用
    //2、其他切面引用
    @Pointcut("execution(public int com.jolan.aop.MathCalculator.*(..))")
    public void pointCut(){

    }

    //@Before在目标方法之前切入：切入点表达式（指定在哪个方法切入）
//    @Before("public int com.jolan.aop.MathCalculator.div(int, int)")
//    @Before("public int com.jolan.aop.MathCalculator.*(..)")
//    public void logStart(){
//        System.out.println("除法运行...参数列表是:{}");
//    }

    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint){
        Object[] args = joinPoint.getArgs();
        System.out.println(joinPoint.getSignature().getName() + "运行...@Before:参数列表是:{" + Arrays.asList(args) +"}");
    }


    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint){
        System.out.println(joinPoint.getSignature().getName() + "结束...@After");
    }

    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result){//JoinPoint参数必须出现在方法的第一个位置，否则会报错
        System.out.println(joinPoint.getSignature().getName() + "正常返回...@AfterReturning:运行结果：｛" + result +"｝");
    }

    @AfterThrowing(value= "pointCut()", throwing = "exceptions")
    public void logException(Exception exceptions){
        System.out.println("除法异常...异常信息：｛" + exceptions.getMessage() + "｝");
    }
}
