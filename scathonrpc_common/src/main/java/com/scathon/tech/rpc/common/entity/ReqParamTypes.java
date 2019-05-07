package com.scathon.tech.rpc.common.entity;

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
public class ReqParamTypes {
    private List<Class> params = new LinkedList<>();

    public ReqParamTypes addParam(Class param) {
        params.add(param);
        return this;
    }

    public ReqParamTypes addAll(Collection<Class> paramCollection) {
        params.addAll(paramCollection);
        return this;
    }

    public ReqParamTypes addAll(Class[] paramTypeArr) {
        for (Class obj : paramTypeArr) {
            params.add(obj);
        }
        return this;
    }

    public List<Class> getParams() {
        return params;
    }
}
