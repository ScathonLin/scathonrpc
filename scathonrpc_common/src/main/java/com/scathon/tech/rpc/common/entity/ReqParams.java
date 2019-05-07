package com.scathon.tech.rpc.common.entity;

import lombok.experimental.Accessors;

import java.util.Collection;
import java.util.LinkedList;
import java.util.List;

/**
 * TODO Function The Class Is.
 *
 * @ClassName ReqParams.
 * @Description TODO.
 * @Author linhd eng:ScathonLin
 * @Date 2019/5/7
 * @Version 1.0
 */
public class ReqParams {
    private List<Object> params = new LinkedList<>();

    public ReqParams addParam(Object param) {
        params.add(param);
        return this;
    }

    public ReqParams addAll(Collection paramCollection) {
        params.addAll(paramCollection);
        return this;
    }

    public ReqParams addAll(Object[] paramTypeArr){
        for (Object obj : paramTypeArr) {
            params.add(obj);
        }
        return this;
    }

    public List<Object> getParams() {
        return params;
    }
}
