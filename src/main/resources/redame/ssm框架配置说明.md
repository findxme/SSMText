### ssm框架整合

首先明确一点就是，web项目的入库是在web.xml下的，所有的配置文件无论怎么注入都要最终到这个配置文件里面（日志文件除外）

首先配置

#### pom.xml

（springframework整合spring框架，整合mybatis框架，mysql驱动，druid连接池，整合log4j，ackson Json处理工具包，Servlet/JSP/JSTL，单元测试，Spring对JDBC数据访问进行封装的所有类）

~~~xml
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/xsd/maven-4.0.0.xsd">
  <modelVersion>4.0.0</modelVersion>
  <groupId>com.zxl</groupId>
  <artifactId>test</artifactId>
  <version>0.0.1-SNAPSHOT</version>
  <packaging>war</packaging>

  <!-- 集中定义依赖版本号 -->
  <properties>
    <junit.version>4.10</junit.version>
    <spring.version>4.1.3.RELEASE</spring.version>
    <mybatis.version>3.2.8</mybatis.version>
    <mybatis.spring.version>1.2.2</mybatis.spring.version>
    <mysql.version>5.1.32</mysql.version>
    <druid.version>1.1.6</druid.version>
    <slf4j.version>1.6.4</slf4j.version>
    <jstl.version>1.2</jstl.version>
    <servlet-api.version>2.5</servlet-api.version>
    <jsp-api.version>2.0</jsp-api.version>
    <jackson.version>2.4.2</jackson.version>
  </properties>

  <dependencies>
    <!-- 单元测试 -->
    <dependency>
      <groupId>junit</groupId>
      <artifactId>junit</artifactId>
      <version>${junit.version}</version>
      <scope>test</scope>
    </dependency>
    <!-- 整合spring框架（包含springmvc） 这个jar文件包含springmvc开发时的核心类, 同时也会将依赖的相关jar文件引入进来(spring的核心jar文件也包含在内) -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-webmvc</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <!--这个jar文件包含对Spring对JDBC数据访问进行封装的所有类 -->
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-jdbc</artifactId>
      <version>${spring.version}</version>
    </dependency>
    <dependency>
      <groupId>org.springframework</groupId>
      <artifactId>spring-aspects</artifactId>
      <version>${spring.version}</version>
    </dependency>

    <!-- 整合mybatis框架 -->
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis</artifactId>
      <version>${mybatis.version}</version>
    </dependency>
    <dependency>
      <groupId>org.mybatis</groupId>
      <artifactId>mybatis-spring</artifactId>
      <version>${mybatis.spring.version}</version>
    </dependency>
    <!-- mysql驱动 -->
    <dependency>
      <groupId>mysql</groupId>
      <artifactId>mysql-connector-java</artifactId>
      <version>${mysql.version}</version>
    </dependency>
    <!-- druid连接池 -->
    <dependency>
      <groupId>com.alibaba</groupId>
      <artifactId>druid</artifactId>
      <version>${druid.version}</version>
    </dependency>

    <!-- 整合log4j -->
    <dependency>
      <groupId>org.slf4j</groupId>
      <artifactId>slf4j-log4j12</artifactId>
      <version>${slf4j.version}</version>
    </dependency>
    <!-- Jackson Json处理工具包 -->

    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->
    <dependency>
      <groupId>com.fasterxml.jackson.core</groupId>
      <artifactId>jackson-databind</artifactId>
      <version>2.10.0.pr1</version>
    </dependency>


    <!-- Servlet/JSP/JSTL -->
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>servlet-api</artifactId>
      <version>${servlet-api.version}</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>javax.servlet</groupId>
      <artifactId>jsp-api</artifactId>
      <version>${jsp-api.version}</version>
      <scope>provided</scope>
    </dependency>
    <dependency>
      <groupId>jstl</groupId>
      <artifactId>jstl</artifactId>
      <version>${jstl.version}</version>
    </dependency>

  </dependencies>
</project>
~~~

#### web.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<web-app xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
         xmlns="http://java.sun.com/xml/ns/javaee"
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee http://java.sun.com/xml/ns/javaee/web-app_2_5.xsd"
         version="2.5">
  <!--项目名字-->
  <display-name>SSMDemo</display-name>
  <!--项目主页寻找地址-->
  <welcome-file-list>
    <welcome-file>index.html</welcome-file>
    <welcome-file>index.htm</welcome-file>
    <welcome-file>index.jsp</welcome-file>
    <welcome-file>default.html</welcome-file>
    <welcome-file>default.htm</welcome-file>
    <welcome-file>default.jsp</welcome-file>
  </welcome-file-list>

  <!-- 配置前端控制器 -->
  <servlet>
    <servlet-name>springmvc</servlet-name>
    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
    <!--使用通配符加载spring容器这里不仅仅加载了springmvc也加载了applicationContext.xml等什么的-->
    <init-param>
      <param-name>contextConfigLocation</param-name>
      <param-value>classpath:spring/*.xml</param-value>
    </init-param>

  </servlet>
  <!--拦截所有的请求-->
  <servlet-mapping>
    <servlet-name>springmvc</servlet-name>
    <url-pattern>/</url-pattern>
  </servlet-mapping>


~~~



#### applicationContext.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:context="http://www.springframework.org/schema/context" xmlns:p="http://www.springframework.org/schema/p"
       xmlns:aop="http://www.springframework.org/schema/aop" xmlns:tx="http://www.springframework.org/schema/tx"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
	http://www.springframework.org/schema/context http://www.springframework.org/schema/context/spring-context-4.0.xsd
	http://www.springframework.org/schema/aop http://www.springframework.org/schema/aop/spring-aop-4.0.xsd http://www.springframework.org/schema/tx http://www.springframework.org/schema/tx/spring-tx-4.0.xsd
	http://www.springframework.org/schema/util http://www.springframework.org/schema/util/spring-util-4.0.xsd">

    <!-- 1.加载jdbc.properties文件的位置 -->
    <context:property-placeholder location="classpath:db.properties" />

    <!-- 2.配置durid连接池, id是固定值, class是druid连接池类的全路径 -->
    <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
        <!-- 配置连接数据库的基本信息 -->
        <property name="driverClassName" value="${db.driverClassName}"></property>
        <property name="url" value="${db.url}"></property>
        <property name="username" value="${db.username}"></property>
         <property name="password" value="${db.password}"></property>
    </bean>

<!--    &lt;!&ndash;事务管理器，对mybatis操作数据库事务控制，spring使用jdbc的事务控制类&ndash;&gt;-->
<!--    <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">-->
<!--        &lt;!&ndash;数据源&ndash;&gt;-->
<!--        <property name="dataSource" ref="dataSource"/>-->

<!--    </bean>-->



<!--    &lt;!&ndash;通知也叫传播行为  transaction-manager通知给事务管理器&ndash;&gt;-->
<!--    <tx:advice id="txAdvice" transaction-manager="transactionManager">-->
<!--        <tx:attributes>-->
<!--            <tx:method name="save*" propagation="REQUIRED"/>-->
<!--            <tx:method name="delete*" propagation="REQUIRED"/>-->
<!--            <tx:method name="insert*" propagation="REQUIRED"/>-->
<!--            <tx:method name="update*" propagation="REQUIRED"/>-->
<!--            &lt;!&ndash;支持事务。码晕就算了，这个是只读&ndash;&gt;-->
<!--            <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>-->
<!--            <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>-->
<!--            <tx:method name="select*" propagation="SUPPORTS" read-only="true"/>-->
<!--        </tx:attributes>-->

<!--    </tx:advice>-->
<!--&lt;!&ndash;aop事务控制&ndash;&gt;-->
<!--    <aop:config>-->
<!--        &lt;!&ndash;切点，切om.wtu.下的service下的impl下的*（所有类的）*（所有方法）（..）不管什么参数  和下面的pointcut一一对应&ndash;&gt;-->
<!--        <aop:advisor advice-ref="txAdvice" pointcut="execution(* com.wtu.service.impl.*.*(..))"/>-->
<!--    </aop:config>-->


    <!-- 3.整合Spring和mybatis框架 将SqlSession等对象的创建交给Spring容器 id值(sqlSessionFactory)是固定值 -->
    <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
        <!-- 3.1 指定mybatis核心配置文件的位置 -->
        <property name="configLocation" value="classpath:mybatis/mybatis-config.xml" />
        <!-- 3.2 配置连接池(数据源) ref指向连接池bean对象的id值 -->
        <property name="dataSource" ref="dataSource" />
        <!-- 3.3 扫描所有的XxxMapper.xml映射文件, 读取其中配置的SQl语句 -->
        <property name="mapperLocations" value="classpath:mybatis/mapper/*.xml" />
    </bean>


    <!-- 4. 定义mapper接口扫描器 配置所有mapper接口所在的包路径, 将来由Spring + myBatis框架来为接口 提供实现类,
        以及实现类的实例也有Spring框架来创建 -->
    <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
        <!-- 扫描所有XxxMapper接口, 将接口实例的创建交给Spring容器 -->
        <property name="basePackage" value="com.wtu.dao" />
        <!--猜测加上之后，可以通过XxxMappe.xml和XxxMapper.java自动匹配，要在同一个目录下-->
<!--        <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>-->
    </bean>

    <!-- 5.配置需要扫描的包(server层): Spring自动去扫描base-package下的类 如果扫描到的类上有@Controller,@Service,@Component等注解,
        将会自动 将类注册为bean(即由Spring创建实例) -->
    <context:component-scan base-package="com.wtu.service">
    </context:component-scan>

</beans>
~~~

#### springmvc-config.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:mvc="http://www.springframework.org/schema/mvc"
       xmlns:context="http://www.springframework.org/schema/context"
       xsi:schemaLocation="http://www.springframework.org/schema/mvc
						http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd
						http://www.springframework.org/schema/beans
						http://www.springframework.org/schema/beans/spring-beans-4.0.xsd
						http://www.springframework.org/schema/context
          				http://www.springframework.org/schema/context/spring-context-4.0.xsd">

    <!-- 1.配置前端控制器访问静态资源(html/css/js等,否则静态资源将无法访问) -->
    <mvc:default-servlet-handler />

    <!-- 2.配置注解驱动,用于识别注解(比如@Controller) -->
    <mvc:annotation-driven></mvc:annotation-driven>

            <!-- 3.配置需要扫描的包: Spring自动去扫描base-package 下的类, 如果扫描到的类上有@Controller,@Service,@Component等注解
                将会自动将类注册为bean -->
    <context:component-scan base-package="com.wtu.controller">
    </context:component-scan>

    <!-- 4.配置内部资源视图解析器 prefix: 配置路径前缀 suffix: 配置文件后缀 -->
    <bean
            class="org.springframework.web.servlet.view.InternalResourceViewResolver">
        <property name="prefix" value="/WEB-INF/pages/" />
        <property name="suffix" value=".jsp" />
    </bean>
    <!-- 对静态资源的访问 -->
    <mvc:resources location="/static/" mapping="/static/**" />

</beans>

~~~

#### mybatis-config.xml

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">

<!-- MyBatis的全局配置文件 -->
<configuration >
    <!-- 整合mybatis和Spring框架, 需要将mybatis的相关配置
        在Spring的配置文件中进行配置
     -->
</configuration>
~~~

#### db.properties

~~~properties
db.driverClassName=com.mysql.jdbc.Driver
db.url=jdbc:mysql://localhost:3306/myweb?characterEncoding=utf-8
db.username=root
db.password=123456
~~~





#### 总结：

1.pom.xml配置如下

（springframework整合spring框架，整合mybatis框架，mysql驱动，druid连接池，整合log4j，ackson Json处理工具包，Servlet/JSP/JSTL，单元测试，Spring对JDBC数据访问进行封装的所有类）

2.web.xml配置如下

 >项目名字
 >
 >项目主页寻找地址
 >
 >前端控制器（加载spring容器，在这个同时也加载springmvc）前端控制器就是用的DispatcherServlet
 >
 >然后加载spring容器，我把spring所有的文件全部放在看spring下面，所有我用通配符就行可以了
 >
 >~~~xml
 >  <!-- 配置前端控制器 -->
 >  <servlet>
 >    <servlet-name>springmvc</servlet-name>
 >    <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>
 >    <!--使用通配符加载spring容器这里不仅仅加载了springmvc也加载了applicationContext.xml等什么的-->
 >    <init-param>
 >      <param-name>contextConfigLocation</param-name>
 >      <param-value>classpath:spring/*.xml</param-value>
 >    </init-param>
 >~~~
 >
 >
 >
 >拦截所有请求
 >
 >~~~xml
 >  <!--拦截所有的请求-->
 >  <servlet-mapping>
 >    <servlet-name>springmvc</servlet-name>
 >    <url-pattern>/</url-pattern>
 >  </servlet-mapping>
 >
 >~~~
 >
 >

applicationContext.xml配置如下

> 配置jdbc，先加载jdbc连接配置文件的位置
>
> ```xml
> <!-- 1.加载jdbc.properties文件的位置 -->
>     <context:property-placeholder location="classpath:db.properties" />
> 
> ```
>
> 
>
> 配置连接池durid，和c3p0等都可以
>
> ~~~xml
> 
>     <!-- 2.配置durid连接池, id是固定值, class是druid连接池类的全路径 -->
>     <bean id="dataSource" class="com.alibaba.druid.pool.DruidDataSource">
>         <!-- 配置连接数据库的基本信息 -->
>         <property name="driverClassName" value="${db.driverClassName}"></property>
>         <property name="url" value="${db.url}"></property>
>         <property name="username" value="${db.username}"></property>
>          <property name="password" value="${db.password}"></property>
>     </bean>
> ~~~
>
> 
>
> 
>
> （配置事务管理，对mybatis操作数据库事务控制，spring使用jdbc的事务控制类）（此项目前不清楚是做什么的，不配置也可以）
>
> ~~~xml
> 
>   <!--事务管理器，对mybatis操作数据库事务控制，spring使用jdbc的事务控制类-->
>     <bean id="transactionManager" class="org.springframework.jdbc.datasource.DataSourceTransactionManager">
>         <!--数据源-->
>         <property name="dataSource" ref="dataSource"/>
> 
>     </bean>
> 
> ~~~
>
> 
>
> （传播行为  transaction-manager通知给事务管理器）此项目前不清楚是做什么的，不配置也可以）猜测是用来规范编码，的在接口方法命名上要符合这个的规则
>
> ~~~xml
> <!--通知也叫传播行为  transaction-manager通知给事务管理器-->
>     <tx:advice id="txAdvice" transaction-manager="transactionManager">
>         <tx:attributes>
>             <tx:method name="save*" propagation="REQUIRED"/>
>             <tx:method name="delete*" propagation="REQUIRED"/>
>             <tx:method name="insert*" propagation="REQUIRED"/>
>             <tx:method name="update*" propagation="REQUIRED"/>
>             <!--支持事务。码晕就算了，这个是只读-->
>             <tx:method name="find*" propagation="SUPPORTS" read-only="true"/>
>             <tx:method name="get*" propagation="SUPPORTS" read-only="true"/>
>             <tx:method name="select*" propagation="SUPPORTS" read-only="true"/>
>         </tx:attributes>
> 
>     </tx:advice>
> ~~~
>
> 
>
> （事务控制）
>
> ~~~xml
> <!--aop事务控制-->
>     <aop:config>
>         <!--切点，切om.wtu.下的service下的impl下的*（所有类的）*（所有方法）（..）不管什么参数  和下面的pointcut一一对应-->
>     <aop:advisor advice-ref="txAdvice" pointcut="execution(* com.wtu.service.impl.*.*(..))"/>
>     </aop:config>
> ~~~
>
> 
>
> 整合spring和mybatis框架，将SqlSessionFactory交个spring容器，读取xml中的sql语句，然后交个spring来管理
>
> ~~~xml
>    <!-- 3.整合Spring和mybatis框架 将SqlSession等对象的创建交给Spring容器 id值(sqlSessionFactory)是固定值 -->
>     <bean id="sqlSessionFactory" class="org.mybatis.spring.SqlSessionFactoryBean">
>         <!-- 3.1 指定mybatis核心配置文件的位置 -->
>         <property name="configLocation" value="classpath:mybatis/mybatis-config.xml" />
>         <!-- 3.2 配置连接池(数据源) ref指向连接池bean对象的id值 -->
>         <property name="dataSource" ref="dataSource" />
>         <!-- 3.3 扫描所有的XxxMapper.xml映射文件, 读取其中配置的SQl语句 -->
>         <property name="mapperLocations" value="classpath:mybatis/mapper/*.xml" />
>     </bean>
> ~~~
>
> 
>
> 定义mapper接口扫描器，配置所有mapper的包路径，将来由spring+mybatis框架为接口来提供服务(扫描dao层)
>
> ~~~xml
>    <!-- 4. 定义mapper接口扫描器 配置所有mapper接口所在的包路径, 将来由Spring + myBatis框架来为接口 提供实现类,
>         以及实现类的实例也有Spring框架来创建 -->
>     <bean class="org.mybatis.spring.mapper.MapperScannerConfigurer">
>         <!-- 扫描所有XxxMapper接口, 将接口实例的创建交给Spring容器 -->
>         <property name="basePackage" value="com.wtu.dao" />
>         <!--猜测加上之后，可以通过XxxMappe.xml和XxxMapper.java自动匹配，要在同一个目录下-->
> <!--   <property name="sqlSessionFactoryBeanName" value="sqlSessionFactory"/>-->
>     </bean>
> ~~~
>
> 
>
> 配置要扫描的service层的包，spring去自动扫描类，扫描注解（扫描service层）
>
> ~~~xml
>  <!-- 5.配置需要扫描的包(server层): Spring自动去扫描base-package下的类 如果扫描到的类上有@Controller,@Service,@Component等注解,
>         将会自动 将类注册为bean(即由Spring创建实例) -->
>     <context:component-scan base-package="com.wtu.service">
>     </context:component-scan>
> ~~~
>
> 



springmvc-config.xml

> 配置注解驱动，用于识别注解，比如@controller@RequestMapping等
>
> ~~~xml
> <mvc:annotation-driven></mvc:annotation-driven>
> <!--这个就相当于做完了处理器映射器和处理器适配器-->
> ~~~
>
> 配置需要去自动扫描的包，sprig就会去自动扫描对应下面的注解
>
> ~~~xml
> <context:component-scan base-package="com.wtu.controller">
>     </context:component-scan>
> ~~~
>
> 视图解析器prefix配置路径前缀，suffix配置文件后缀
>
> ~~~xml
>   <bean
>             class="org.springframework.web.servlet.view.InternalResourceViewResolver">
>         <property name="prefix" value="/WEB-INF/pages/" />
>         <property name="suffix" value=".jsp" />
>     </bean>
> ~~~
>
> 配置静态资源扫描。对于访问这个文件夹下面的资源就放行
>
> ~~~xml
>  <mvc:resources location="/static/" mapping="/static/**" />
> ~~~

mybatis-config.xml

> 此文件已经被spring整合了里面本来应该配置数据链接信息，和数据连接池。然后包这个文件注入spring中，现在我直接放到了applicationContext中所有这个文件暂时没有用

#### mybatis

放在resource下面，然后可以自动扫描到

~~~xml
<?xml version="1.0" encoding="UTF-8"?>
<!--xml在头标签中配置与mybatis层的连接-->
<!DOCTYPE configuration
        PUBLIC "-//mybatis.org//DTD Config 3.0//EN"
        "http://mybatis.org/dtd/mybatis-3-config.dtd">
<configuration>
    <environments default="dev">
        <environment id="dev">
            <transactionManager type="JDBC"></transactionManager>
            <!--链接属性数据-->
            <dataSource type="POOLED">
                <property name="driver" value="com.mysql.jdbc.Driver"/>
                <property name="url" value="jdbc:mysql://localhost:3306/myweb?characterEncoding=utf-8"/>
                <property name="username" value="root"/>
                <property name="password" value="123456"/>
            </dataSource>
        </environment>
    </environments>
    <!--配置映射文件位置-->
    <mappers>
        <mapper resource="mapper/user.xml"></mapper>
    </mappers>
</configuration>
~~~

工具类，创建工厂的，返回session

~~~java
    public SqlSession openMyBatis(){
        /*确定指令连接对象*/
        SqlSessionFactory sqlSessionFactory;
        /*sqlsession为指令执行对象*/
        SqlSession session=null;
        String resources= "mybatis.xml";
        try {
            InputStream inputStream = Resources.getResourceAsStream(resources);
            /*如果配置文件正常加载，就开始连接数据库*/
            sqlSessionFactory =new SqlSessionFactoryBuilder().build(inputStream);
            /*sql指令执行环节session调用工具方法，实例化成功
             * 获取到dao层接口，并实例化*/
            session= sqlSessionFactory.openSession();

        }catch (Exception e){
            e.printStackTrace();
            System.out.println("mybatis实例化失败");
        }
        return session;
    }
    public static void closeMybatis(SqlSession sqlSession){
        if (sqlSession!=null){
            sqlSession.close();
        }
    }
~~~



具体实现类

~~~java
   public void LoginServlet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        String name = request.getParameter("fAccount");
        String password =request.getParameter("fPassword");
        MybatisConfig mybatisConfig = new MybatisConfig();
        SqlSession session=mybatisConfig.openMyBatis();
        UserDao userDao =session.getMapper(UserDao.class);

        List<Map<String, Object>> list = userDao.selectUserAll();

        for (Map<String, Object> map : list) {
            if (map.get("name").equals(name)&&map.get("password").equals(password)){
                request.setAttribute("msg","看见我就是登录成功");
                request.getRequestDispatcher("/WEB-INF/success.jsp").forward(request,response);
                break;
            }
        }
        request.setAttribute("msg","看见我就是登录失败，失败，失败，重要的事说三遍，滚回去改代码去");
        request.getRequestDispatcher("/WEB-INF/failure.jsp").forward(request,response);

    }
~~~



调用工具类返回session，然后通过session去调用getMapper（传递接口的字节码）==所以这个就是调用的这个接口的实例化对象==

然后在根据这个对象调用相应的sql语句，执行sql语句，只需要关注两点，传入参数和返回对象



在web项目中，java代表了web的后端开发区域

com.stu.ssm代表了项目的主题包，所有的自保都要在主题包中简历

1.base：项目的前端板块，客户浏览板块，给网页浏览者查看的区域板块，一般不需要账户登录，可直接查看

2.basemanage：项目的后端板块，管理员运营板块，有相关盈利机构组织负责人员进行账号登陆权限管理，对base客户浏览页面板块进行及时的数据文字报道，视频，音频，图像，消费品广告信息进行上下架更新管理

3.filter：web项目的过滤器，对整套系统安全性方木啊的数据过滤，请求过滤，(后端控制层的)

4.interceptor web项目的拦截器，对访问jsp及整套html的请求，在访问到页面之后，进行结果反弹，重定向登陆页面

--注意，一般过滤器和拦截器都设定在basemanage后端中--

5.resource，是对整个web项目的spring框架配置xml文件存储的空间

6.util自定义工具包