# querydb
通用查询数据库的组件，jar包，有前后台（js/css/字体文件）

实现：maven + servlet + spring + mybatis

### 实现的功能
1. 代码 down 下来，可以打包为 jar 包供其他项目使用。
<br> 配置好后，访问地址为 /querydb/index.html
2. 通过配置实现指定表查，指定字段不查。
<br>（配置文件参考：resources/querydb/example/limit.json）
3. 支持多数据源，与业务隔离。
<br>（配置文件参考：resources/querydb/example/db.properties，resources/querydb/example/spring-mybatis-querydb.xml）
4. 页面支持导出功能。
5. 页面支持查询条件动态组装（没有 sql 注入）。
<br>（参考类：com.yule.querydb.commonenum.CustomConditionEnum）
6. 现支持 oracle 数据库。
7. 现支持 oracle 大字段类型：Long、Blob、Clob

### 配置步骤
1. 加 pom 文件
2. 配置 web.xml 文件
3. 配置 spring-mybatis-querydb.xml 文件（如果需要自定义数据源）
4. 配置 querydb.properties 文件
5. 配置 spring-mvc.xml 文件

### 待解决问题
1. 导出 jar 包冲突  csv文件
5. 写配置文档