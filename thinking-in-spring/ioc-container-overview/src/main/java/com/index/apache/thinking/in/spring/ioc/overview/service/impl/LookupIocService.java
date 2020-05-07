package com.index.apache.thinking.in.spring.ioc.overview.service.impl;

import com.index.apache.thinking.in.spring.ioc.overview.service.IocService;

/**
 * @ClassName: LookupIocService
 * @Description: 依赖查找服务
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/8 22:28
 * @Version： 1.0
 */
public class LookupIocService implements IocService {

    protected String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "LookupIocService{" +
                "type='" + type + '\'' +
                '}';
    }
}
