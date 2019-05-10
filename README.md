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



