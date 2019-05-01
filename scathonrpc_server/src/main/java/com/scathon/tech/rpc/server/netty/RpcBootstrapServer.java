package com.scathon.tech.rpc.server.netty;

import com.scathon.tech.rpc.common.conf.RpcProperties;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * netty rpc server.
 *
 * @ClassName RpcBootstrapServer.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/1
 * @Version 1.0
 */
@Component
public class RpcBootstrapServer {

    @Autowired
    private RpcProperties rpcProperties;

    public void bootstrap() {

    }

}
