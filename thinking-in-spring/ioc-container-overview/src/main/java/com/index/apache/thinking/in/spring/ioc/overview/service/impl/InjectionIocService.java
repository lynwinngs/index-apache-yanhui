package com.index.apache.thinking.in.spring.ioc.overview.service.impl;

import com.index.apache.thinking.in.spring.ioc.overview.annotation.Auto;
import com.index.apache.thinking.in.spring.ioc.overview.service.IocService;

/**
 * @ClassName: InjectionIocService
 * @Description: 依赖注入服务
 * @Author: Xiao Xuezhi
 * @Date: 2020/4/8 23:03
 * @Version： 1.0
 */
@Auto
public class InjectionIocService implements IocService {

    private String type;

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return "InjectionIocService{" +
                "type='" + type + '\'' +
                '}';
    }
}
