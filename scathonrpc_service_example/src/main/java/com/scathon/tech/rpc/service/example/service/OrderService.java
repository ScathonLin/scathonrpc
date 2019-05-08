package com.scathon.tech.rpc.service.example.service;

import com.scathon.tech.rpc.common.annotations.RpcService;
import com.scathon.tech.rpc.service.example.entity.User;

/**
 * TODO Function The Class Is.
 *
 * @ClassName OrderService.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/8
 * @Version 1.0
 */
@RpcService(name = "orderService")
public interface OrderService {
    String findOrderByUserInfo(User user,int paramInt,String paramString);
}
