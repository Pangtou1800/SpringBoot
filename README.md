# SpringBoot

## 第一节 SpringBoot入门

### 1.1 简介

    SpringBoot是Spring团队伴随Spring 4.0在2014年开发的。

    SpringBoot用来简化Spring应用开发，约定大于配置，去繁从简，JustRun就创建一个独立的企业级开发。

    Spring全家桶时代
        -> SpringBoot：J2EE一站式解决方案
        -> SpringCloud：分布式整体解决方案

    ·优点：
        -快速创建独立运行的Spring项目以及主流框架集成
        -使用嵌入式Servlet容器，应用无需打成war包
        -starters自动依赖与版本控制
        -大量的自动配置，简化开发，当然值也可以修改
        -无需配置xml，无代码生成，开箱即用
        -准生产环境的运行时应用监控
        -与云计算的天然集成

    ·缺点：
        -入门容易，精通难
        -基于Spring构建，要求对Spring底层API有深入了解才能深度定制

### 1.2 微服务

    2014年，Martin Flower发表了一篇关于微服务的文章。

    传统的应用：All-in-One
        +开发测试简单
        +部署运维简单
        +容易水平扩展
        -紧耦合
        -大型项目难以维护

    微服务是一种架构风格，提倡
        “一个应用应该是一组小型服务，每一个服务都应用在自己的进程内。
        服务之间可以通过Http的方式进行互通。”
    每一个功能元素最终都是可以独立替换、可以独立升级的软件单元。

[详细参见微服务文档](https://insights.thoughtworks.cn/microservices-martin-fowler/)

    <?xml version="1.0"?>
    <settings>
        <mirrors>
            <mirror>
                <id>aliyunmaven</id>
                <mirrorOf>*</mirrorOf>
                <name>阿里云公共仓库</name>
                <url>https://maven.aliyun.com/repository/public</url>
            </mirror>
        </mirrors>
    </settings>

    <profiles>
        <profile>
            <id>jdk-1.8
            <activation>
                <activeByDefault>true
                <jdk>1.8
            <properties>
                <maven.compiler.source>1.8
                <maven.compiler.target>1.8
                <maven.compiler.compilerVersion>1.8

### 1.3 SpringBoot HelloWorld

    1.创建一个Maven工程

    2.导入SpringBoot相关依赖

    3.编写一个主程序，启动SpringBoot应用

        @SpringBootApplication
        public class BootStrap {
            public static void main(String[] args) {
                // 启动Spring应用
                SpringApplication.run(BootStrap.class, args);
            }
        }

    4.编写Controller

        @Controller
        public class HelloController {

            @ResponseBody
            @RequestMapping("/hello")
            public String hello() {
                return "Welcome to Joja Market!";
            }
        }

    5.运行Bootstrap.main，访问localhost:8080/hello即可

    6.简化部署工作

        <build>
            <plugins>
                <plugin>
                    <groupId>org.springframework.boot</groupId>
                    <artifactId>spring-boot-maven-plugin</artifactId>
                </plugin>
            </plugins>
        </build>

        执行Maven - build，打好的jar包直接 java -jar jar包 运行即可

### 1.4 HelloWorld详细

    1.POM文件
        ·父项目
            直接父项目还有一个父项目

            <parent>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-dependencies</artifactId>
                <version>2.2.5.RELEASE</version>
                <relativePath>../../spring-boot-dependencies</relativePath>
            </parent>

            <properties>
                ..
                定义了很多Jar包的版本 - 管理SpringBoot所有依赖的版本，版本仲裁中心
                -以后导入依赖基本上不需要指定版本

    2.导入的依赖 - 启动器
        ·spring-boot-starter-web
            SpringBootStarter - 场景启动器
            导入了web模块正常启动需要的组件

        ·spring-boot-starter有各种情景用的整合
            将所有的功能场景抽取出来，做成了很多的starters（启动器）
            只要在项目中引入这些starter，所有依赖都会自动导入
            需要功能时加入启动器的启动器即可

    3.主程序类/主入口类

        @SpringBootApplication标注在某个类上，指定它时SpringBoot的主配置类
        SpringBoot就运行这个类的main方法来启动SpringBoot应用

        它是一个组合注解：

        @Target(ElementType.TYPE)
        @Retention(RetentionPolicy.RUNTIME)
        @Documented
        @Inherited
        @SpringBootConfiguration
        @EnableAutoConfiguration
        @ComponentScan(excludeFilters = { @Filter(type = FilterType.CUSTOM, classes = TypeExcludeFilter.class),
                @Filter(type = FilterType.CUSTOM, classes = AutoConfigurationExcludeFilter.class) })
        public @interface SpringBootApplication {

        1.@SpringBootConfiguration - SpringBoot配置类
            -表示这是一个SpringBoot的配置类
            -@Configuration配置类上来标注这个注解

                配置文件 <-> 配置类

                配置类也是容器中的一个组件 @Component

        2.@EnableAutoConfiguration - 启用自动配置功能

            @AutoConfigurationPackage
            @Import(AutoConfigurationImportSelector.class)
            public @interface EnableAutoConfiguration {

            @AutoConfigurationPackage - 自动配置包

                @Import(AutoConfigurationPackages.Registrar.class)
                public @interface AutoConfigurationPackage {

                Spring底层注解@Import，给容器中导入一个组件；
                导入的组件由AutoConfigurationPackages.Register.class
                将主配置类（@SpringBootApplication标注的类）的所在包及以下所有子包
                里的组件扫描道Spring容器中

            @Import(AutoConfigurationImportSelector.class) - 导入哪些组件的选择器

                将所有需要导入的组件以全类名的方式返回，
                这些组件就会被添加道容器中

                会给容器中导入非常多的自动配置类（xxxAutoConfiguration）
                就是场景需要的组件的配置类了

                有了自动配置类，免去了手动编写配置注入功能组件等的工作

                    SpringFactoriesLoader.loadFactoryNames(
                        EnabelAutoConfiguration.class,
                        ClassLoader
                    )
                
            总结：
               SpringBoot启动时，从META-INF/spring.factories中获取指定的值，
               并将这些值作为自动配置类导入容器中。自动配置类生效后就会进行自动配置工作。需要自己指定的配置，自动配置类都替代完成了。

               关注：spring-boot-autoconfigure.jar包里各种组件的配置方式

    4.一些默认文件结构
        -resources
            -static : 静态资源
            -templates : HTML模板资源
            application.properties

## 第二节 SpringBoot配置

### 2.1 全局配置文件

    类路径下的
        ·application.properties
        ·application.yml
      会被Spring识别为全局配置文件

        server:
            port: 8081

    作用：
        修改SpringBoot自动配置的默认值

### 2.2 YAML语法

    .yml - YAML (YAML Ain't Markup Language)，以数据为中心，比json和xml更适合做配置

    1.基本语法

        k:(空格)v - 键值对

        层级关系也用空格缩进来表示，左对齐的列为同一层级

        server:
            port: 8081
            path: /hello

        ※属性和值大小写敏感

    2.值的写法
        ·字面量：普通值（数字、字符串、布尔）
            直接书写，不需要加'或"
            ":不会转义字符串的特殊字符
            ':会转义特殊字符
            key: value

            name: "张三\n李四"
                => 张三
                   李四
            
            name: '张三\n李四'
                => 张三\n李四

        ·对象/Map：键值对
            key:
                v_key1: v_value1
                v_key2: v_value2
                ..

            friend: 
                lastName: 张三
                age: 20

            也有行内写法：
                friend: {lastName: 张三, age: 20}

        ·数组/List/Set
            用- 值表示数组的元素
                pets:
                    - cat
                    - dog
                    - pig
            
            也支持行内写法
                pets: [cat,dog,pig]

### 2.3 Spring自动注入装配文件值

        ·application.yml
            person:
                lastName: 张三
                age: 18
                boss: false
                birthday: 2017/12/12
                maps: {key1: value1, key2: value2}
                lists:
                    - 李四
                    - 王五
                    - 小花
                    - 赵六
                dog:
                    name: Doggie
                    age: 2

        ·pom.xml
            <dependency>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-configuration-processor</artifactId>
                <optional>true</optional>
            </dependency>

        ·Person.java
            @Component
            @ConfigurationProperties(prefix = "person")
            public class Person {

                或者为每个域添加注解
                @Value("${person.lastName}")
                private String lastName;

                同样支持字面量/${}/#{SpEL}

||@ConfigurationProperties|@Value|
|---|---|---|
|功能|批量注入|个别指定|
|松散绑定|支持|不支持|
|SpEL|不支持|支持|
|JSR303校验|支持|不支持|
|复杂类型封装|支持|不支持|

    ※松散绑定：
        Person - lastName
            =>  person.lastName
            =>  person.last_name
            =>  person.last-name
            =>  PERSON_LAST_NAME

### 2.4 @PropertySource & @ImportResource

    1. @ConfigurationProperties默认从全局配置文件获取值

    从指定配置文件获取值：
    @PropertySource("classpath:person.properties")

    2. 导入额外的配置文件并使其内容生效
    @ImportResource(locations={"classpath:beans.xml"})

    ※可以就添加在主类上

    SpringBoot推荐的方式：
        ·写一个配置类
            @Configuration
            public class MyAppConfig {
        ·写注入方法 - id:方法名，val：返回值
            @Bean
            public HelloService helloService() {

### 2.5 配置文件占位符

    ·用占位符来获取随机数
        id=ID${random.uuid}

        ${random.value} ${random.int} ${random.long}
        ${random.int(10)} ${random.int[1024,65536]}

    ·用占位符来获取其他定义过的变量 :默认值
        app.name=JojaMarket
        app.welcome=Welcom to ${app.name:HelloProj}

### 2.6 Profile多环境

    1.多Profile文件
        主配置文件采用以下：
            application-{profile}.properties/.yml

            ※默认使用application.properties

            application.properties写以下即可激活环境
                spring.profiles.active=dev

    2.yml文档块
```yml
    server:
        port: 8081
    spring:
        profiles:
            active: prod
    ---
    server:
        port: 8084
    spring:
        profiles: dev
    ---
    server:
        port: 8085
    spring:
        profiles: prod
        ---
```

    3.命令行
        启动参数加上：
            --spring.profiles.active=dev

        ※会覆盖文件中的指定激活内容

    4.虚拟机参数
        虚拟机参数加上：
            -Dspring.profiles.active=dev

        ※会覆盖文件中的指定激活内容

### 2.7 默认配置文件路径

    1.四个默认文件路径
        ·file:./config/
        ↓file:./
        ↓classpath:/config/
        ↓classpath:/

        优先级从高到低采用配置内容

        JojaMarket例
        -config/application.properties
        -application.properties
        -bin/config/application.properties
        -bin/application.properties

    2.互补配置
        file:./config/
            -server.port=8084
        classpath:/
            -server.servlet.context-path=/boot02

    3.通过spring.config.location来指定配置文件的位置
        项目打包好以后，通过命令行参数来指定
        它会和包内的配置文件互补生效

        ※命令行参数指定的配置文件优先级最高

### 2.8 外部配置的加载顺序

    1.命令行参数直接指定 ☆
    2.来自java:comp/env的JNDI属性
    3.Java系统属性(System.getProperties())
    4.操作系统环境变量
    5.RandomValuePropertySource配置的random.*属性
    6.jar包外部的application-{profile}.properties/yml ☆
    7.jar包内部的application-{profile}.properties/yml ☆
    8.jar包外部的application.properties/yml ☆
    9.jar包内部的application.properties/yml ☆
    10.@Configuration注解类的@PropertySource
    11.SpringApplication.setDefaultProperties指定的默认属性

    ※ 6~9：由profile指定时profile配置优先

### 2.9 自动配置原理

    首先，可以查看官方文档

    自动配置原理详细：
        1.SpringBoot启动时，加载主配置类，会开启自动配置功能
            @EnableAutoConfiguration
        2.它利用EnableAutoConfigurationImportSelector给容器中导入组件
        3.selectImports()方法获取候选配置
            List<String> configurations=
                getCandidateConfigurations(annotationMetadata, attributes);
            ·SpringFactoriesLoader.loadFactoryNames()
                扫描所有jar包类路径下META-INF/spring.factories
            ·把扫描到的文件内容包装为properties对象
            ·从properties中获取EnableAutoConfiguration.class类对应的值，
             然后把它们添加到容器中

org.springframework.boot.autoconfigure.aop.AopAutoConfiguration,\
org.springframework.boot.autoconfigure.batch.BatchAutoConfiguration,\
...

    例：
        HttpEncodingAutoConfiguration

        @Configuration(proxyBeanMethods = false)
            -标记配置类
        @EnableConfigurationProperties(HttpProperties.class)
            -启用ConfigurationProperties功能，
            并把HttpProperties.class的实例注入容器，并关联到当前类
        @ConditionalOnWebApplication(type = ConditionalOnWebApplication.Type.SERVLET)
            -根据不同条件自动判读，满足条件后配置才会生效
                例中：判断当前应用是否web应用
        @ConditionalOnClass(CharacterEncodingFilter.class)
            -判断当前项目jar包中有没有CharacterEncodingFilter
        @ConditionalOnProperty(prefix = "spring.http.encoding", value = "enabled", matchIfMissing = true)
            -判断配置文件中有没有某个配置
        public class HttpEncodingAutoConfiguration {
            -外面的判断生效之后，执行类中的注入方法

                @Bean
                @ConditionalOnMissingBean
                public CharacterEncodingFilter characterEncodingFilter() {
                    -从关联好的HttpProperties中取得配置属性

        @ConfigurationProperties(prefix = "spring.http")
            -从配置文件中获取配置值的类
        public class HttpProperties {

            ※所有可以配置的属性都是在xxxProperties类中封装的属性

        总结：
            1.SpringBoot启动会加载大量配置类
            2.寻找配置对象属性的自动配置类
            3.寻找自动配置类中配置的属性
            4.给容器中自动配置类添加组件的时候，会从properties中获取属性，
              这些属性就是可以在配置文件中指定的

    ※在配置文件中配置debug=true，可以观察哪些自动配置类生效了

        ============================
        CONDITIONS EVALUATION REPORT
        ============================

        Positive matches:
        -----------------

        DispatcherServletAutoConfiguration matched:
        - @ConditionalOnClass found required class 'org.springframework.web.servlet.DispatcherServlet' (OnClassCondition)
        - found 'session' scope (OnWebApplicationCondition)

        ...

        Negative matches:
        -----------------

        ActiveMQAutoConfiguration:
        Did not match:
         - @ConditionalOnClass did not find required class 'javax.jms.ConnectionFactory' (OnClassCondition)

## 第三节 日志

### 3.1 常见的日志框架

|日志门面|日志实现|
|---|---|
|JCL (Jakarta Commons Logging)|Log4j<br>JUL (java.util.logging)|
|SLF4j (Simple Logging Facade for Java)|Log4j2<br>Logback|
|jboss-logging||

    JCL ×
    jboss-loggin ×

    常用的日志门面 - 日志实现：
        SFL4j - Logback

    SpringBoot: 底层是Spring框架，Spring框架默认使用JCL门面。
        SpringBoot进行包装之后使用了SLF4j - Logback

### 3.2 SFL4j使用

    1.在系统中使用SLF4j

        在开发中输出日志时，不应该直接调用日志的实现类，而是要调用抽象方法

        ·导入slf4j.jar和logback.jar
        ·Logger logger = LoggerFactory.getLogger(HelloWorld.class)

        使用非直接实现包时，可以加入中间适配包来使用

    2.遗留问题

        PJ：slf4j+logback
            -Spring commons-logging
            -Hibernate jboss-logging
            -MyBatis ...
            ...

        如何统一日志记录？
            -> 用框架转换包替代原生Log包

                commons-logging -> jcl-over-slf4j
                log4j -> log4j-over-slf4j
                jul -> jul-to-slf4j

        比如说：

            application
                |-------commons logging------java.util.logging
                |           ↓                       ↓
                |       jcl-over-slf4j.jar      jul-to-slf4j.jar
                |           |                       |
            slf4j-api.jar----------------------------
                |
            slf4j-log412.jar (adaption)
                |
            log4j.jar

        就可以将不同框架的LOG编写方式整合到SLF4J接口，并且底层由log4j来实现

    3.SpringBoot日志关系

```xml
    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter</artifactId>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-logging</artifactId>
    </dependency>
```
        -> logback-classic -> logback-core
        -> jul-to-slf4j     --↘  
        -> log4j-over-slf4j ---|--> slf4j-api
        -> jcl-over-slf4j   --↗

        总结：
            1.SpringBoot底层使用slf4j+logback的方式
            2.SpringBoot把其他的log门面都转换成了slf4j
            3.如果引入其他框架，一定要把这个框架的默认日志依赖移除掉

    4.SpringBoot使用

        > 默认配置
```java
    Logger logger = LoggerFactory.getLogger(getClass());

    @Test
    public void testLog() {
        // 日志的级别由低到高：
        // trace -> debug -> info -> warn -> error
        // 可以调整需要输出的日志级别，只打印指定以上级别的信息
        logger.trace("这是trace日志...");
        logger.debug("这是debug日志...");
        // SpringBoot默认指定info级别
        logger.info("这是info日志...");
        logger.warn("这是warn日志...");
        logger.error("这是error日志...");
    }
```
        > yml配置：
```yml
logging:
  level:
    pt.joja.SpringBootLogTest: trace
```

        > 常规设置：

|logging.file|logging.path|example|description|
|---|---|---|---|
|(none)|(none)||console|
|fileName|(none)|my.log|输出到my.log|
|(none)|directory|/var/log|输出到该目录的spring.log|

    ※file和path是冲突的，file优先

    可以设置输出日志的格式：
      logging.pattern.console=%d{yyyy-MM-dd} [%thread] %-5level %logger{50} - %msg%n
      logging.pattern.file

    想要定制时，只要在类路径下加入原生日志框架的配置文件就可以了

    高级特性：
        经由SpringBoot启动的日志框架配置文件可以加入
            <springProfile name="prod">
                <pattern>....
        来切换不同环境的日志输出

        ※logback.xml -> logback-spring.xml来阻止logback框架自动识别配置文件

### 3.3 日志框架切换

    按照slf4j的日志适配图进行相关切换 - 更换jar包即可，无需额外指定配置

    例：换成log4j实现

        1.去除log4j转SLF4j的依赖
        2.加入slf4j转log4j的适配层包

    例：切换log4j2

        因为SpringBoot准备了相应的starter，所以切换starter即可

        1.排除默认的spring-boot-starter-logging依赖
        2.加上spring-boot-starter-log4j2依赖

    ## TODO 28

## 第四节 Web开发

    4.1 使用步骤：
        ·在创建界面选择想要的模块
        ·创建工程之后就自动导入了模块的依赖
        ·在配置文件中指定少量必须的配置就可以运行起来
        ·编写业务逻辑代码即可

        难点：
            每一个场景中，SpringBoot自动配置了哪些属性？
            那些属性必须指定，哪些属性可以修改？

            -> 观察xxxAutoConfiguration, xxxProperties来获取相关内容

    4.2 SpringBoot对静态资源的映射规则

        关注WebMvcAutoConfiguration

```java
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        if (!this.resourceProperties.isAddMappings()) {
            logger.debug("Default resource handling disabled");
            return;
        }
        Duration cachePeriod = this.resourceProperties.getCache().getPeriod();
        CacheControl cacheControl = this.resourceProperties.getCache().getCachecontrol().toHttpCacheControl();
        if (!registry.hasMappingForPattern("/webjars/**")) {
            customizeResourceHandlerRegistration(registry.addResourceHandler("/webjars/**")
                    .addResourceLocations("classpath:/META-INF/resources/webjars/")
                    .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
        }
        String staticPathPattern = this.mvcProperties.getStaticPathPattern();
        if (!registry.hasMappingForPattern(staticPathPattern)) {
            customizeResourceHandlerRegistration(registry.addResourceHandler(staticPathPattern)
                    .addResourceLocations(getResourceLocations(this.resourceProperties.getStaticLocations()))
                    .setCachePeriod(getSeconds(cachePeriod)).setCacheControl(cacheControl));
        }
    }

    @ConfigurationProperties(prefix = "spring.resources", ignoreUnknownFields = false)
    public class ResourceProperties {
```
        1./webjars/下的所有请求都去classpath:/META-INF/resources/webjars/寻找

            如：http://localhost:8080/webjars/jquery/3.5.1/jquery.js
        
            ※webjars：以jar包的方式引入工程资源
[webjars官网](https://www.webjars.org/)

        2.spring.resources设置和静态资源有关的参数
            ·缓存时间

        3.访问当前项目的任何资源/**，没有其他响应时就访问静态资源文件夹
            ·classpath:/META-INF/resources/
            ·classpath:/resources/
            ·classpath:/static/
            ·classpath:/public/
            ·/ - 当前项目的根路径

        4.欢迎页
            静态资源文件夹下的index.html

        5.favicon
            任何访问的的**/favicon.ico都从静态资源下找favicon.ico

        6.自定义
            spring.resources.static-location=classpath:/hello/,classpath:/resource/

    4.2 案例

    #TODO 30

