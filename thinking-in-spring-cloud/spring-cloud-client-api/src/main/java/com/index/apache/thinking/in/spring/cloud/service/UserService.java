package com.index.apache.thinking.in.spring.cloud.service;

import com.index.apache.thinking.in.spring.cloud.entity.User;

/**
 * @ClassName UserService
 * @Description 用户服务类
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 9:32
 * @Version 1.0
 **/
public interface UserService {

    boolean saveUser(String name);

    User getUser(Long id);
}
                                                  