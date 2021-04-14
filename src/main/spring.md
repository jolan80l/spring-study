

# 导入spring核心包

​	浏览器打开https://mvnrepository.com/，搜索spring context，找到相应版本点击进入，即可找到对应的maven依赖。

```xml
<!-- https://mvnrepository.com/artifact/org.springframework/spring-context -->
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-context</artifactId>
    <version>5.2.6.RELEASE</version>
</dependency>
```

# xml定义bean

## 创建person类（略）

## 创建xml文件

``` xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id = "person" class = "com.jolan.bean.Person">
        <property name="name" value="zhangsan"></property>
        <property name="age" value="18"></property>
    </bean>
</beans>
```

## 创建测试类并运行

```java
public class MainTest {
    public static void main(String[] args) {
        ApplicationContext applcationContext = new ClassPathXmlApplicationContext("beans.xml");
        Person bean = (Person)applcationContext.getBean("person");
        System.out.println(bean);
    }
}
```

## 运行结果

```json
Person{name='zhangsan', age=18}
```

# 使用配置类

## 创建配置类

```java
/**
 * @author jolan80
 * @date 2020-08-08 15:25
 *
 * 配置类 = 配置文件
 */
@Configuration//告诉Spring这是一个配置类
public class MainConfig {
    //给容器中注册一个bean;类型为返回值的类型;id默认是方法名
    @Bean
    public Person person(){
        return new Person("lisi", 20);
    }
}
```

## 创建测试类并运行

```java
ApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
Person bean = (Person)applicationContext.getBean(Person.class);
System.out.println(bean);
String[] names = applicationContext.getBeanNamesForType(Person.class);
for (String name : names) {
    System.out.println(name);
}
```

## 运行结果

```json
Person{name='lisi', age=20}
person
```

## 修改注册容器中的beanid

### 方法一

​		修改被@Bean注解的方法名称

### 方法二

​		在@Bean注解上修改，如@Bean("person").

# 使用xml方式配置包扫描

```xml
<!-- 包扫描：只要标注了@Controller、@Service、@Respositry、@Compoment，会被自动扫描加入配置中 -->
<context:component-scan base-package="com.jolan"></context:component-scan>
```

# 使用配置类做包扫描

## 创建配置类		

​		使用注解@ComponentScan

```java
@Configuration//告诉Spring这是一个配置类
@ComponentScan(value="com.jolan")
public class MainConfig {
    //给容器中注册一个bean;类型为返回值的类型;id默认是方法名
    @Bean
    public Person person(){
        return new Person("lisi", 20);
    }
}
```

## 创建bean

​		分别创建BookContoller,BookService,BookDao，并且分别用@Controller，@Service，@Repository标签注解。

## 创建测试类

```java
@SuppressWarnings("resource")
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
```

## 运行结果

```xml
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig
bookController
bookDao
bookService
person
```

​		除了spring自身的组件外，还有我们自己定义的几个bean。

## 包扫描排除

​		排除使用@Controller和@Service注解的bean。

```java
@ComponentScan(value="com.jolan", excludeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class, Service.class})
})
```

​		测试结果如下。

```java
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig
bookDao
```

## 包扫描包含

​		只包含@Controller注解的bean。注意修改useDefaultFilters属性，该属性默认是扫描所有，当该属性值为false时includeFilters才能生效。

```java
@ComponentScan(value="com.jolan", includeFilters = {
        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
}, useDefaultFilters = false)
```

## 包扫描数组

​		包扫描注解@ComponentScan是可重复使用的注解，或者使用@ComponentScans数组，其中可以配置多个@ComponentScan包扫描规则。

```java
@ComponentScans(
        value = {
                @ComponentScan(value="com.jolan", includeFilters = {
                        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class})
                }, useDefaultFilters = false)
        }
)
```

## 其他包扫描过滤规则

​		上面的例子中介绍了按照注解类型进行包扫描的过滤，下面介绍其他的几种规则。

### 按照给定类型过滤

​		按照指定的类型过滤使用FilterType.ASSIGNABLE_TYPE进行指定。如下所示，在原来的基础上，增加了BookService类型。

```java
@ComponentScans(
        value = {
                @ComponentScan(value="com.jolan", includeFilters = {
                        @ComponentScan.Filter(type = FilterType.ANNOTATION, classes = {Controller.class}),
                        @ComponentScan.Filter(type = FilterType.ASSIGNABLE_TYPE, classes = {BookService.class})
                }, useDefaultFilters = false)
        }
)
```

​		运行结果如下（忽略spring自身的组件），可以看到增加了bookService的bean组件。

```java
mainConfig
bookController
bookService
person
```

### 按ASPECTJ表达式过滤

​		使用较少，不介绍。

### 按则表达式过滤

​		使用较少，不介绍。

### 自定义过滤类型

#### 创建过滤类

​		自定义的过滤类，需要实现TypeFilter接口。

```java
public class MyTypeFilter implements TypeFilter {
    /**
     * metadataReader ： 读取到的当前正在扫描的类信息
     * metadataReaderFactory ： 可以获取到其他任何类信息的工厂
     *
     **/
    public boolean match(MetadataReader metadataReader, MetadataReaderFactory metadataReaderFactory) throws IOException {
        //获取当前类注解的信息
        AnnotationMetadata annotationMetadata = metadataReader.getAnnotationMetadata();
        //获取当前正在扫描的类信息
        ClassMetadata classMetadata = metadataReader.getClassMetadata();
        //获取当前类的资源信息（类路径等）
        Resource resource = metadataReader.getResource();
        String className = classMetadata.getClassName();
        System.out.println("--->" + className);
        if(className.contains("er")){
            return true;
        }
        return false;
    }
}
```

#### 修改配置类

​		在配置类中，指定Filter的type值为FilterType.CUSTOM，classes数组中指定自己创建的过滤类MyTypeFilter。

```java
@ComponentScans(
        value = {
                @ComponentScan(value="com.jolan", includeFilters = {
                        @ComponentScan.Filter(type = FilterType.CUSTOM, classes = {MyTypeFilter.class})
                }, useDefaultFilters = false)
        }
)
```

#### 运行结果

```java
--->com.jolan.test.IOCTest
--->com.jolan.bean.Person
--->com.jolan.bean.com.jolan.MainTest
--->com.jolan.com.jolan.controller.BookController
--->com.jolan.com.jolan.dao.BookDao
--->com.jolan.com.jolan.service.BookService
--->com.jolan.config.MyTypeFilter
org.springframework.context.annotation.internalConfigurationAnnotationProcessor
org.springframework.context.annotation.internalAutowiredAnnotationProcessor
org.springframework.context.annotation.internalCommonAnnotationProcessor
org.springframework.context.event.internalEventListenerProcessor
org.springframework.context.event.internalEventListenerFactory
mainConfig
person
bookController
bookService
myTypeFilter
```

# 组件的作用域

## 默认

​		spring的组件默认是单实例的。

### 创建主配置文件

```java
@Configuration
public class MainConfig2 {
    //默认是单实例的
    @Bean("person")
    public Person person(){
        return new Person("zhangsan", 25);
    }
}
```

### 创建测试类

```java
@Test
    public void test02(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
        Object bean1 =  applicationContext.getBean("person");
        Object bean2 =  applicationContext.getBean("person");
        System.out.println(bean1 == bean2);
    }
```

### 运行结果

```java
mainConfig2
person
true
```

### 单实例创建时机

​		ioc容器启动会调用方法创建对象放到ioc容器中。以后每次获取就直接从容器中拿。

### 懒加载

​		容器启动时不创建对象，在第一次使用bean时候再创建对象，并初始化，在后面使用过程中不再加载。使用@Lazy进行注解。懒加载只能针对单实例的作用域有效。

```java
@Bean("person")
@Lazy
public Person person(){
    return new Person("zhangsan", 25);
}
```



## 多实例

### 修改住配置文件

​		使用@Scope属性修改作用域，这里修改为prototype。

```java
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
```

### 运行结果

```java
mainConfig2
person
false
```

### 多实例创建时机

​		在每次获取对象时才会创建对象把对象加入spring容器。

# 按条件注册bean

# 容器中正常注册bean

```java
@Bean("person")
public Person person(){
    return new Person("zhangsan", 25);
}

/**
     * @Conditional : 按照一定的条件进行判断，满足条件给容器中注册bean
     *
     */
@Bean("bill")
public Person pserson01(){
    return new Person("Bill Gates", 62);
}

@Bean("linus")
public Person pserson02(){
    return new Person("linus", 48);
}
```

## 测试类

```java
@SuppressWarnings("resource")
@Test
public void test03(){
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
    String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
    for (String s : namesForType) {
        System.out.println(s);
    }
    Map<String, Person> map = applicationContext.getBeansOfType(Person.class);
    System.out.println(map);
}
```

## 运行结果

```java
person
bill
linus
{person=Person{name='zhangsan', age=25}, bill=Person{name='Bill Gates', age=62}, linus=Person{name='linus', age=48}}
```

## 创建Condition类

​		由于@Conditional中使用的是Condition类型的数组，所以需要创建实现了Condition接口的自定义类。

```java
public class LinuxCondition implements Condition {
    /**
     * ConditionContext : 判断环境能使用的上下文（环境）
     * AnnotatedTypeMetadata : 注解信息
     */
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //是否为linux系统
        //1、能获取到ioc使用的beanFactory
        ConfigurableListableBeanFactory configurableListableBeanFactory = conditionContext.getBeanFactory();
        //2、获取类加载器
        ClassLoader classLoader = conditionContext.getClassLoader();
        //3、获取当前环境信息
        Environment environment = conditionContext.getEnvironment();
        //4、获取到bean定义信息
        BeanDefinitionRegistry registry = conditionContext.getRegistry();
        String osName = environment.getProperty("os.name");
        if(osName.toUpperCase().contains("Linux".toUpperCase())){
            return true;
        }
        return false;
    }
}
```

```java
public class WindowsCondition implements Condition {
    public boolean matches(ConditionContext conditionContext, AnnotatedTypeMetadata annotatedTypeMetadata) {
        //是否为windows系统
        Environment environment = conditionContext.getEnvironment();
        String osName = environment.getProperty("os.name");
        if(osName.toUpperCase().contains("windows".toUpperCase())){
            return true;
        }
        return false;
    }
}
```

​		这两个类的作用是根据操作系统判断返回true或者false，具体的业务场景可根据情况确定。

## 修改配置类

```java
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
```

## @Conditional注解运行结果

​		如下所示，可以看到linus的bean没有被注册到Spring容器中，因为LinuxCondition返回的是false。

```java
当前操作系统名称：Windows 8.1
person
bill
{person=Person{name='zhangsan', age=25}, bill=Person{name='Bill Gates', age=62}}
```

## 修改运行时参数测试

​		手工修改当前环境参数，在idea的启动配置中增加-Dos.name=linux，如下图所示。再次运行测试类，发现linus已经被注册到容器中。

![avatar](pic/1.png "idea增加运行时参数")

```java
当前操作系统名称：linux
person
linus
{person=Person{name='zhangsan', age=25}, linus=Person{name='linus', age=48}}
```

## 在配置类上使用@Conditional

​		在配置类上增加@Conditional({LinuxCondition.class})，表示只有当操作系统为linux时才会往容器中测试bean，所以这个配置类中的bean一个也不会注册。

```java
//满足当前条件，这个类中配置的所有bean注册才能生效
@Conditional({LinuxCondition.class})
@Configuration
public class MainConfig2 {
    //注册若干bean
}
```

```java
当前操作系统名称：Windows 8.1
{}
```

# 给容器中注册组件的几种方式

+ 包扫描 + 组件标注注解（@Controller/@Service@Respository/@Compoment）

+ 使用@Bean[导入第三方包里面的组件]
+ @Import[快速给容器中导入一个组件
  1. @Import（要导入的组件）：容器中就会自动注册这个组件，id默认是全类名
  
  2. @ImportSelector：返回需要导入的组件的全类名数组
  
  3. @ImportBeanRegistrar : 手动注册bean到容器中
  
  4. 使用Spring提供的FactoryBean（工厂bean）.
  
     默认获取到的是工程bean调用getObject创建的对象。
  
     要获取工程bean本身，需要给id前面增加一个&

# @Import

## 创建普通color类

```java
public class Color {
    
}
```

## 创建测试类并运行

```java
@SuppressWarnings("resource")
    @Test
    public void test04(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
        printBeans(applicationContext);//打印所有的bean名称
    }
```

```java
mainConfig2
person
bill
```

## 配置类上增加@Import注解

```java
@Configuration
@Import(Color.class)//导入Color组件，组件id默认是全类名
public class MainConfig2 {
    //注册若干bean
}
```

​		运行结果。

```java
mainConfig2
com.jolan.bean.Color
person
bill
```

#  @Import一次导入多个组件

​		新增一个Read类（略），并加入到Spring容器。

```java
@Import({Color.class, Red.class})
public class MainConfig2 {
    //注册若干bean
}
```

```java
mainConfig2
com.jolan.bean.Color
com.jolan.bean.Red
person
bill
```

# @ImportSelector

​		@ImportSelector要和@Import配合使用。

## 创建Blue和Yellow类

​		略

## 创建ImportSelector实现类

```java
public class MyImportSelector implements ImportSelector {
    //自定义逻辑，返回需要的组件,返回值就是要导入容器中的组件全类名
    //AnnotationMetadata:当前标注@Import注解的类的所有注解信息
    //该方法不能返回null，可以返回空数组
    public String[] selectImports(AnnotationMetadata annotationMetadata) {
        return new String[]{"com.jolan.bean.Blue", "com.jolan.bean.Yellow"};
    }
}
```

## 修改配置类

```java
@Import({Color.class, Red.class, MyImportSelector.class})
public class MainConfig2 {
    //注册若干bean
}
```



## 运行结果

​		从运行结果发现Blue和Yellow也被注册到Spring容器中

```java
mainConfig2
com.jolan.bean.Color
com.jolan.bean.Red
com.jolan.bean.Blue
com.jolan.bean.Yellow
person
bill
```

# @ImportBeanDefinitionRegistrar

## 创建RainBow类

​		略

## 创建ImportBeanDefinitionRegistrar实现类

​		如果存在Red组件和Blue组件，则向容器中添加id为rainBow的组件。

```java
public class MyImportBeanDefinitionRegistrar implements ImportBeanDefinitionRegistrar {
    /**
     * importingClassMetadata : 当前类的注解信息
     * registry ： bean定义的注册类
     *
     * 把所有需要添加到容器中的bean : 调用BeanDefinitionRegistry.registerBeanDefinitions，手动注册
     */
    public void registerBeanDefinitions(AnnotationMetadata importingClassMetadata, BeanDefinitionRegistry registry) {
        boolean redFlag = registry.containsBeanDefinition("com.jolan.bean.Red");
        boolean blueFlag = registry.containsBeanDefinition("com.jolan.bean.Blue");
        if(redFlag && blueFlag){
            //指定bean
            RootBeanDefinition rootBeanDefinition = new RootBeanDefinition(RainBow.class);
            registry.registerBeanDefinition("rainBow", rootBeanDefinition);
        }
    }
}
```

## 修改配置类

```java
@Import({Color.class, Red.class, MyImportSelector.class, MyImportBeanDefinitionRegistrar.class})
public class MainConfig2 {
    
}
```

## 运行结果

​		可以看到运行结果中添加了rainBow组件。

```java
mainConfig2
com.jolan.bean.Color
com.jolan.bean.Red
com.jolan.bean.Blue
com.jolan.bean.Yellow
person
bill
rainBow
```

# FactoryBean

## 创建实现FactoryBean实现类

​		可以修改isSingleton()的返回值来决定bean的作用域。如果isSingleton()返回false，则每次调用都会调用getObject()方法。

```java
/**
 * @author jolan80
 * @date 2020-08-11 20:08
 *
 * 创建Spring定义的FactoryBean
 */
public class ColorFactoryBean implements FactoryBean<Color> {
    //返回一个Color对象，这个对象会添加到容器中
    public Color getObject() throws Exception {
        System.out.println("ColorFactoryBean...getObject()");
        return new Color();
    }

    public Class<?> getObjectType() {
        return Color.class;
    }
    //是否为单例
    public boolean isSingleton() {
        return true;
    }
}
```

## 修改配置类

​		将ColorFactoryBean加入到spring容器

```java
@Bean
public ColorFactoryBean colorFactoryBean(){
    return new ColorFactoryBean();
}
```

## 测试结果

​		可以看到id为colorFactoryBean的bean，其类型为Color。也就是说工厂Bean获取的是调用getObject创建的对象。

```java
@Test
public void test04(){
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
    printBeans(applicationContext);//打印所有的bean名称

    Object bean2 = applicationContext.getBean("colorFactoryBean");
    Object bean3 = applicationContext.getBean("colorFactoryBean");
    System.out.println("bean的类型：" + bean2.getClass());
    System.out.println(bean2 == bean3);
}
```

```java
mainConfig2
com.jolan.bean.Color
com.jolan.bean.Red
com.jolan.bean.Blue
com.jolan.bean.Yellow
person
bill
colorFactoryBean
rainBow
ColorFactoryBean...getObject()
bean的类型：class com.jolan.bean.Color
true
```

## 获取FactoryBean实现类的对象

​		从上面实例可以看出，根据bean的id获取的对象是getObject()方返回的对象。如果要获取FactoryBean的对象可以在id前面添加&符号。

## 修改测试类及测试结果

```java
@Test
public void test04(){
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfig2.class);
    printBeans(applicationContext);//打印所有的bean名称
    //工厂Bean获取的是调用getObject创建的对象
    Object bean2 = applicationContext.getBean("colorFactoryBean");
    Object bean3 = applicationContext.getBean("colorFactoryBean");
    System.out.println("bean的类型：" + bean2.getClass());
    System.out.println(bean2 == bean3);
    Object bean4 = applicationContext.getBean("&colorFactoryBean");
    System.out.println(bean4.getClass());
}
```

```java
mainConfig2
com.jolan.bean.Color
com.jolan.bean.Red
com.jolan.bean.Blue
com.jolan.bean.Yellow
person
bill
colorFactoryBean
rainBow
ColorFactoryBean...getObject()
bean的类型：class com.jolan.bean.Color
true
class com.jolan.bean.ColorFactoryBean
```

# bean的生命周期

​		bean的生命周期包括创建、初始化和销毁。创建对象通过构造调用构造函数完成。初始化和销毁提供一下几种方式。

## 指定@Bean的初始化和销毁方法

### 创建Car类

```java
public class Car {
    public Car(){
        System.out.println("car constructor...");
    }

    public void init(){
        System.out.println("car ...init...");
    }

    public void destroy(){
        System.out.println("car ...destroy...");
    }
}
```

### 修改配置类

​		在@Bean注解中，指定initMethod和destroyMethod。这里分别对象Car类中的init()方法和destroy()方法。

```java
@Bean(initMethod = "init", destroyMethod = "destroy")
public Car car(){
    return new Car();
}
```

### 测试结果

​		可以看到首先调用构造方法，然后是init()方法，最后在容器销毁时调用destroy()方法。注意这里默认bean的作用域是单例的。如果在上面的配置类上指定@Scope("prototype")，那么输出结果将会是容器创建完成...。如果在测试类中增加applicationContext.getBean("car")这段代码，输出结果将会是最下面的结果

```java
@Test
public void test01(){
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);
    System.out.println("容器创建完成...");

    //关闭容器
    applicationContext.close();
}
```

```java
car constructor...
car ...init...
容器创建完成...
car ...destroy...
```

```java
容器创建完成...
car constructor...
car ...init...
```

## 实现接口

​		可以让bean实现InitializingBean来对bean进行初始化，实现DisposableBean完成对bean的销毁。

### 创建类

```java
@Component
public class Cat implements InitializingBean, DisposableBean {
    public Cat(){
        System.out.println("cat constructor...");
    }

    public void destroy() throws Exception {
        System.out.println("cat ...destroy...");
    }

    public void afterPropertiesSet() throws Exception {
        System.out.println("cat ...afterPropertiesSet...");
    }
}
```

### 修改配置类

​		在配置类中增加包扫描。

```java
@Configuration
@ComponentScan("com.jolan")
public class MainConfigLifeCycle {
    //do something
}
```

### 测试结果

​		测试类代码未做修改。可以看到cat类的创建、初始化和销毁过程。

```java
cat constructor...
cat ...afterPropertiesSet...
容器创建完成...
car constructor...
car ...init...
cat ...destroy...
```

## JSR250

​		使用JSR250规范进行初始化和销毁工作。

### 创建类

​		init()方法和destroy()方法分别用@PostConstruct和@PreDestroy注解进行标注。

```java
@Component
public class Dog {
    public Dog() {
        System.out.println("Dog constructor...");
    }

    //对象创建并赋值之后调用
    @PostConstruct
    public void init(){
        System.out.println("Dog ...@PostConstruct...");
    }

    //容器移除对象之前
    @PreDestroy
    public void destroy(){
        System.out.println("Dog ...@PreDestroy...");
    }
}
```

### 测试结果

```java
cat constructor...
cat ...afterPropertiesSet...
Dog constructor...
Dog ...@PostConstruct...
容器创建完成...
car constructor...
car ...init...
Dog ...@PreDestroy...
cat ...destroy...
```

## 实现BeanPostProcessor

### 创建类

```JAVA
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {
    /**
     * @param bean:刚刚创建的bean实例
     * @param beanName : bean实例在Spring容器中的id
     * @return 返回的对象
     * */
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization..." + beanName + "=>" + bean);
        return bean;
    }

    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization..." + beanName + "=>" + bean);
        return bean;
    }
}
```

### 测试结果

​		这里只截取了部分结果。拿Cat类进行举例。

+ 先调用了Cat类的构造方法，得到一个Cat对象。
+ 再任何初始化方法调用之前，先调用postProcessBeforeInitialization()方法。
+ 执行Cat类的初始化方法。
+ 在所有初始化方法调用之后调用postProcessAfterInitialization()方法。

```java
cat constructor...
postProcessBeforeInitialization...cat=>com.jolan.bean.Cat@5bd03f44
cat ...afterPropertiesSet...
postProcessAfterInitialization...cat=>com.jolan.bean.Cat@5bd03f44
```

# BeanPostProcessor小总结

​		BeanPostProcessor是bean的后置处理器，在bean创建之后做一些后置操作。

+ postProcessBeforeInitialization：在任何初始化工作之前调用。
+ postProcessAfterInitialization：在所有初始化工作完成之后调用。

​		在整个bean的生命周期中，处于下面的位置。populateBean是构建一个bean对象，然后是调用postProcessBeforeInitialization，接着调用bean自身的初始化方法，最后调用postProcessAfterInitialization。

```java
populateBean(beanName, mbd, instanceWrapper);
       initializeBean：｛
           //遍历得到容器中所有的BeanPostProcessor:挨个执行BeforeInitialization，一旦返回null，跳出for循环，不会执行后面的BeanPostProcessor
           applyBeanPostProcessorsBeforeInitialization(bean, beanName);
           invokeInitMethods(beanName, wrappedBean, mbd);//执行初始化
           applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
       ｝
```

​		在spring底层，也有对BeanPostProcessor的大量使用。如：

+ bean赋值
+ 注入其他组件
+ @Autowired
+ 生命周期注解功能@PostConstruct
+ @Async等

# @Value

​		使用@Value给bean的属性赋值。

## 增加配置文件

​		在resources下创建person.properties配置文件。

```properties
person.nickName = 小张三
```

## 创建配置类

```java
@PropertySource(value={"classpath:/person.properties"})//使用@PropertySource读取外部配置文件的属性，保存到运行的环境变量中
@Configuration
public class MainConfigOfPropertyValues {
    @Bean
    public Person person(){
        return new Person();
    }
}
```

## 修改Person类

```java
public class Person {
    /**
     * 使用@Value复制
     * 1.基本数值
     * 2.可以写SpEL：#{}
     * 3.可以写${}，取出配置文件中的值(在运行环境变量的值)
     * */

    @Value("张三")
    private String name;
    @Value("#{20 - 2}")
    private Integer age;
    @Value("${person.nickName}")
    private String nickName;
    //Getter Setter toString 
}
```

## 测试结果

​		可以看到输出的person属性为是张三和18.

```java
@Test
public void test01(){
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigLifeCycle.class);
    System.out.println("容器创建完成...");

    applicationContext.getBean("car");
    //关闭容器
    applicationContext.close();
}
```

```java
******忽略spring自身组件*******
mainConfigOfPropertyValues
person
==================
Person{name='张三', age=18, nickName='小张三'}
小张三
```

# 自动装配

## @Autowired

### 修改controller等类

​		在前面的学习中创建了几个bean分别为BookContoller,BookService,BookDao，现在对其进行修改。

#### BookController

```java
@Controller
public class BookController {
    @Autowired
    private BookController bookController;
}
```

#### BookService

```java
@Service
public class BookService {
    @Autowired
    private BookDao bookDao;

    public void print(){
        System.out.println(bookDao);
    }
}
```

### 添加新配置类

```java
@Configuration
@ComponentScan({"com.jolan.com.jolan.controller", "com.jolan.com.jolan.service", "com.jolan.com.jolan.dao"})
public class MainConfigOfAutowired {

}
```

### 添加测试类及运行结果

​		可以看到BookService中自动装配的BookDao对象和从容器中直接过去的BookDao对象是相同的。

```java
AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
@Test
public void test01(){
    BookService bookService = (BookService)applicationContext.getBean(BookService.class);
    bookService.print();

    BookDao bookDao = (BookDao)applicationContext.getBean(BookDao.class);
    System.out.println(bookDao);
}
```

```java
com.jolan.com.jolan.dao.BookDao@20d28811
com.jolan.com.jolan.dao.BookDao@20d28811
```

### 多个相同类型组件情况

#### 修改BookDao类

```java
@Repository
public class BookDao {
    private String label = "1";

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    @Override
    public String toString() {
        return "BookDao{" +
                "label='" + label + '\'' +
                '}';
    }
}
```

#### 修改配置类

​		在配置类中注册一个id为bookDao2的组件。此时容器中有两个类型为BookDao的组件，一个是通过组件扫描注册的bookDao，还有当前通过@Bean注册的bookDao2

```java
@Bean("bookDao2")
public BookDao bookDao(){
    BookDao bookDao = new BookDao();
    bookDao.setLabel("2");
    return bookDao;
}
```

#### 修改测试类并运行

​		运行结果是BookService中注入的是id为bookDao的组件。也就是说，当有多个相同类型的组件被@Autowired注入的时候，会用组件名作为id到容器中查找，如果找到则用applicationContext.getBean("id")方式注入。

```java
AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
@Test
public void test01(){
    BookService bookService = (BookService)applicationContext.getBean(BookService.class);
    bookService.print();

    //        BookDao bookDao = (BookDao)applicationContext.getBean(BookDao.class);
    //        System.out.println(bookDao);
}
```

```java
BookDao{label='1'}
```

#### @Qualifier

​		使用@Qualifier指定需要装备的组件id，而不是使用属性名。如在这个例子中，我们想使用bookDao2这个组件注入到BookService对象中，可以使用这个注解。需要修改BookService类。

```java
@Service
public class BookService {
    @Qualifier("bookDao2")
    @Autowired
    private BookDao bookDao;

    public void print(){
        System.out.println(bookDao);
    }
}
```

```java
BookDao{label='2'}
```

#### @Primary

​		当容器中有多个相同类型的组件可以被自动装备的时候，默认使用被@Primary注解标注的类。

​		修改配置类

```java
@Primary
@Bean("bookDao2")
public BookDao bookDao(){
    BookDao bookDao = new BookDao();
    bookDao.setLabel("2");
    return bookDao;
}
```

​		去掉BookService上的@Qualifier注解。

​		运行结果如下所示。

```java
BookDao{label='2'}
```

​		需要注意，@Qualifier注解的优先级高于@Primary。

### 没有组件则不装配

​		使用@Autowired的required属性，如果容器中没有找到需要类型的组件，则不进行注入。

```java
@Autowired(required = false)
```

### Autowired标注在方法上

#### 创建bean--Boss

​	创建Boss类型，并使用@Component注解标注，把它加入Spring容器。在配置文件中将Boss引入。

```java
@Component
public class Boss {

    private Car car;

    public Car getCar() {
        return car;
    }

    @Autowired
    //标注在方法上，Spring容器创建当前对象，就会调用方法，完成赋值
    //方法使用的参数，自定义类型的值从ioc容器获取
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
```

```java
@Configuration
@ComponentScan({"com.jolan.com.jolan.controller", "com.jolan.com.jolan.service", "com.jolan.com.jolan.dao", "com.jolan.bean"})
public class MainConfigOfAutowired {
    @Primary
    @Bean("bookDao2")
    public BookDao bookDao(){
        BookDao bookDao = new BookDao();
        bookDao.setLabel("2");
        return bookDao;
    }
}
```

#### 测试运行

```java
public class IOCTest_Autowired {
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfAutowired.class);
    @Test
    public void test01(){
        BookService bookService = (BookService)applicationContext.getBean(BookService.class);
        bookService.print();

//        BookDao bookDao = (BookDao)applicationContext.getBean(BookDao.class);
//        System.out.println(bookDao);
        Boss boss = applicationContext.getBean(Boss.class);
        System.out.println(boss);
        Car car = applicationContext.getBean(Car.class);
        System.out.println(car);
    }

    private void printBeans(AnnotationConfigApplicationContext applicationContext){
        String[] names = applicationContext.getBeanDefinitionNames();
        for (String name : names) {
            System.out.println(name);
        }
    }
}
```

#### 运行结果

​	可以看到Boss中Car成员变量的值和从Spring容器中直接获取的值是一致的。

```java
Boss{car=com.jolan.bean.Car@78b66d36}
com.jolan.bean.Car@78b66d36
```

### Autowired标注在构造器上

​	修改Boss类并运行。去掉set方法上的Autowired注解，增加构造函数并使用Autowired注解。

```java
@Component
public class Boss {

    private Car car;

    //构造器要用的组件，都是从容器中获取
    @Autowired
    public Boss(Car car){
        this.car = car;
        System.out.println("Boss的有参构造器...");
    }

    public Car getCar() {
        return car;
    }

//    @Autowired
    //标注在方法上，Spring容器创建当前对象，就会调用方法，完成赋值
    //方法使用的参数，自定义类型的值从ioc容器获取
    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Boss{" +
                "car=" + car +
                '}';
    }
}
```

​	运行结果:

```java
Boss{car=com.jolan.bean.Car@363ee3a2}
com.jolan.bean.Car@363ee3a2
```

### Autowired标注在参数上

​	改动如下图所示，运行结果一致，此处略过。

```java
public Boss(@Autowired Car car){
    this.car = car;
    System.out.println("Boss的有参构造器...");
}
```

### Autowired和@Bean配合使用

#### 修改Color类

​	将Color类中增加成员变量car。

```java
public class Color {
    public Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    @Override
    public String toString() {
        return "Color{" +
                "car=" + car +
                '}';
    }
}
```

#### MainConfigOfAutowired中增加Colorbean

```java
//部分代码
@Bean
public Color color(Car car){//这里Car前面省略了@Autowired
    Color color = new Color();
    color.setCar(car);
    return color;
}
```

#### 修改测试类

```java
@Test
public void test01(){
    BookService bookService = (BookService)applicationContext.getBean(BookService.class);
    bookService.print();

    //        BookDao bookDao = (BookDao)applicationContext.getBean(BookDao.class);
    //        System.out.println(bookDao);
    Boss boss = applicationContext.getBean(Boss.class);
    System.out.println(boss);
    Car car = applicationContext.getBean(Car.class);
    System.out.println(car);

    Color color = applicationContext.getBean(Color.class);
    System.out.println(color);
}
```

#### 运行结果

​	通过运行结果可知，Boss中的Car和Color中的Car对象是同一个，都是从Spring容器中获取的。

```java
Boss{car=com.jolan.bean.Car@47d90b9e}
com.jolan.bean.Car@47d90b9e
Color{car=com.jolan.bean.Car@47d90b9e}
```



## @Resource

​		可以和@Autowired一样实现自动装配功能，默认是按照组件名称进行装配，也可以用name属性重新指定：@Resource(name="bookDao2")。不支持@Primary等注解。

## @Inject

​		使用@Inject注解需要先导入maven依赖。

```xml
<dependency>
    <groupId>javax.inject</groupId>
    <artifactId>javax.inject</artifactId>
    <version>1</version>
</dependency>
```

## Aware

​	自定义组件实现xxxAware接口，在创建对象的时候，会调用接口规定的方法注入相关的组件。xxxAware接口的父接口是Aware接口。

### 修改Red类

​	让Red类实现ApplicationContextAware、BeanNameAware、EmbeddedValueResolverAware接口，并且使用@Component注解。

```java
package com.jolan.bean;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanNameAware;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.stereotype.Component;
import org.springframework.util.StringValueResolver;

/**
 * @author jolan80
 * @date 2020-08-09 10:23
 */
@Component
public class Red implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    public void setBeanName(String s) {
        System.out.println("当前bean的名字：" + s);
    }

    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
        System.out.println("传入的ioc：" + applicationContext);
    }

    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        String resolveStringValue = stringValueResolver.resolveStringValue("你好${os.name} 我是#{20*18}");
        System.out.println("解析的字符串：" + resolveStringValue);
    }
}

```

### 测试运行

​	通过这几个Aware接口，获取到了Spring容器底层的组件。

```java
当前bean的名字：red
解析的字符串：你好Windows 8.1 我是360
传入的ioc：org.springframework.context.annotation.AnnotationConfigApplicationContext@75828a0f, started on Fri Feb 19 22:58:06 CST 2021
```

# Profile

​	以不同环境切换数据源为例，先给容器注入三个数据源。

## 修改pom文件

​	增加数据源和mysql驱动的坐标依赖。

```xml
<!-- https://mvnrepository.com/artifact/com.mchange/c3p0 -->
<dependency>
    <groupId>com.mchange</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.5.5</version>
</dependency>

<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.23</version>
</dependency>
```



## 创建dbConfig配置文件

​	在resources根目录下创建配置文件。

![avatar](pic/2.png "增加配置文件")

```properties
db.user = root
db.password = 123456
db.driverClass = com.mysql.jdbc.Driver
```



## 创建MainConfigOfProfile配置文件

```java
package com.jolan.config;

import com.mchange.v2.c3p0.ComboPooledDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.beans.factory.config.EmbeddedValueResolver;
import org.springframework.context.EmbeddedValueResolverAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.util.StringValueResolver;

import javax.sql.DataSource;
import java.beans.PropertyVetoException;

/**
 * @author jolan80
 * @date 2021-02-19 23:10
 *
 * Profile:
 *  Spring为我们提供的可以根据当前环境，动态的激活和切换一系列组件的功能；
 *
 *  如开发环境、测试环境、生产环境
 */
@PropertySource("classpath:/dbConfig.properties")
@Configuration
public class MainConfigOfProfile implements EmbeddedValueResolverAware {

    @Value("${db.user}")
    private String user;

    private StringValueResolver stringValueResolver;

    @Bean("devDataSource")
    public DataSource dataSourceDev(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(pwd);
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/dev");
        comboPooledDataSource.setDriverClass(stringValueResolver.resolveStringValue("${db.driverClass}"));
        return comboPooledDataSource;
    }

    @Bean("testDataSource")
    public DataSource dataSourceTest(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(pwd);
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/test");
        comboPooledDataSource.setDriverClass(stringValueResolver.resolveStringValue("${db.driverClass}"));
        return comboPooledDataSource;
    }

    @Bean("prodDataSource")
    public DataSource dataSourceProd(@Value("${db.password}") String pwd) throws PropertyVetoException {
        ComboPooledDataSource comboPooledDataSource = new ComboPooledDataSource();
        comboPooledDataSource.setUser(user);
        comboPooledDataSource.setPassword(pwd);
        comboPooledDataSource.setJdbcUrl("jdbc:mysql://localhost:3306/prod");
        comboPooledDataSource.setDriverClass(stringValueResolver.resolveStringValue("${db.driverClass}"));
        return comboPooledDataSource;
    }

    public void setEmbeddedValueResolver(StringValueResolver stringValueResolver) {
        this.stringValueResolver = stringValueResolver;
    }
}

```

## 新增测试类

```java
package com.jolan.test;

import com.jolan.bean.Boss;
import com.jolan.bean.Car;
import com.jolan.bean.Color;
import com.jolan.com.jolan.service.BookService;
import com.jolan.config.MainConfigOfAutowired;
import com.jolan.config.MainConfigOfProfile;
import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import javax.sql.DataSource;

/**
 * @author jolan80
 * @date 2020-08-15 15:16
 */
public class IOCTest_Profile {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
        String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
        for(String dataSourceName : namesForType){
            System.out.println(dataSourceName);
        }
        applicationContext.close();
    }

}

```

## 运行结果

​	目前容器中有三个数据源

```java
devDataSource
testDataSource
prodDataSource
```

## 激活profile的几种方式

### 使用命令行参数

​	在运行前在命令行参数中增加 -Dspring.profiles.active=test，表示使用test环境。运行结果如下。

```java
testDataSource
```

### 在代码中加载

```java
@Test
public void test02(){
    //1.创建一个applicationContext对象
    AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
    //2.设置需要设置的环境
    applicationContext.getEnvironment().setActiveProfiles("test", "dev");
    //3.注册配置类
    applicationContext.register(MainConfigOfProfile.class);
    //4.启动刷新容器
    applicationContext.refresh();
    String[] namesForType = applicationContext.getBeanNamesForType(DataSource.class);
    for(String dataSourceName : namesForType){
        System.out.println(dataSourceName);
    }
    applicationContext.close();
}
```

​	测试结果如下：加载了test和dev环境的bean。

```java
devDataSource
testDataSource
```

## 其他说明

​	@Profile还可以标注在类上，只有是指定的环境的时候，整个配置类里面的所有配置才能生效。

​	如果要默认加载可以使用@Profile("default")

# AOP

## AOP工作流程

​	1）、传入配置类，创建ioc容器

​	2）、注册配置类，调用refresh()刷新容器

​	3）、registerBeanPostProcessors(beanFactory);注册bean的后置处理器来方便拦截bean的创建

​		①先获取ioc容器中已经定义了需要创建对象的所有BeanPostProcessor

​		②给容器中加别的BeanPostProcessor

​		③优先注册实现了PriorityOrdered接口的BeanPostProcessor

​		④再给容器中注册实现了Ordered接口的BeanPostProcessor

​		⑤注册没实现优先级接口的BeanPostProcessor

​		⑥注册BeanPostProcessor，实际就是创建BeanPostProcessor对象，保存在容器中。

​			创建internalAutoProxyCreator的BeanPostProcessor【AnnotationAwareAspectJAutoProxyCreator】

​			Ⅰ 创建Bean的实例

​			Ⅱ this.populateBean(beanName, mbd, instanceWrapper);//给Bean的各种属性赋值

​			Ⅲ  this.initializeBean(beanName, exposedObject, mbd);//初始化Bean

​				一、this.invokeAwareMethods(beanName, bean);//处理Aware接口的方法回调

​				二、wrappedBean = this.applyBeanPostProcessorsBeforeInitialization(bean, beanName);//应用后置处理器的postProcessBeforeInitialization

​				三、this.invokeInitMethods(beanName, wrappedBean, mbd);//执行自定义的初始化方法

​				四、wrappedBean = this.applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);//执行后置处理器的postProcessAfterInitialization

​			Ⅳ BeanP（AnnotationAwareAspectJAutoProxyCreator）创建成功。

​		⑦把BeanPostProcessor注册到BeanFactory中，beanFactory.addBeanPostProcessor(postProcessor);

​	4）、this.finishBeanFactoryInitialization(beanFactory);//完成BeanFactory初始化工作，创建剩下的单实例Bean

​		①遍历获取容器中所有的Bean，依次创建对象getBean(weaverAwareName);

​			getBean->doGetBean->getSingleton->

​		②创建Bean

​			【AnnotationAwareAspectJAutoProxyCreator在所有bean创建之前会有一个拦截，InstantiationAwareBeanPostProcessor，会调用applyBeanPostProcessorsBeforeInstantiation】

​			Ⅰ 先从缓存中获取当前bean，如果能获取到，说明bean是之前被创建过的，直接使用，否则再创建。只要创建好的Bean都会被缓存起来。

​			Ⅱ createBean()：创建Bean。AnnotationAwareAspectJAutoProxyCreator会在任何Bean创建之前先尝试返回Bean的实例。

​			【BeanPostProcessor是在Bean对象创建完成并初始化前后调用的】

​			【InstantiationAwareBeanPostProcessor是在创建Bean实例之前先尝试用后置处理器返回对象】

​				一、resolveBeforeInstantiation(beanName, mbdToUse);//希望后置处理器在此能返回一个代理对象；如果能返回代理对象就使用，如果不能就继续。

​						第一步：后置处理器先尝试返回对象。

​						applyBeanPostProcessorsBeforeInstantiation(targetType, beanName);//拿到所有后置处理器，如果是InstantiationAwareBeanPostProcessor就执行postProcessBeforeInstantiation

​						applyBeanPostProcessorsAfterInitialization(bean, beanName);

​				二、doCreateBean(beanName, mbdToUse, args);//真正的创建一个Bean实例，和上面的3.6流程一样

## AnnotationAwareAspectJAutoProxyCreator

AnnotationAwareAspectJAutoProxyCreator.postProcessBeforeInstantiation()的作用。

1）在每一个Bean创建之前，调用postProcessBeforeInstantiation。

​	① 判断当前bean是否在advisedBeans中（保存了所有需要增强的bean）

​	② 判断当前bean是否是基础类型：Advice，Pointcut，Advisor，AopInfrastructureBean

​		或者是否是切面（@Aspect）

​	③ 是否需要跳过：

​		Ⅰ 获取候选的增强器（切面中的通知方法）【List<Advisor> advisors】

​			每一个封装的通知方法的增强器是InstantiationModelAwarePointcutAdvisor;

​			判断每一个增前期是否是AspectJPointcutAdvisor，如果是返回true

​		Ⅱ 永远返回false

2） 创建对象之后调用：postProcessAfterInstantiation

​		return this.wrapIfNecessary(bean, beanName, cacheKey);//包装如果需要的情况下

​		① 获取当前bean的所有增强器（通知方法）Object []  = specificInterceptors

​			Ⅰ 找到候选的所有增强器（哪些通知方法是需要切入当前bean的方法）

​			Ⅱ 获取到能在bean使用的增强器

​			Ⅲ 给增强器排序

​		② 保存当前bean在advisedBeans中。

​		③ 如果当前bean需要增强，创建当前bean的代理对象

​			Ⅰ 获取所有增前期（通知方法）

​			Ⅱ 保存到proxyFactory中

​			Ⅲ 创建代理对象：Spring自动决定

​				JdkDynamicAopProxy：jdk动态代理

​				CglibAopProxy：cglib动态代理

​		④ 给容器中返回当前组件使用cglib增强了的代理对象

​		⑤ 以后容器中获取到的就是这个组件的代理对象，执行目标方法的时候，代理对象就会执行通知方法的流程

​	3） 目标方法执行：容器中保存了组件的代理对象（cglib增强后的对象），这个对象里面保存了详细信息（比如增强器、目标对象等等）

​		① CglibAopProxy.intercept()拦截目标方法的执行

​		② 根据ProxyFactory获取将要执行的目标方法的拦截器链

​			List<Object> chain = this.advised.getInterceptorsAndDynamicInterceptionAdvice(method, targetClass);

​			Ⅰ List<Object> interceptorList = new ArrayList(advisors.length);保存所有拦截器。其中advisors.length的长度是5。一个默认的ExposeInvocationInterceptor和4个自定义的增强器。

​			Ⅱ  遍历所有的增强器，将其转为Interceptor[] interceptors = registry.getInterceptors(advisor);

​			Ⅲ 将增强器转为List<MethodInterceptor>

​				 如果是MethodInterceptor，直接加入到集合中

​				 如果不是，使用AdvisorAdapter将增强器转为MethodInterceptor

​				 转换完成返回MethodInterceptor数组

​		③ 如果没有拦截器链，直接执行目标方法

​			 拦截器链（每一个通知方法又被包装为方法拦截器，利用MethodInterceptor机制）

​		④ 如果有拦截器链，把需要执行的目标对象，目标方法，拦截器链等所有信息传入创建一个CglibMethodInvocation对象，并调用他的proceed()方法，其返回值为Object.

​		⑤ 拦截器链的触发过程

​				Ⅰ 如果没有拦截器，直接执行目标方法，或者说拦截器的索引和拦截器数组-1大小一样（执行到了最后一个拦截器）执行目标方法。

​				Ⅱ 链式获取每一个拦截器，拦截器执行invoke方法，每一个拦截器等待下一个拦截器执行完成返回以后再来执行。

​					 拦截器链的机制，保证通知方法和目标方法的执行顺序。

## AOP总结

​	1） @EnableAspectJAutoProxy开启AOP功能

​	2） @EnableAspectJAutoProxy会给容器注册一个组件AnnotationAwareAspectJAutoProxyCreator

​	3） AnnotationAwareAspectJAutoProxyCreator是一个后置处理器

​	4） 容器的创建流程

​		① registerBeanPostProcessors()注册后置处理器，创建AnnotationAwareAspectJAutoProxyCreator对象。

​		② finishBeanFactoryInitialization()初始化剩下的单实例bean

​			Ⅰ 创建业务逻辑组件和切面组件。

​			Ⅱ AnnotationAwareAspectJAutoProxyCreator拦截组件的创建过程

​			Ⅲ 组件创建完之后，判断组件是否需要增强。如果是，切面通知方法保证成增强器（Advisor），给业务逻辑组件创建一个代理对象（cglib）。

​	5） 执行目标方法。

​		① 即代理对象执行方法。

​		② CglibAopProxy.intercept()

​			Ⅰ 得到目标方法的拦截器链（增强器包装成拦截器MethodInterceptor）

​			Ⅱ 利用拦截器的链式机制，依次进入每一个拦截器进行执行

​			Ⅲ 效果：

​				 正常执行：前置通知->目标方法->后置通知->返回通知

​				 异常执行：前置通知->目标方法->后置通知->异常通知

# 声明式事物

## 环境搭建

### 导入相关依赖

​	数据源、数据库驱动、springjdbc模块。

```xml
<dependency>
    <groupId>org.springframework</groupId>
    <artifactId>spring-jdbc</artifactId>
    <version>5.2.6.RELEASE</version>
</dependency>

<dependency>
    <groupId>com.mchange</groupId>
    <artifactId>c3p0</artifactId>
    <version>0.9.5.5</version>
</dependency>

<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>8.0.23</version>
</dependency>

```

### 配置数据源

​	新建TxConfig配置类。

```java
@ComponentScan("com.jolan.tx")
@Configuration
public class TxConfig {

    //数据源
    @Bean
    public DataSource dataSource() throws Exception{
        ComboPooledDataSource datasource = new ComboPooledDataSource();
        datasource.setUser("xxx");
        datasource.setPassword("xxx");
        datasource.setDriverClass("com.mysql.jdbc.Driver");
        datasource.setJdbcUrl("jdbc:mysql://xxx:3306/test");
        return datasource;
    }


    @Bean
    public JdbcTemplate jdbcTemplate() throws Exception{
        //Spring对@Configuration类会特殊处理：给容器中加组件的方法，多次调用都只是从容器中找组件
        JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
        return jdbcTemplate;
    }
}
```

### 新建dao层操作数据库

​	新建UserDao类

```java
@Repository
public class UserDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(){
        String sql = "insert into tbl_user(username, age) values(?, ?)";
        String userName = UUID.randomUUID().toString().substring(0, 5);
        jdbcTemplate.update(sql, userName, 19);
    }
}
```

### 新建service层调用dao层

```java
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    public void inserUser(){
        userDao.insert();
        //otherDao.otherMethod()
        System.out.println("插入完成");
        int i = 10/0;
    }

}
```

### 新建测试类

​	在上面的UserService类中，在执行数据库插入操作之后，执行了除数为0的除法操作，数据库并没有像我们期待的一样进行回归，仍然正常的插入了一条数据。

```java
public class IOCTest_Tx {

    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(TxConfig.class);
        UserService userService = applicationContext.getBean(UserService.class);
        userService.inserUser();
        applicationContext.close();
    }
}
```

### 运行结果

```java
插入完成

java.lang.ArithmeticException: / by zero

	at com.jolan.tx.UserService.inserUser(UserService.java:20)
	at com.jolan.test.IOCTest_Tx.test01(IOCTest_Tx.java:20)
	at sun.reflect.NativeMethodAccessorImpl.invoke0(Native Method)
	at sun.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:62)
	at sun.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)
	at java.lang.reflect.Method.invoke(Method.java:498)
	at org.junit.runners.model.FrameworkMethod$1.runReflectiveCall(FrameworkMethod.java:50)
	at org.junit.internal.runners.model.ReflectiveCallable.run(ReflectiveCallable.java:12)
	at org.junit.runners.model.FrameworkMethod.invokeExplosively(FrameworkMethod.java:47)
	at org.junit.internal.runners.statements.InvokeMethod.evaluate(InvokeMethod.java:17)
	at org.junit.runners.ParentRunner.runLeaf(ParentRunner.java:325)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:78)
	at org.junit.runners.BlockJUnit4ClassRunner.runChild(BlockJUnit4ClassRunner.java:57)
	at org.junit.runners.ParentRunner$3.run(ParentRunner.java:290)
	at org.junit.runners.ParentRunner$1.schedule(ParentRunner.java:71)
	at org.junit.runners.ParentRunner.runChildren(ParentRunner.java:288)
	at org.junit.runners.ParentRunner.access$000(ParentRunner.java:58)
	at org.junit.runners.ParentRunner$2.evaluate(ParentRunner.java:268)
	at org.junit.runners.ParentRunner.run(ParentRunner.java:363)
	at org.junit.runner.JUnitCore.run(JUnitCore.java:137)
	at com.intellij.junit4.JUnit4IdeaTestRunner.startRunnerWithArgs(JUnit4IdeaTestRunner.java:68)
	at com.intellij.rt.junit.IdeaTestRunner$Repeater.startRunnerWithArgs(IdeaTestRunner.java:33)
	at com.intellij.rt.junit.JUnitStarter.prepareStreamsAndStart(JUnitStarter.java:230)
	at com.intellij.rt.junit.JUnitStarter.main(JUnitStarter.java:58)
```

数据库结果为：1	fc6b1	19

## @Transactional

​	在UserService类上增加@Transactional注解。再次运行事物没有生效。

```java
@Transactional
public void inserUser(){
    userDao.insert();
    //otherDao.otherMethod()
    System.out.println("插入完成");
    int i = 10/0;
}
```

## @EnableTransactionManagement

​	@Transactional没有生效是因为没有开启声明式事物，需要在配置类上增加@EnableTransactionManagement标签。

```java
@EnableTransactionManagement
@ComponentScan("com.jolan.tx")
@Configuration
public class TxConfig 
```

## PlatformTransactionManager

​	增加开启注解后运行测试类报错，是因为缺少事物管理器管理事物，增加事物管理器Bean，在配置类中增加配置。以上配置完成后再运行测试类型，数据库中将不会插入新的记录。

```java
@Bean
public PlatformTransactionManager transactionManager() throws Exception{
    return new DataSourceTransactionManager(dataSource());
}
```

## @EnableTransactionManagement工作原理		

​	1） @EnableTransactionManagement利用TransactionManagementConfigurationSelector给容器中导入组件

​	2） 导入两个组件

​		① AutoProxyRegistrar

​		② ProxyTransactionManagementConfiguration

​	3） AutoProxyRegistrar：给容器中注册一个InfrastructureAdvisorAutoProxyCreator组件

​		  利用后置处理器机制，在对象创建以后，包装对象成一个代理对象（增强器），代理对象执行方法利用拦截器链进行调用

​	4）ProxyTransactionManagementConfiguration

​		① 给容器中注册事物增强器。事物增强器要用事物注解的信息，AnnotationTransactionAttributeSource解析事物注解

​		② 事物拦截器TransactionInterceptor：保存了事物的属性信息，和事物管理器。他是一个MethodInterceptor。

​			 在目标方法执行的时候，执行拦截器链。事物拦截器流程如下。

​			Ⅰ 获取事物属性

​			Ⅱ 再获取PlantformTranscationManager。如果事先没有添加指定任何TranscationManager，最终会从容器中按照类型获取一个PlantformTranscationManager。

​			Ⅲ 执行目标方法，如果一场，获取到事物管理器，利用事物管理器回滚这次操作。如果正常，利用事物管理器提交事物。

# BeanFactoryPostProcessor

​	和BeanPostProcessor是bean的后置处理器类似，BeanFactoryPostProcessor是BeanFactory的后置处理器。其执行时机是在BeanFactory标准初始化之后调用，即：所有的bean定义已经保存加载到BeanFactory，但是Bean的实例还没有创建。

## 执行说明

​	1）ioc容器创建对象。

​	2）invokeBeanFactoryPostProcessors(beanFactory);执行BeanFactoryPostProcessor

​		如何找到所有的BeanFactoryPostProcessor并执行他们的方法，分下面两个步骤。

​		① 直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并且执行他们的方法

​		② 在初始化创建其他组件前面执行

## 创建配置类ExtConfig

​	创建一个配置类，在配置类中注册一个Blue类型的bean。

```java
@Configuration
@ComponentScan("com.jolan.ext")
public class ExtConfig {

    @Bean
    public Blue blue(){
        return new Blue();
    }
}
```

## 创建MyBeanFactoryPostProcessor

​	创建MyBeanFactoryPostProcessor，实现BeanFactoryPostProcessor。

```java
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {


    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanFactoryPostProcessor...postProcessBeanFactory...");
        int count = configurableListableBeanFactory.getBeanPostProcessorCount();
        String[] names = configurableListableBeanFactory.getBeanDefinitionNames();
        System.out.println("当前BeanFactory中有"+count+"个Bean");
        System.out.println(Arrays.asList(names));
    }
}
```

## 创建测试类并运行

```java
public class IOCTest_Ext {

    @SuppressWarnings("resource")
    @Test
    public void test01(){
        AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);

        applicationContext.close();
    }


}
```

​	运行结果如下。可以看到MyBeanFactoryPostProcessor是在Bean实例化之前运行的，并且可以获取到Bean的定义信息。

```java
MyBeanFactoryPostProcessor...postProcessBeanFactory...
当前BeanFactory中有3个Bean
[org.springframework.context.annotation.internalConfigurationAnnotationProcessor, org.springframework.context.annotation.internalAutowiredAnnotationProcessor, org.springframework.context.annotation.internalCommonAnnotationProcessor, org.springframework.context.event.internalEventListenerProcessor, org.springframework.context.event.internalEventListenerFactory, extConfig, myBeanFactoryPostProcessor, blue]
```

# BeanDefinitionRegistryPostProcessor

​	BeanDefinitionRegistryPostProcessor是在所有bean定义信息将要被加载，bean实例还未创建时运行。优先于BeanFactoryPostProcessor执行，利用BeanDefinitionRegistryPostProcessor给容器中再添加一些组件。它继承了BeanFactoryPostProcessor。

## 创建MyBeanDefinitionRegistryPostProcessor

​	MyBeanDefinitionRegistryPostProcessor实现了BeanDefinitionRegistryPostProcessor，它有两个需要实现的方法postProcessBeanDefinitionRegistry是来自于BeanDefinitionRegistryPostProcessor，postProcessBeanFactory是来自于BeanDefinitionRegistryPostProcessor继承的BeanFactoryPostProcessor。

​	new RootBeanDefinition(Blue.class)和BeanDefinitionBuilder.rootBeanDefinition(Blue.class).getBeanDefinition()都是创建bean定义的一种方法。

```java
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    //BeanDefinitionRegistry Bean定义信息的保存中心，以后的BeanFactory就是按照BeanDefinitionRegistry里面保存的每一个ban定义信息创建bean实例的。
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry beanDefinitionRegistry) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor...postProcessBeanDefinitionRegistry...bean的数量:" + beanDefinitionRegistry.getBeanDefinitionCount());
        //下面两种方法都可以创建一个bean定义信息
        RootBeanDefinition beanDefinition = new RootBeanDefinition(Blue.class);
        AbstractBeanDefinition beanDefinition1 = BeanDefinitionBuilder.rootBeanDefinition(Blue.class).getBeanDefinition();
        beanDefinitionRegistry.registerBeanDefinition("hello", beanDefinition);
    }

    public void postProcessBeanFactory(ConfigurableListableBeanFactory configurableListableBeanFactory) throws BeansException {
        System.out.println("MyBeanDefinitionRegistryPostProcessor...bean的数量:" + configurableListableBeanFactory.getBeanDefinitionCount());
    }
}
```

## 运行

​	测试类和上一章节相同，运行结果如下。可以看到postProcessBeanDefinitionRegistry的方法优先于postProcessBeanFactory执行。在后者实行时，bean的数量增加了一个，是在postProcessBeanDefinitionRegistry中刚刚创建的bean。

```java
MyBeanDefinitionRegistryPostProcessor...postProcessBeanDefinitionRegistry...bean的数量:9
MyBeanDefinitionRegistryPostProcessor...bean的数量:10
MyBeanFactoryPostProcessor...postProcessBeanFactory...
当前BeanFactory中有3个Bean
[org.springframework.context.annotation.internalConfigurationAnnotationProcessor, org.springframework.context.annotation.internalAutowiredAnnotationProcessor, org.springframework.context.annotation.internalCommonAnnotationProcessor, org.springframework.context.event.internalEventListenerProcessor, org.springframework.context.event.internalEventListenerFactory, extConfig, myBeanDefinitionRegistryPostProcessor, myBeanFactoryPostProcessor, blue, hello]

Process finished with exit code 0

```

## 工作原理（执行顺序）

​	1） IOC创建对象

​	2） 执行refresh()方法，然后执行该方法中的invokeBeanFactoryPostProcessors

​	3） invokeBeanFactoryPostProcessors会从容器中获取得所有的BeanDefinitionRegistryPostProcessor组件

​		① 依次触发所有的postProcessBeanDefinitionRegistry()方法

​		② 再来触发postProcessBeanFactory()方法的BeanFactoryPostProcessor

​	4） 再从容器中找到BeanFactoryPostProcessor组件，然后依次触发postProcessFactory()方法（这个就是上面的BeanFactoryPostProcessor组件的功能，它的执行在BeanDefinitionRegistryPostProcessor之后）

