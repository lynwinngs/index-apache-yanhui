package com.index.apache.thinking.in.spring.cloud.repository;

import com.index.apache.thinking.in.spring.cloud.entity.User;

/**
 * @ClassName UserRepository
 * @Description 用户仓储
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 10:06
 * @Version 1.0
 **/
public interface UserRepository {

    boolean saveUser(String name);

    User getUser(Long id);
}
                                                  