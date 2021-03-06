# querydb
通用查询数据库的组件，jar包，有前后台（js/css/字体文件）
<br>

博客地址：https://www.cnblogs.com/yuxiaole/p/9775016.html

实现：maven + servlet（3.x以上） + spring + mybatis + oracle
<br>
前端实现：vue + element ui + require.js 的 CDN 方式

### 一、实现的功能
1. 代码 down 下来，可以打包为 jar 包供其他项目使用。
<br> 配置好后，访问地址为 /querydb/index.html
2. 通过配置实现指定表查，指定字段不查。
<br>（配置文件参考：resources/querydb/example/limit.json）
3. 支持多数据源，与业务隔离。
4. 页面支持导出功能。
5. 页面支持查询条件动态组装（没有 sql 注入）。
6. 现支持 oracle 数据库。
7. 现支持 oracle 大字段类型的查询：Long、Blob、Clob

### 二、配置步骤
#### 1、引入依赖
目前版本为1.0-SNAPSHOT
<br>
##### 1.1 如果这个包在仓库里，则添加如下依赖
```xml
<dependency>
    <groupId>com.yule</groupId>
    <artifactId>querydb</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
##### 1.2 如果需要将包放在工程里，则添加如下依赖
假设这个 jar 包放在 WEB-INF/lib 文件夹下。
```xml
<!--自定义查询组件的jar包-->
<dependency>
    <groupId>com.yule</groupId>
    <artifactId>querydb</artifactId>
    <version>1.0-SNAPSHOT</version>
    <scope>system</scope>
    <systemPath>${project.basedir}/src/main/webapp/WEB-INF/lib/querydb-1.0-SNAPSHOT.jar</systemPath>
</dependency>
```
然后添加插件
```xml
<!--引用工程jar包-->
<plugin>
    <groupId>org.apache.maven.plugins</groupId>
    <artifactId>maven-dependency-plugin</artifactId>
    <version>2.0</version>
    <executions>
        <execution>
            <id>copy-dependencies</id>
            <phase>compile</phase>
            <goals>
                <goal>copy-dependencies</goal>
            </goals>
            <configuration>
                <outputDirectory>${project.basedir}/WebContent/WEB-INF/lib</outputDirectory>
                <includeScope>system</includeScope>
            </configuration>
        </execution>
    </executions>
</plugin>
```
#### 2、配置 spring 的上下文
如果是 springMVC 框架，则在 springMVC 的配置文件中加如下配置：
```xml
    <!--设置 spring 上下文 querydb-->
    <bean class="com.yule.querydb.utils.SpringContextHolder"/>
```
#### 3、配置 spring 与 mybatis
在 web.xml 中添加如下配置：
```xml
  <context-param>
    <param-name>contextConfigLocation</param-name>
    <param-value>classpath*:querydb/conf/spring-mybatis-querydb-double.xml</param-value>
  </context-param>
  <listener>
    <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>
  </listener>
```
##### 3.1 这里给出了两个默认的配置文件路径：
###### 1. querydb/conf/spring-mybatis-querydb-double.xml
表示配置双数据源，数据源的配置文件路径默认为：**conf/system/querydb.properties**
<br>
配置示例：
```properties
first.db.driver=oracle.jdbc.OracleDriver
first.db.datasourceurl=jdbc:oracle:thin:@localhost:1521:ORCL
first.db.username=testdev
first.db.password=test1234

second.db.driver=oracle.jdbc.OracleDriver
second.db.datasourceurl=jdbc:oracle:thin:@localhost:1521:ORCL
second.db.username=testdev2
second.db.password=test2
```
###### 2. querydb/conf/spring-mybatis-querydb-single.xml
表示配置单数据源，数据源的配置文件路径默认为：**conf/system/querydb.properties**
<br>
配置示例：
```properties
first.db.driver=oracle.jdbc.OracleDriver
first.db.datasourceurl=jdbc:oracle:thin:@localhost:1521:ORCL
first.db.username=testdev
first.db.password=test1234
```
##### 3.2 配置 spring-mybatis-querydb.xml 文件（如果需要自定义数据源）<br>
建议参考 querydb/conf/spring-mybatis-querydb-single.xml 或者 querydb/conf/spring-mybatis-querydb-double.xml 文件进行自定义配置。

#### 4、配置 servlet
##### 4.1 配置数据源的查询表限制 json 文件路径（以下简称配置一）
这个配置放在步骤 4.2 的配置文件里。
<br>
这里的 dbComponentDataSources 指的是定义的数据源 id，必须与步骤 3 中的定义一致。
<br>
这里的 dbComponentDataSourceNames 指的是定义的数据源名字，前端显示，与 dbComponentDataSources 对应。
<br>
这里的 dbComponentDataSourceLimitJsonUrls 指的是限制 json 文件的路径。
<br>
如果使用的是默认的配置，则这里给出示例：
```properties
#单数据源 与配置 querydb/conf/spring-mybatis-querydb-single.xml 一致
#单表查询组件的数据源切换
dbComponentDataSources=firstDb
dbComponentDataSourceNames=数据源一
#表数据私密性设置，对应 dbComponentDataSources 数据源
dbComponentDataSourceLimitJsonUrls=conf/dbcomponent/limit.json
```
```properties
#双数据源 与配置 querydb/conf/spring-mybatis-querydb-double.xml 一致
#单表查询组件的数据源切换
dbComponentDataSources=firstDb,secondDb
dbComponentDataSourceNames=数据源一,数据源二
#表数据私密性设置，对应 dbComponentDataSources 数据源
dbComponentDataSourceLimitJsonUrls=conf/dbcomponent/limit.json,conf/dbcomponent/limit2.json
```
##### 4.2 在 web.xml 文件中配置，如果使用 springMVC 框架，则启动顺序在 springMVC 之后；
这里可以指定配置一的配置文件路径，如果没有指定，则默认路径为"/conf/system/querydb.properties"；
```xml
  <servlet>
    <servlet-name>querydb</servlet-name>
    <servlet-class>com.yule.querydb.servlet.QueryDbDispatcherServlet</servlet-class>
    <!--启动加载的顺序-->
    <load-on-startup>2</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>querydb</servlet-name>
    <!--“/”: 会拦截所有请求，包括js、jsp、html等-->
    <url-pattern>/querydb/*</url-pattern>
  </servlet-mapping>
```
自定义设置配置文件的路径
```xml
  <servlet>
    <servlet-name>querydb</servlet-name>
    <servlet-class>com.yule.querydb.servlet.QueryDbDispatcherServlet</servlet-class>
    <init-param>
      <!--可以自定义设置配置文件的路径-->
      <param-name>propertiesPath</param-name>
      <param-value>/conf/system/querydb.properties</param-value>
    </init-param>
    <!--启动加载的顺序-->
    <load-on-startup>2</load-on-startup>
  </servlet>
  <servlet-mapping>
    <servlet-name>querydb</servlet-name>
    <!--“/”: 会拦截所有请求，包括js、jsp、html等-->
    <url-pattern>/querydb/*</url-pattern>
  </servlet-mapping>
```
#### 5、配置表查询的限制： limit.json
示例1
```javaScript
//配置查询表的限制；默认空数组 []；
//举例：canQueryTables 表示可以查询的表，必填
//forbidQueryColumns 表示禁止查询的表字段，可以为空，可以没有这个字段
{
  "canQueryTables": ["t_user"],
  "forbidQueryColumns": [
    {
      "tableName": "t_user",
      "tableColumns": ["id"]
    }
  ]
}
```
示例2
```javaScript
{
}
```
示例3
```javaScript
{
    "canQueryTables": ["t_user", "t_book"]
}
```
