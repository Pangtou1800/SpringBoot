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

``` yml
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

org.springframework.boot.autoconfigure.aop. AopAutoConfiguration, \
org.springframework.boot.autoconfigure.batch. BatchAutoConfiguration, \
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

``` xml
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

``` java
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

``` yml
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

### 4.1 使用步骤

    ·在创建界面选择想要的模块
    ·创建工程之后就自动导入了模块的依赖
    ·在配置文件中指定少量必须的配置就可以运行起来
    ·编写业务逻辑代码即可

    难点：
        每一个场景中，SpringBoot自动配置了哪些属性？
        那些属性必须指定，哪些属性可以修改？

        -> 观察xxxAutoConfiguration, xxxProperties来获取相关内容

### 4.2 SpringBoot对静态资源的映射规则

    关注WebMvcAutoConfiguration

``` java
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

### 4.3 模板引擎

    常见的有：JSP，Velocity，Freemarker，ThymeLeaf...

    模板引擎的功能就是动态结合页面和数据，生成最后的输出页面

    SpringBoot推荐使用ThymeLeaf
        ·语法简单
        ·功能强大

    1.引入ThymeLeaf

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-thymeleaf</artifactId>
        </dependency>

        覆盖默认版本：
        <properties>
            <thymeleaf.version>3.0.9.RELEASE</thymeleaf.version>
            <thymeleaf-layout-dialect.version>2.1.1</thymeleaf-layout-dialect.version>
        </properties>

    2.ThymeLeaf语法

``` java
    @ConfigurationProperties(prefix = "spring.thymeleaf")
    public class ThymeleafProperties {

	private static final Charset DEFAULT_ENCODING = StandardCharsets.UTF_8;

	public static final String DEFAULT_PREFIX = "classpath:/templates/";

	public static final String DEFAULT_SUFFIX = ".html";
```

        类路径下的/templates中

        准备：html 加入命名空间<html xmlns:th="http://www.thymeleaf.org">

        在标签上添加属性：
            <div th:text="${hello}">!显示欢迎信息！</div>
        取值内容就会替换<div>本来的文本内容了

    3.语法规则1
        ·th:text - 改变当前元素里的文本内容

        => th:任意HTML属性，就可以替换原生属性的值

        ·Fragment inclusion
            -th:insert
            -th:replace
        ·Fragment iteration
            -th:each
        ·Conditional evalution
            -th:if
            -th:unless
            -th:switch
            -th:case
        ·Local variable modification
            -th:object
            -th:with
        ·General attribute modification
            -th:attr
            -th:attrprepend
            -th:attrappend
        ·Specific attribute modification
            -th:value
            -th:href
            -th:src
        ·Text(tag body modification)
            -th:text
            -th:utext - 不转义特殊字符
        ·Fragment specification
            -th:fragment
        ·Fragment removal
            -th:remove

    4.语法规则2 - 表达式

``` properties
Simple expressions: - 基础表达式
    Variable Expressions: ${...} - 获取值的表达式，OGNL
        1.获取对象属性
        2.调用方法
        3.内置基本对象
            #ctx: the context object.
            #vars: the context variables.
            #locale: the context locale.
            #request: (only in Web Contexts) the HttpServletRequest object.
            #response: (only in Web Contexts) the HttpServletResponse object.
            #session: (only in Web Contexts) the HttpSession object.
            #servletContext: (only in Web Contexts) the ServletContext object.
        4.内置工具对象
            #execInfo: information about the template being processed.
            #messages: methods for obtaining externalized messages inside variables expressions, in the same way as they would be obtained using #{…} syntax.
            #uris: methods for escaping parts of URLs/URIs
            #conversions: methods for executing the configured conversion service (if any).
            #dates: methods for java.util.Date objects: formatting, component extraction, etc.
            #calendars: analogous to #dates, but for java.util.Calendar objects.
            #numbers: methods for formatting numeric objects.
            #strings: methods for String objects: contains, startsWith, prepending/appending, etc.
            #objects: methods for objects in general.
            #bools: methods for boolean evaluation.
            #arrays: methods for arrays.
            #lists: methods for lists.
            #sets: methods for sets.
            #maps: methods for maps.
            #aggregates: methods for creating aggregates on arrays or collections.
            #ids: methods for dealing with id attributes that might be repeated (for example, as a result of an iteration).
    Selection Variable Expressions: *{...} - 变量选择表达式
        1.基本和${}一样
        2.补充功能
            th:object后，*{field1} => ${object.field1}
    Message Expressions: #{...} - 获取国际化内容
    Link URL Expressions: @{...} - 定义URL连接
    Fragment Expressions: ~{...} - 片段引用表达式
Literals - 字面量
    Text literals: 'one text', 'Another one!',…
    Number literals: 0, 34, 3.0, 12.3,…
    Boolean literals: true, false
    Null literal: null
    Literal tokens: one, sometext, main,…
Text operations: - 文本操作
    String concatenation: +
    Literal substitutions: |The name is ${name}|
Arithmetic operations: - 数学运算
    Binary operators: +, -, *, /, %
    Minus sign (unary operator): -
Boolean operations: - 逻辑运算
    Binary operators: and, or
    Boolean negation (unary operator): !, not
Comparisons and equality: - 比较运算
    Comparators: >, <, >=, <= (gt, lt, ge, le)
    Equality operators: ==, != (eq, ne)
Conditional operators: - 分歧控制
    If-then: (if) ? (then)
    If-then-else: (if) ? (then) : (else)
    Default: (value) ?: (defaultvalue)
Special tokens: - 特殊
    No-Operation: _
```

### 4.4 SpringBoot对SpringMVC的支持

    1.SpringBoot已经自动配置好了SpringMVC需要的大部分组件

        ·ViewResolver
            -ContentNegotiationViewResolver
                从IOC容器中获取所有视图解析器并组合它们
                -> 想要定制的时候只要向IOC容器加入即可
            -BeanNameViewResolver
        ·静态资源文件路径，webjars
        ·index.html
        ·favicon.ico
        ·自动注册了Converter，GenericConverter，Formatter
            -Converter 转换器 - 类型转换组件
            -Formattter 格式化器
                自己添加的格式化器、转换器，加入IOC容器中即可
        ·HttpMessageConverters
            -转换Http请求和响应
            -值是从容器中确定的，所以自己添加MessageConverter时加入IOC容器即可
        ·MessageCodeResolver
            -定义错误码生成规则
        ·ConfigurableWebBindingInitializer
            -初始化WebDataBinder：请求数据绑定到JavaBean等
            -可以自定义添加到容器中

    2.总结：如何修改SpringBoot的默认配置？

        > 基本将自定义组件放进容器就可以了
            ·对于只需要一个的组件，SpringBoot基本上都会先尝试从容器中获取组件之后再自定义
            ·对于可以有多个的组件，SpringBoot会尝试将多个组件组合使用

        > 想要扩展SpringMVC时，编写一个配置类
            ·@Configuration
            ·继承WebMvcConfigurerAdapter(实装WebMvcConfigurer)
            ·不能标记@EnableWebMvc

            既保留默认配置，也加入扩展内容

        > 全面接管SpringMVC配置时，编写一个配置类
            ·@EnableWebMvc
            ·其余同上

            所有的MVC自动配置就都失效了

### 4.5 ResufulCRUD实例

    1.访问首页
        ·在自定义Configuration中配置首页路径
         @Bean
        public WebMvcConfigurerAdapter webMvcConfigurerAdapter() {
            return new WebMvcConfigurerAdapter() {
                @Override
                public void addViewControllers(ViewControllerRegistry registry) {
                    super.addViewControllers(registry);
                    registry.addViewController("/").setViewName("login");
                    registry.addViewController("/index.html").setViewName("login");
                }
            };
        }
        ·静态资源在templates中，资源链接替换为th:标签形式
            > 好处：可以自动更改项目名

    2.页面国际化
        经典：
            ·编写国际化配置文件
            ·使用resourceBundleMessageSource管理国际化资源文件
            ·在JSP页面使用fmt标签获取国际化内容
        SpringBoot：
            ·编写国际化配置文件
            ·SpringBoot自动配置好了管理国际化资源的组件
                ※默认类路径下messages.properties

                设基础名：spring.messages.basename=i18n.login
            ·用ThymeLeaf - #{}取出

        至此：
            根据浏览器的语言设置信息切换国际化

        实现按钮切换：
            ·原理 - 利用国际化区域信息对象（Locale）

            SpringBoot默认配置了LocaleResolver - 根据请求头带来的区域信息进行国际化

            ·自定义LocaleResolver - 根据连接的参数来国际化

                @Override
                public Locale resolveLocale(HttpServletRequest request) {
                    String l = request.getParameter("l");
                    if (!StringUtils.isEmpty(l)){
                        String[] params = l.split("_");
                        return new Locale(params[0], params[1]);
                    }
                    return Locale.getDefault();
                }

            ·在连接上带上 语言_区域 信息

                <a class="btn btn-sm" th:href="@{/index.html(l='zh_CN')}">中文</a>

    3.登录功能

        小技巧：
            要让开发时期静态资源变更实时生效
                1.禁用模板引擎缓存
                2.Ctrl+F9 IDEA再次编译

        为了防止重复提交
            1.添加view映射
                registry.addViewController("/main.html").setViewName("dashboard");
            2.重定向至登录成功页面
                return "redirect:/main.html";
            3.拦截器防止直接连接主页面
                public class LoginHandlerInterceptor implements HandlerInterceptor {
                    public boolean preHandle(
                    //从session中检查登录信息
                    //没有登录时发送至首页面
    
    4.员工列表

        ·Restful CRUD
                            请求URI     请求方式
            查询所有员工     emps        GET
            查询某个员工     emp/{id}    GET
            添加页面         emp         GET
            添加员工         emp         POST
            修改页面         emp/{id}    GET
            (查出员工回显)
            修改员工         emp         PUT
            删除员工         emp/{id}    DELETE

        4.1 抽取公共html代码片段

            - 抽取

                <div th:fragment="copy">
                    ...
                </div>

            - 引入

                <div th:insert="~{footer::copy}">
                    ...
                </div>

                ~{template::selector} - 模板名：选择器
                {template::fragmentname} - 模板名：片段名

                模板名照thymeleaf的视图解析名拼串

            - 三种引入公共片段的th属性：

                th:insert - 片段直接插入宿主元素
                th:replace - 片段取代宿主元素
                th:include - 片段内容取代宿主元素内容

            - 在引用公共片段时传入参数

                th:replace="commons/bar::#sidebar(activeUri='main.html')

        4.2 表示

            - 日期格式化

                ${#dates.format(emp.birth, 'yyyy-MM-dd')}

    5.添加员工

        最容易出现的问题：数据格式不对

            日期：2017-12-01 2017/12/01 2017.12.01

            日期格式化 spring.mvc.date-format=yyyy-MM-dd

    6.修改员工

        区分是修改还是添加？
            -修改需要回显
            -添加不需要回显

        发送PUT请求
            -配置HiddenHttpMethodFilter
            -POST表单中隐藏input _method + METHOD

    7.删除员工

        用ThymeLeaf为DOM元素添加额外属性
            -th:attr="del_uri=@{/emp/}+${emp.id}"

## 第五节 错误处理机制

### 5.1 默认错误处理

    > 浏览器
        Whitelabel Error Page
        This application has no explicit mapping for /error, so you are seeing this as a fallback.

        Wed Sep 02 19:46:25 CST 2020
        There was an unexpected error (type=Not Found, status=404).
        No message available

    > 其他客户端
        {
        "timestamp": "2020-09-02T11:47:59.953+0000",
        "status": 404,
        "error": "Not Found",
        "message": "No message available",
        "path": "/crud/empsa"
        }

### 5.2 默认处理原理 - 自动配置

    ErrorMvcAutoConfiguration

    1.默认添加的组件
        @Bean
        @ConditionalOnMissingBean(value = ErrorAttributes.class, search = SearchStrategy.CURRENT)
        public DefaultErrorAttributes errorAttributes() {

        @Bean
        @ConditionalOnMissingBean(value = ErrorController.class, search = SearchStrategy.CURRENT)
        public BasicErrorController basicErrorController(ErrorAttributes errorAttributes,
                ObjectProvider<ErrorViewResolver> errorViewResolvers) {

        @Bean
	    public ErrorPageCustomizer errorPageCustomizer(DispatcherServletPath dispatcherServletPath) {

        @Bean
	    public static PreserveErrorControllerTargetClassPostProcessor preserveErrorControllerTargetClassPostProcessor() {

    2.默认错误流程

        400+或500+时，ErrorPageCustomer就会生效，订制错误的响应规则

        @Value("${error.path:/error}")
	    private String path = "/error";

        没有配置的时候就↓

        @RequestMapping("${server.error.path:${error.path:/error}}")
        public class BasicErrorController extends AbstractErrorController {

            然后根据请求头里有没有 Accept: text/html来决定调用返回HTML或JSON的默认处理方法

        默认去解析error/404地址
            ·首先试图交由模板引擎解析
            ·不可以时再从静态资源中寻找对应页面

        > 如何订制错误页面？
            -有模板引擎时，error/状态码.html加入即可
            -4XX或5XX.html可以统一匹配错误页面（当然遵循精确匹配原则）

            -页面获取信息：
                [[${status}]]
                ...

        > 如何订制错误JSON数据？
            -利用SpringMVC
            @ControllerAdvice
            public class MyExceptionHandler {
                @ResponseBody
                @ExceptionHandler(UserNotExistException.class)
                public Map<String, Object> handleException(UserNotExistException e){

                缺点：不会自适应服务器和其他客户端

            -转发到/error，利用SpringBoot的自适应
                @ExceptionHandler(UserNotExistException.class)
                public String handleException(UserNotExistException e, HttpServletRequest request) {

                request.setAttribute("javax.servlet.error.status_code", 525);

                ※错误信息都要设在request域中

            -将订制数据携带出去
                1.实现ErrorController或者继承AbstractErrorController
                2.覆盖DefaultErrorAttributes
                    @Component
                    public class MyErrorAttributes extends DefaultErrorAttributes {
                        @Override
                        public Map<String, Object> getErrorAttributes(WebRequest webRequest, boolean includeStackTrace) {
                            Map<String, Object> errorAttributes = super.getErrorAttributes(webRequest, includeStackTrace);
                            errorAttributes.put("company", "JojaMarket");
                            errorAttributes.put("ext", webRequest.getAttribute("ext", WebRequest.SCOPE_REQUEST));
                            return errorAttributes;
                        }

                ·map就会被转换为JSON
                ·map中的值可以在错误页面上取出

## 第六节 配置嵌入式Servlet容器

    SpringBoot默认使用嵌入式的Servlet容器（Tomcat）

### 6.1 如何订制修改Tomcat配置

    关注：ServerProperties

    ·各种相关server.的属性
    ·Tomcat相关就是server.tomcat
    ...

    ·也可以编写一个EmbeddedServletContainerCustomizer
        已被替代为WebServerFactoryCustomizer

        @Bean
        public WebServerFactoryCustomizer<ConfigurableWebServerFactory> webServerFactoryCustomizer() {
            return new WebServerFactoryCustomizer<ConfigurableWebServerFactory>() {
                @Override
                public void customize(ConfigurableWebServerFactory factory) {
                    factory.setPort(8083);
                }
            };
        }

    也可以放一个嵌入式Servlet容器定制器

### 6.2 注册Servlet组件

    -Servlet: ServletRegistrationBean
    -Filter: FilterRegistrationBean
    -Listener: ListenerRegistrationBean

    @Bean
    public ServletRegistrationBean<MyServlet> myServlet() {
        return new ServletRegistrationBean<>(new MyServlet(), "/myServlet");
    }

    向容器中注册各种RegistrationBean即可

    最好的例子：SpringBoot自动注册SpringMVC的DispatcherServlet

### 6.3 如何切换为其他的Servlet容器

    -Jetty（适合长连接应用，比如在线聊天）
    -Undertow(不支持JSP，但并发性能很好)

    对于这三个默认支持的Servlet容器，切换pom的依赖关系即可

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-web</artifactId>
        <exclusions>
            <exclusion>
                <groupId>org.springframework.boot</groupId>
                <artifactId>spring-boot-starter-tomcat</artifactId>
            </exclusion>
        </exclusions>
    </dependency>

    <dependency>
        <groupId>org.springframework.boot</groupId>
        <artifactId>spring-boot-starter-jetty</artifactId>
    </dependency>

### 6.4 嵌入式Servlet容器和外置Servlet容器

    ·优点:
        简单、便携
    ·缺点：
        默认不支持JSP
        优化定制复杂（ServerProperties或者自定义定制器，甚至自己编写嵌入式容器创建工厂）

    使用外置Servlet容器
        1.安装Tomcat
        2.应用打包为war包

    步骤：
        1.创建为war项目及其目录结构(main/webapp/WEB-INF/web.xml)
        2.将嵌入式的Tomcat指定为provided
        3.编写一个ServletInitializer extends SpringBootServletInitializer
            @Override
            protected SpringApplicationBuilder configure(SpringApplicationBuilder)
            // 传入SpringBoot的应用主程序
        4.启动服务器就可以使用了

    启动原理：
        jar包：直接执行SpringBoot启动类的主方法即可
        war包：启动服务器，服务器启动SpringBoot应用

        Servlet3.0中有一项规范：
            Shared libraries and runtimes pluggability
            1.在Web应用启动时，会创建当前Web应用中的每一个jar包中的
                ServletContainerInitializer的实现类的实例
            2.该实现类从jar包的META-INF/services文件下的名为
                javax.servlet.ServletContainerInitializer的文件，
                文件内容指向该实现类的全类名
            3.还可以使用@HandleTypes，在应用启动的时候，加载我们感兴趣的类

        就是这个了：
        org\springframework\spring-web\5.2.8.RELEASE\spring-web-5.2.8.RELEASE.jar!\META-INF\services\javax.servlet.ServletContainerInitializer

            org.springframework.web.SpringServletContainerInitializer

            @HandlesTypes(WebApplicationInitializer.class)
            public class SpringServletContainerInitializer implements ServletContainerInitializer {

                ↓ 传到这方法的第一个参数

            @Override
            public void onStartup(@Nullable Set<Class<?>> webAppInitializerClasses, ServletContext servletContext)

                ↓各自调用onStartup

            for (WebApplicationInitializer initializer : initializers) {
                initializer.onStartup(servletContext);
            }

## 第七节 SpringBoot与Docker

### 7.1 什么是Docker？

    Docker是一个开源的应用容器引擎

    - 容器 + 虚拟机

    Linux：                             Linux2:
                                        Docker
    MySQL   -> MySQL-Docker镜像     ->   运行镜像产生MySQL容器
    Redis   -> Redis-Docker镜像     ->   运行镜像产生Redis容器
    Tomcat  -> Tomcat-Docker镜像    ->   运行镜像产生Tomcat容器
    ...

    Docker支持将软件编译成一个镜像；
    在镜像软件中做好各种配置，将镜像发布出去，其他人可以直接使用镜像；
    运行中的镜像称为容器，容器的启动是非常快的。

### 7.2 Docker核心概念

    Docker主机（Host） - 安装了Docker程序的机器；Docker直接安装在操作系统上

    Docker客户端（Client） - 连接Docker主机进行操作，与Docker的守护进程进行通信

    Docker仓库（Registry） - 保存Docker镜像用的。有公用也可以私用

    Docker镜像（Images） - 用于创建Docker容器的模板。从守护进程运行镜像即可

    Docker容器（Container） - 镜像启动后的实例称为一个容器 ※同一镜像运行五次即可获得五个实例

    使用步骤:
        1.安装Docker
        2.去Docker仓库下载软件镜像
        3.使用Docker运行镜像，生成Docker容器
        4.对容器的启动停止就是对软件的启动停止

        Docker要求CentOS内核版本高于3.10
            uname -r 查看内核版本
            yum update 升级内核
            yum install docker 安装Docker
            systemctl start docker 启动Docker
            systemctl stop docker 停止Docker
            systemctl enable docker 开机自启Docker

### 7.3 Docker常用操作

    1.镜像操作

        检索： docker search 关键字 - 就是去Docker Hub搜索镜像
        拉去： docker pull 镜像名#tag - tag默认latest
        查看： docker images - 查看所有镜像
        删除： docker rmi image-id

[Docker Hub](https://hub.docker.com/)

    2.容器操作

        软件镜像 -> 运行镜像 -> 产生容器

        运行： docker run --name 运行名 -d(后台运行) [-p 虚拟机端口：容器映射端口] 镜像名
        列表： docker ps [-a(所有包括历史)]
        停止： docker stop 容器ID/容器名
        启动： docker start 容器ID/容器名
        删除： docker rm 容器ID/容器名
        运行tomcat：docker run --name MyTomcat -d -p 8888:8080 tomcat
        日志： docker logs  容器ID/容器名

        ※想同时启动多个Tomcat时，只要映射不同端口就可以了

### 7.4 环境搭建

    ·MySQL
    ·Redis
    ·RabbitMQ
    ·ElasticSearch

    ※不要忘记映射端口

    几个高级操作：
        docker run --name some-mysql -v /my/custom:/etc/mysql/conf.d -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag

        -v /my/custom:/etc/mysql/conf.d ： 挂载/my/custom到Docker容器的/etc/mysql/conf.d下

        或者疯狂写参数：
        docker run --name some-mysql -e MYSQL_ROOT_PASSWORD=my-secret-pw -d mysql:tag --character-set-server=utf8mb4 --collation-server=utf8mb4_unicode_ci

## 第八节 SpringBoot与数据访问

### 8.1 简介

    对于数据访问层，无论是SQL还是NOSQL，SpringBoot默认采用整合SpringData的方式进行统一处理。

    各种场景启动器：spring-boot-starter-data-xxxx

### 8.2 整合JDBC与数据源

    1.添加依赖

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-jdbc</artifactId>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>
    
    2.简单配置

        spring:
            datasource:
                url: jdbc:mysql://192.168.229.129:3306/jdbc?serverTimeZone=UTC
                driver-class-name: com.mysql.cj.jdbc.Driver
                username: root
                password: root
                schema:

                - classpath:department.sql
                - classpath:employee.sql

                initialization-mode: always

        参考：
            DataSourceConfiguration

            SpringBoot默认支持：
                tomcat.jdbc.pool.DataSource
                HikariDataSource
                org.apache.commons.dbcp2.BasicDataSource

            也可以自定义DataSource

            初始化时会调用DataSourceInitializer
                -继承自ApplicationListener
                -可以自动运行runSchemaScript(), runDataScripts()
                -schema-*.sql 建表； data-*.sql 插数

    3.高级配置 - 使用druid数据源
        ·引入druid

            <dependency>
                <groupId>com.alibaba</groupId>
                <artifactId>druid</artifactId>
                <version>1.1.23</version>
            </dependency>

            spring.datasource.type: com.alibaba.druid.pool.DruidDataSource

        ·配置属性

            spring:
                datasource:
                    type: com.alibaba.druid.pool.DruidDataSource
                    druid:
                    url: jdbc:mysql://192.168.229.129:3306/jdbc?serverTimeZone=UTC
                    driver-class-name: com.mysql.cj.jdbc.Driver
                    username: root
                    password: root
                    initialization-mode: always
                    minIdle: 5
                    maxActive: 20
                    maxWait: 60000
                    ...
                    filters: stat

        ·使非内置属性生效
            @Configuration
            public class DruidConfig {

                @ConfigurationProperties(prefix = "spring.datasource.druid")
                @Bean
                public DataSource druid() {
                    return new DruidDataSource();
                }

                //配置一个Druid的监控
                //1.配置一个管理后台的Servlet
                @Bean
                public ServletRegistrationBean<StatViewServlet> statViewServlet() {

                //2.配置一个监控的filter
                @Bean
                public FilterRegistrationBean<WebStatFilter> webStatFilter(){

### 8.3 整合MyBatis

    1.添加依赖

        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>

        ※MyBatis自己出的，自带mybatis, mybatis-spring

    2.写Bean和Dao

    3.注解版的话，写好Mapper就可以直接用了

        insert:
            @Options(useGeneratedKeys = true, keyProperty = "id")
            @Insert("insert into department (departmentName) values(#{departmentName})")
            Integer insertDepartment(Department department);

    4.添加自定义设置类

        @MapperScan("pt.joja.mapper") - 批量扫描Mapper接口
        @Configuration
        public class MyBatisConfig {
            @Bean
            public ConfigurationCustomizer configurationCustomizer() {
                return configuration -> {
                    configuration.setMapUnderscoreToCamelCase(true);
                };
            }
        }

    5.配置版

        application.yml指定即可 ※全局配置文件会使自定义配置类失效

        mybatis:
            config-location: classpath:mybatis/mybatis-config.xml
            mapper-locations: classpath:mybatis/mapper/*.xml
            

### 第九节 SpringData与JPA

#### 9.1 什么是SpringData?

    SpringData项目的目的是为了简化构建基于Spring框架应用的数据访问技术，
    也包括非关系数据库、MapReduce框架、云数据服务等等。

    特点：
        ·提供统一的Respository接口，包含了CRUD，排序，分页...
        ·提供模板类RedisTemplate，MongoTemplate..

    应用程序面向SpringData编程即可

### 9.2 JPA - Java Persistence API

    JSR317规范：使用注解来辅助实现数据持久化

    具体实现有Hibernate、Toplink、OpenJPA等，SpringData默认使用Hibernate

### 9.3 整合JPA

    1.引入依赖
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-jpa</artifactId>
        </dependency>

    2.配置数据源

    3.使用JPA

        JPA也是基于ORM思想的

        ·编写实体类
        ·使用JPA注解配置映射关系

            @Entity
            @Table(name = "tbl_user") - 省略时默认表名类名小写
            public class User {

            @Id - 主键
            @GeneratedValue(strategy = GenerationType.IDENTITY) - 自增
            private int id;

            @Column
            private String lastName;

        ·编写Dao接口操作实体类对应的数据表

            public interface UserRepository extends JpaRepository<User, Integer> {

                ·JpaRepository就有了sort和paging的功能
                ·<Entity, PK>

            ※不需要打@Repository注解

        ·配置自动创建数据表，打印执行SQL

            spring:
                jpa:
                    hibernate:
                        ddl-auto: update
                    show-sql: true

        ·就可以在Controller里直接使用了

## 第九节 SpringBoot启动配置原理

    启动原理、运行流程、自动配置原理

    几个重要的事件回调机制：
        ApplicationContextInitializer
        SpringApplicationRunListener
        ApplicationRunner
        CommandLineRunner

    启动流程：
        1.创建SpringApplication对象
            -initialize(Object...)
                -判断是否web应用
                -从类路径下META-INF/spring.factories中寻找所有配置的ApplicationContextInitializer
                -从类路径下META-INF/spring.factories中寻找所有配置的ApplicationListener
                -从多个配置类中找到有main方法的主配置类

        2.运行run方法
            -从类路径下META-INF/spring.factories中寻找所有配置的SpringApplicationRunListener
            -回调所有的SpringApplicationRunListener.starting()
            -创建环境之后，回调所有SpringApplicationRunListener.environmentPrepared()
            -创建IOC容器 ： 决定一下是WebIOC还是普通IOC
            -准备上下文环境
                -保存environment
                -回调所有ApplicationContextInitializer.initialize()
                -回调所有SpringApplicatoinRunListener.contextPrepared()
                -最后一步回调所有SpringApplicatoinRunListener.contextLoaded()
            -刷新容器：IOC容器初始化
                如果是web应用，嵌入式Tomcat也在此时创建
            -从IOC容器中获取所有ApplicationRunner和CommandLineRunner，进行回调
            而且ApplicationRunner比CommandLineRunner先执行
            -回调所有的SpringApplicationRunListener.finished()
            -全部启动完成后，返回启动好的IOC容器

## 第十节 SpringBoot自定义starters

    starter：
        1.场景需要使用的依赖是什么
        2.如何编写自动配置
            ·@Configuration指定配置类
            ·@Conditionalonxxx指定条件
            ·@AutoConfigureOrder指定自动配置顺序
            ·@Bean添加各种组件
            ·使用@ConfigurationProperties结合XXXProperties.class来绑定相关配置
            ·@EnableConfigurationProperties让XXXProperties生效
        3.将需要启动就加载的自动配置类配置在/META-INF/spring.factories中

        模式：
            1.启动器
                -启动器是一个空的jar文件，仅提供辅助性依赖管理
                -专门提供一个自动配置模块，受到启动器依赖
                -命名规约：
                    官  方："spring-boot-starter-模块名"
                    自定义："模块名-spring-boot-starter"

    步骤：
        1.创建一个空工程
        2.添加Model - starter
        3.添加Model - starter-autoconfiguration
        4.向starter中添加starter-autoconfiguration的依赖
        5.编写starter-autoconfiguration
            -保留spring-boot-starter的依赖
            -创建功能类，开启Properties的getter和setter
            -创建Properties类，注解@ConfigurationProperties，将property都设置为属性
                @ConfigurationProperties(prefix = "pt.joja")
                public class HelloProperties {
            -创建AutoConfiguration类
                @Configuration
                @ConditionalOnWebApplication - 条件示例
                @EnableConfigurationProperties(HelloProperties.class)
                public class HelloServiceAutoConfiguration {
                    @Autowired
                    HelloProperties helloProperties;

                    @Bean - 向容器注入功能类
                    public HelloService helloService() {
            -将AutoConfiguration添加至/META-INF/spring.factories中
        6.安装starter-configuration至仓库
        7.安装starter至仓库
    
    使用：
        1.在其他SpringBoot工程中，引入starter依赖
        2.在application.properties中按照Properties注解的前缀设置属性
        3.从容器中获取功能呢类，就是自动配置完成的类了

## 第十一节 SpringBoot与缓存

    ·JSR-107缓存规范
    ·Spring缓存抽象
    ·整合Redis

### 11.1 JSR107规范

    Java Caching定义了5个核心接口

    1.CachingProvider - 缓存提供者
        定义了创建、配置、获取、管理和控制多个CacheManager。
        一个应用可以在运行期间访问多个CacheProvider
    2.CacheManager - 缓存管理器
        定义了创建、配置、获取、管理和控制多个唯一命名的Cache，
        这些Cache存在于CacheManager的上下文中。
        一个CacheManager仅为一个CacheProvider所拥有
    3.Cache - 缓存
        是一个类似于Map的数据结构并临时存储以Key为索引的值。
        一个Cache仅为一个CacheManager所拥有
    4.Entry - 键值对
        是一个存储在Cache中的键值对
    5.Expiry - 有效期
        每一个存储在Cache中的条目有个一个定义好的有效期。
        一旦超过这个期限条目处于过期状态。
        过期的条目不可以访问、更新和删除。
        缓存有效期通过ExpiryPolicy设置

    导包：
        <dependency>
            <groupId>javax.cache</groupId>
            <artifactId>cache-api</artifactId>
        </dependency>

    JSR107应为目前支持得不那么广泛，所以更过的使用Spring的缓存抽象

### 11.2 Spring缓存抽象

    Spring保留了CacheManager和Cache两个接口，并支持一部分JSR107的缓存注解

    1.几个重要概念

        CacheManger - 缓存管理器
        Cache - 缓存接口，有RedisCache、EhCacheCache、ConcurrentMapCache等
        @Cacheable - 针对方法，根据方法的请求参数对其结果进行缓存
        @CacheEvict - 清空缓存，可以标注在删除方法上
        @CachePut - 保证方法被调用，同时希望结果被缓存，比如说更新方法
        @EnableCaching - 开启基于注解的缓存
        keyGenerator - 缓存key的生成策略
        serialize - 缓存数据时value的序列化策略

### 11.3 快速体验缓存

    1.搭建环境
        Core
            -Cache
        Web
            -Web
        SQL
            -MySQL
            -MyBatis

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-web</artifactId>
        </dependency>
        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-cache</artifactId>
        </dependency>
        <dependency>
            <groupId>org.mybatis.spring.boot</groupId>
            <artifactId>mybatis-spring-boot-starter</artifactId>
            <version>2.1.3</version>
        </dependency>
        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <scope>runtime</scope>
        </dependency>

        ·配置数据源
            spring.datasource...

    2.使用步骤

        1.开启基于注解的缓存
            @EnableCaching

        2.标注缓存注解
            @Cacheable

        
    3.@Cacheable
        将方法的运行结果进行缓存，再次调用方法时直接从缓存中获取
            -cacheNames/value: 指定缓存组件的名字
            -key：缓存数据时用的key，默认使用方法参数的值
                可以使用SpEL
                    #id 参数值
                    #a0
                    #p0
                    #root.args[0]
            -keyGenerator: 默认就用指定的key，也可以定制，和key属性二选一
            -cacheManger/cacheResolver：从哪个管理器中拿缓存
            -condition/unless：符合条件的情况下才缓存
            -sync：是否使用异步模式

        @Cacheable工作原理

            1.缓存标记方法运行之前，先按照cacheNames指定的名字从cacheManager中获取Cache
            2.拿不到缓存就加锁造一个缓存
            3.从cache中用generator生成的key进行查找
            4.没有查到缓存的话就调用目标方法，然后把结果用key放进缓存
            5.如果查到了缓存就直接返回，不调用目标方法

            可以自定义KeyGenerator：
                @Configuration
                public class MyCacheConfig {
                    @Bean("myKeyGenerator")
                    public KeyGenerator keyGenerator() {
                        return (target, method, params) -> {
                            String key = method.getName() + "[" + Arrays.toString(params) + "]";
                            System.out.println("myKeyGenerator: " + key);
                            return key;
                        };
                    }
                }

    4.@CachePut
        调用方法后更新缓存

        记得要和@Cacheable协调缓存key，以达到同步更新缓存的目的

        @CachePut(key="#employee.id")或(key="#result.id")
        @Cacheable(key="#id") - 不可以使用result.id因为方法不一定执行

    5.@CacheEvict
        缓存清除 - 比如说删除了某个记录后

        用key指定清除对象的键

        指定allEntries = true，清空全部缓存内容
        指定beforeInvocation = true，指定清除在方法执行前进行

    6.@Caching
        组合注解定义复杂的缓存规则

    7.@CacheConfig(cacheNames = "emp")
        @Service
        public class EmployeeService {

            加在类上设置缓存的公共配置

### 11.4 缓存中间件

    除了默认的SimpleCache之外，更多使用的是响应的缓存中间件

    示例：整合Redis

    步骤：
        1.安装Redis
        2.添加starter依赖
        3.配置Redis

            spring:
                redis:
                    host: 192.168.229.129:6379

            AutoConfiguration会自动注入
                @Autowired
                StringRedisTemplate stringRedisTemplate;

                @Autowired
                RedisTemplate<Object, Object> redisTemplate;

        4.测试Redis
            // String
            stringRedisTemplate.opsForValue();
            // List
            stringRedisTemplate.opsForList();
            // Set
            stringRedisTemplate.opsForSet();
            // Hash
            stringRedisTemplate.opsForHash();
            // ZSet
            stringRedisTemplate.opsForZSet();

            使用对象的话，默认会以Java序列化的格式保存 ※通过Java程序可以正常读取

                get "\xac\xed\x00\x05t\x00\x06emp-01"
                "\xac\xed\x00\x05sr\x00\x15pt.joja.bean.Employee}
                ...
                comsq\x00~\x00\x04\x00\x00\x00\x00sq\x00~\x00\x04\x00\x00\x00\x01t\x00\x04Lily"

            一般转换为JSON等形式之后进行保存
                1.可以自己手动转JSON
                2.Redis默认提供了一些序列化器，自定义配置中注入容器即可

                    @Bean
                    public RedisTemplate<Object, Employee> redisTemplateEmployee(RedisConnectionFactory redisConnectionFactory) throws UnknownHostException {
                        RedisTemplate<Object, Employee> redisTemplate = new RedisTemplate<>();
                        redisTemplate.setConnectionFactory(redisConnectionFactory);
                        Jackson2JsonRedisSerializer<Employee> serializer = new Jackson2JsonRedisSerializer<>(Employee.class);
                        redisTemplate.setDefaultSerializer(serializer);
                        return redisTemplate;
                    }

                    ※使用的时候方法名就是beanName
                    ※反序列化也要使用同一个redisTemplate

        5.测试缓存
            工作原理：
                ·引入RedisStarter之后，容器中就注入了RedisCacheManager，
                其他的CacheManager就不会起作用了
                ·RedisCacheManger会创建RedisCache

            默认保存<Object, Object>是利用序列化来保存的，
            因为RedisCacheManager默认使用的是JDK的RedisTempalte

            自定义CacheManager

            默认会将cacheName作为key的前缀

            缓存控制既可以用注解的方式，也可用编码的方式

## 第十二节 SpringBoot与消息

### 12.1 概述

    大多数应用都可以通过消息服务中间件来提升系统的异步通信性能，扩展解耦能力

    场景举例：
        ·异步处理请求写入消息队列，由消息队列异步执行
        ·应用解耦，不同服务分别写入+订阅
        ·流量削峰，请求进入消息队列，进队成功的顺次处理；进队失败的快速响应

    两个重要概念：
        消息代理 message broker
        目的地 destination

        消息发送者发送消息以后，将由消息代理接管，消息代理保证消息传递到指定目的地
    
    主要的两种形式的目的地：
        1.队列 queue - 点对点消息通信（point-to-point）
        2.主题 topic - 发布（publish）/订阅（subscribe）消息通信

    点对点式：
        消息发送者发送消息至队列，消息接收者从队列中获取消息内容，消息读取后被移出队列
    
        ※可以有多个接收者，但只有一个接收者能收到消息

    发布订阅式：
        发布者发送消息到主题，多个接收者监听/订阅主题，那么就会在消息到达时同时收到消息

    JMS - Java Message Service
        基于JVM消息代理的规范。实现有ActiveMQ、HornetMQ

    AMQP - Advanced Message Queuing Protocol
        高级消息队列协议，兼容JMS。实现有RabbitMQ

||JMS|AMQP|
|---|---|---|
定义|Java API|网络线级协议|
跨语言|否|是|
跨平台|否|是|
Model|1.Peer-2-Peer<br>2.Pub/Sub|1.direct exchange<br>2.fanout exchange<br>3.topic<br>4.headers exchange<br>5.system exchange|
|支持消息类型|TextMessage<br>MapMessage<br>BytesMessage<br>StreamMessage<br>ObjectMessage<br>Message|byte[]|

    Spring的支持：
        ·Spring-jms提供了对JMS的支持
        ·Spring-rabbit提供了对AMQP的支持
        ·提供了JmsTemplate、RabbitTemplate来发送消息
        ·@JmsListener、@RabbitListener注解在方法上监听消息
        ·@EnableJms、@EnableRabiit开启支持

### 12.2 RabiitMQ简介

    erlang开发的AMQP的开源实现

    核心概念：
        -Message
            ·消息是不具名的，由消息头和消息体组成
            ·消息体是不透明的
            ·消息头由一系列可选属性组成，
            包括routing-key（路由键）、priority（优先级）、delivery-mode（持久性存储）等
        -Publisher
            消息的生产者，一个向交换器发布消息的客户端应用程序
        -Exchange
            交换器，用来接收生产者发送的消息，并将消息路由给服务器中的队列
            ·direct（默认）
            ·fanout
            ·topic
            ·headers
        -Queue
            消息队列，保存消息直到发送给消费者
        -Binding
            绑定，用于消息队列和交换器之间的关联，基于路由键将交换器和消息队列连接起来
        -Connection
            网络连接
        -Channel
            信道，多路复用连接中的一条独立的双向数据流通道
            信道是建立在真实的TCP连接内的虚拟连接，AMQP命令都是通过信道发出去的
        -Consumer
            消费者
        -Virtual Host
            虚拟主机，表示一批交换器、消息队列和相关对象
            每个vhost本质上都是一个mini版的RabbitMQ，拥有自己的队列、交换器、绑定和权限机制
        -Broker
            消息代理，表示消息队列的服务器实体

            Publisher
            ↓
            ---Broker---
                ---VirtualHost---
                    Exchange
                    ↓
                    ↓Binding
                    ↓
                    Queue
                ---
            ---
            ↓
            ---Connection---
                Channel Channel Channel
                ↓       ↓       ↓
            ---
            ↓
            Consumer

### 12.3 RabbitMQ运行机制

    1.AMQP的消息路由

        AMQP相比于JMS增加了Exchange和Binding的角色

    2.Exchange类型

        ·direct - 直连，完全一致
            routing key = Queue的binding key时进行连接
        ·fanout - 广播
            不看路由键，直接绑定到所有队列上
        ·topic - 主题
            路由键模糊匹配
            routing key : key1.key2.key3
            binding key : key1.*.* key1.# #.key3 ...

            # : 0或多个单词
            * ：1个单词

### 12.4 RabbitMQ整合

    0.安装RabbitMQ

        docker run -d -p 5672:5672 -p 15672:15672 --name RabbitMQ0907 587380cbba10
                            ↑           ↑
                        消息接口      管理接口

        0.1 在交互页面上添加Exchange
            exchange.direct
            exchange.fanout
            exchange.topic

        0.2 在交互页面上添加消息队列
            joja
            joja.emps
            joja.news
            market.news

        0.3 绑定路由键
            direct - joja : joja
            direct - joja.emps : joja.emps
            direct - joja.news : joja.news
            direct - market.news : market.news
            fanout - joja : joja
            fanout - joja.emps : joja.emps
            fanout - joja.news : joja.news
            fanout - market.news : market.news
            topic - joja : joja.#
            topic - joja.emp : joja.#
            topic - joja.news : joja.#
            topic - joja.news : *.news
            topic - market.news : *.news

        0.4 在交互页面上测试发送消息


    1.添加依赖

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-amqp</artifactId>
        </dependency>

        自带依赖
            spring-message
            spring-rabbit

        RabbitAutoConfiguration
            -自动配置了连接工厂
            -从RabbitProperteis中获取属性
            -添加RabbitTemplate
            -添加AmqpAdmin ： RabbitMQ系统管理组件，可以创建队列等等

    2.配置

        spring:
            rabbitmq:
                host: 192.168.229.129
                username: guest
                password: guest
                port: 5672
                virtual-host: /

    3.测试
        rabbitTemplate.send("", "", message);
            - 自制Message
        rabbitTemplate.convertAndSend("" ,"", object);
            - Object自动转换消息体，默认Java序列化
        Object o = rabbitTemplate.receiveAndConvert("joja.news");
            - 指定消息队列，默认Java反序列化，取走之后消息消失

    4.定制Converter

        注入MessageConver即可

        @Configuration
        public class RabbitConfig {
            @Bean
            public MessageConverter messageConverter(){
                return new Jackson2JsonMessageConverter();
            }
        }

        ※自带了JSON converter，序列化、反序列化自定义对象都是可以的

    5.配备监听器

        @EnableRabbit
        SpringApplication

        @RabbitListener(queues = {"joja.news", "market.news"})
        listenerMethod

    6.程序中创建组件

        AmqpAdmin

        amqpAdmin.declareExchange(new DirectExchange("amqp.exchange.direct"));
        amqpAdmin.declareQueue
        amqpAdmin.Binding

        ※各种declare方法即可

## 第十三节 SpringBoot与检索

### 13.1 检索

    我们的应用经常需要添加检索功能，开源的ElasticSearch是目前全文搜索引擎的首选。
    它可以快速地存储、搜索和分析海量的数据。

    ElasticSearch是一个分布式搜索引擎，提供RestfulAPI，底层基于Lucene，
    采用多分片（shard）的方式保证数据安全，并且提供自动resharding功能。

### 13.2 安装ElasticSearch环境

    1.安装Docker镜像
    2.启动Docker镜像

        ·ElasticSearch默认启动占用2G的内存，加-e选项设置内存容量
        ·ElasticSearch的网络接口默认9200
        ·ElasticSearch节点间互相通信接口9300

        docker run -e ES_JAVA_OPTS="-Xms256m -Xmx256m" -d -p 9200:9200 -p 9300:9300 --name Elastic0908 elasticsearch

        启动后直接外部浏览器访问9200端口即可

            {
                "name" : "zF0HKUJ",
                "cluster_name" : "elasticsearch",
                "cluster_uuid" : "N4iR8EwMSU-3B_6rlE_qmQ",
                "version" : {
                    "number" : "5.6.12",
                    "build_hash" : "cfe3d9f",
                    "build_date" : "2018-09-10T20:12:43.732Z",
                    "build_snapshot" : false,
                    "lucene_version" : "6.6.1"
                },
                "tagline" : "You Know, for Search"
            }

    3.基本介绍

        ElasticSearch是面向文档的，它存储着整个对象或文档
        而且会索引每个文档的内容使之可以被检索、排序和过滤
        ES使用JSON作为文档的序列化格式

        存储数据到ES的行为就称为索引

        一个ES集群里可有多个索引，每个索引可以包含多个类型，每个类型包含多个属性

        ES集群
        ↓
        索引1           索引2
        ↓--------       ↓
        类型1   类型2   ...
        ↓
        文档1
        {
            属性：值
            ...
        }
        文档2
        {
            属性：值
            ...
        }

        类比：索引-库，类型-表，文档-记录

        简单操作：

            PUT /索引/类型/文档ID - 初次为插入，既存为更新
            {
                属性：值
                ...
            }

            GET  /索引/类型/文档ID - 检索

            DELETE  /索引/类型/文档ID - 删除

            HEAD  /索引/类型/文档ID - 查找是否存在

            GET  /索引/类型/_search - 检索所有

            GET  /索引/类型/_search?q=last_name:Smith - 检索条件

                ※返回值里还有相关性得分

            GET/POST /索引/类型/_search - 按JSON的表达式检索
            {
                "query": {
                    "match": { - 匹配语法规则
                    "last_name": "Smith"
                    }
                }
            }

        全文检索：
            "match": {
                "about": "rock climbing" - about属性里是不是有"rock","climbing"
            }

            包含任一词汇即可

        短语检索：
            "match_phrase": {
                "about": "rock climbing" - about属性里是不是有"rock climbing"
            }

        高亮检索：
            "query": {
                "match_phrase": {
                    "about": "rock climbing" - about属性里是不是有"rock climbing"
                }
            },
            "highlight": {
                "fields": {
                    "about": {}
                }
            }

            - 查询结果中就会有以下内容：
                "highlight": {
                    "about": [
                        "I love to go <em>rock</em> <em>climbing</em>"
                    ]
                }
            
### 13.3 SpringBoot整合ElasticSearch

    1.添加依赖

        <dependency>
            <groupId>org.springframework.boot</groupId>
            <artifactId>spring-boot-starter-data-elasticsearch</artifactId>
        </dependency>

        ※SpringBoot默认用spring-data-elasticsearch模块进行操作

        SpringBoot默认支持两种技术来和ES交互：
            ·Jest - 似乎已经不再默认支持了
            ·SpringData

    2.利用自动注入的变量进行操作

        @Autowired
        RestHighLevelClient highLevelClient;

        @Autowired
        ElasticsearchOperations elasticsearchOperations;

        目前只要指定一个地址就可以了

        spring:
            elasticsearch:
                rest:
                    uris: 192.168.229.129:9200

    ※ElasticSearch标准正在疯狂改定中。。。MappingType预定删除，接口剧烈变化