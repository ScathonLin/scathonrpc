# 简易RPC框架实现

### 技术选型：Netty，Zookeeper，Spring
> 思想借鉴了网上有一些Demo的设计思路，但是网上的demo的使用方式不太符合spring的直观用法，本项目最终的实现效果就是，对于服务化接口的RPC调用实现自动注入.

### 模块构成
- scathonrpc_client
> 客户端RPC请求功能的封装

- scathonrpc_server
> rpc服务端功能的封装，包括服务发布等功能.

- scathonrpc_registry
> rpc服务注册模块，目前使用zookeeper作为服务注册中心.

- scathonrpc_common
> 框架的共有工具以及逻辑的封装.

- scathonrpc_client_simple
> 客户端测试模块.

- scathonrpc_server_simple
> 服务端测试模块

- scathonrpc_service_example
> API 定义模块，主要是业务Service抽象出的接口.

### 使用方法

- 1. API模块的定义 假设模块名字是 scathonrpc_service_example
> 发布的接口中，加入@RpCService注解，并且制定将要发布的接口服务的名称.
- example 1:
```
@RpcService(name = "userService")
public interface UserService {
    User findUserById(int id);
}
```

```
@RpcService(name = "orderService")
public interface OrderService {
    String findOrderByUserInfo(User user,int paramInt,String paramString);
}
```


- 2.  客户端开发依赖

1. 添加pom依赖
``` 
<!-- 必须依赖 -->
 <dependency>
    <groupId>com.scathon.tech</groupId>
    <artifactId>scathonrpc_client</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
<!-- 项目中定义的业务Service抽象的接口模块，实际中模块名称自定义 -->
 <dependency>
    <groupId>com.scathon.tech</groupId>
    <artifactId>scathonrpc_service_example</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```
2. 配置文件

填写zookeeper的地址，逗号分隔：
```
register.component.type=zookeeper
zookeeper.cluster.addresses=127.0.0.1:2181
zookeeper.session.timeout.ms=60000
```

3. API

客户端类上面加上@RpcServiceSubscriber 注解，表明该类需要订阅RPC服务，spring会自动扫描装配这个类，同时，容器启动的时候，会自动扫描该类中被@RpcAutowired 注解修饰的服务抽象接口的字段，并且自动注入一个服务请求代理类，使用起来就像是Spring @Autowired 注解自动装配一样,业务方法中，就可以调用接口中定义的方法了。

```
/**
 * @author linhd
 */
@RpcServiceSubscriber
public class UserServiceClient {

    @RpcAutowired
    private UserService userService;

    @RpcAutowired
    private OrderService orderService;

    public String findUserById(int id) {
        User user = userService.findUserById(id);
        return user.toString();
    }

    public String findOrderByUserInfo() {
        User user = User.builder().username("hello").id(888).build();
        return orderService.findOrderByUserInfo(user, 666, "999");
    }

}
```

- 3. 服务端（假设是：scathonrpc_server_simple）

1. pom文件添加依赖
```
<!-- 依赖服务接口定义模块（api模块） -->
 <dependency>
    <groupId>com.scathon.tech</groupId>
    <artifactId>scathonrpc_service_example</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
<!-- 服务端rpc封装模块 -->
<dependency>
    <groupId>com.scathon.tech</groupId>
    <artifactId>scathonrpc_server</artifactId>
    <version>1.0-SNAPSHOT</version>
</dependency>
```

2. 业务功能的实现（本例中，是把业务接口的实现在服务端模块scathonrpc_server_simple实现的，实际中，可能会有单独一个模块实现API接口，那么就需要在pom文件中将这个自定义模块引入）
```
@RpcServicePublisher(name = "userService")
public class UserServiceImpl implements UserService {
    @Override
    public User findUserById(int id) {
        return User.builder().id(1).username("linhd").build();
    }
}

@RpcServicePublisher(name = "orderService")
public class OrderServiceImpl implements OrderService {
    @Override
    public String findOrderByUserInfo(User user, int paramInt, String paramString) {
        return JSONObject.toJSONString(user) + paramInt + paramString;
    }
}

```
> 如上实现，在容器启动的时候，会自动发现服务，并且将服务发布到注册中心，供客户端订阅服务.

3. 配置文件
```
# netty 监听的rpc服务端口(要配置！)
server.provider.ips.addrs=127.0.0.1:8081
register.component.type=zookeeper
zookeeper.cluster.addresses=127.0.0.1:2181
zookeeper.session.timeout.ms=60000
```
与客户端不同的是，要添加RPC服务绑定的端口和IP配置.
