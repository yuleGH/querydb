# querydb
通用查询数据库的组件，jar包，有前后台（js/css/字体文件）

实现：maven + servlet + spring + mybatis

需要放心的是，没有 sql 注入！！！

### 实现的功能
1. 代码 down 下来，可以打包为 jar 包供其他项目使用。
<br> 配置好后，访问地址为 /querydb/index.html
2. 通过配置实现指定表查，指定字段不查。
<br>（配置文件参考：resources/querydb/example/limit.json）
3. 支持多数据源。
<br>（配置文件参考：resources/querydb/example/db.properties，resources/querydb/example/spring-mybatis-querydb.xml）
4. 页面支持导出功能。
5. 页面支持查询条件动态组装。
<br>（参考类：com.yule.querydb.commonenum.CustomConditionEnum）
6. 现支持 oracle 数据库。