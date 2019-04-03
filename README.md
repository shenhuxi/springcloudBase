- dingxin-file-server 8210
- dingxin-eureka 8220
- dingxin-config 8230
- dingxin-gateway 8240
- dingxin-oauth-server 8250
- dingxin-system-server 8260

**swagger2配置步骤**
#### 1、微服务pom 添加包
```xml
<!--swagger2-->
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger2</artifactId>
    <version>${swagger2.version}</version>
</dependency>
<dependency>
    <groupId>io.springfox</groupId>
    <artifactId>springfox-swagger-ui</artifactId>
    <version>${swagger2.version}</version>
</dependency>
<!--swagger2-->
```

#### 2、网关添加一行
DocumentationConfig 类中添加一行，/clue 改成自己的
resources.add(swaggerResource("线索管理", "/clue/v2/api-docs", "2.0"));

#### 3、微服务swagger配置
参考clue微服务的\com\dingxin\config包下的两个类配置，拷贝到自己的微服务，修改成自己的配置

#### 4、访问：http://localhost:8240/api/swagger-ui.html#/


#### init SQL
set  IDENTITY_INSERT SYS_USER on
INSERT INTO SYS_USER (id,user_name, PASS_WORD,ORG_ID,USER_STATE) VALUES (1,'admin', 'f379eaf3c831b04de153469d1bec345e',1,1);
INSERT INTO SYS_USER (id,user_name, PASS_WORD,ORG_ID,USER_STATE) VALUES (2,'abel', 'f379eaf3c831b04de153469d1bec345e',1,1);

set  IDENTITY_INSERT SYS_ROLE on
INSERT INTO SYS_ROLE(id,NAME) VALUES(1,'ROLE_ADMIN');
INSERT INTO SYS_ROLE(id,NAME) VALUES(2,'ROLE_USER');

INSERT INTO SYS_ROLE_USER(SYS_USER_ID,sys_role_id) VALUES(1,1);
INSERT INTO SYS_ROLE_USER(SYS_USER_ID,sys_role_id) VALUES(2,2);

set  IDENTITY_INSERT Sys_permission_role on
INSERT INTO Sys_permission_role(id,role_id ,permission_id) VALUES ('1', '1', '1'), ('2', '1', '2'), ('3', '2', '1');
INSERT INTO Sys_permission_role(id,role_id ,permission_id) VALUES('4', '1', '3'), ('5', '1', '4'), ('6', '1', '5');
INSERT INTO Sys_permission_role(id,role_id ,permission_id) VALUES ('7', '1', '6'), ('8', '2', '3');

set  IDENTITY_INSERT Sys_permission on
INSERT INTO  Sys_permission(id,name,descritpion,url,pid,method) VALUES ('1', 'ROLE_HOME', 'home', '/', 0,'GET'), ('2', 'ROLE_ADMIN', 'ABel', '/admin', 0,'POST'),('3', 'ROLE_USER_GET', 'user', '/user/**', 0,'GET'),('4', 'ROLE_USER_POST', 'user', '/user/**', 0,'POST'),('5', 'ROLE_USER_PUT', 'user', '/user/**', 0,'PUT'),('6', 'ROLE_USER_ALL', 'user', '/user/**', 0,'ALL');

