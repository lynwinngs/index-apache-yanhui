package com.index.apache.thinking.in.spring.cloud.repository.impl;

import com.index.apache.thinking.in.spring.cloud.entity.User;
import com.index.apache.thinking.in.spring.cloud.repository.UserRepository;
import org.springframework.stereotype.Repository;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @ClassName UserRepositoryLocalImpl
 * @Description 用户仓储本地实现
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 10:14
 * @Version 1.0
 **/
@Repository
public class UserRepositoryLocalImpl implements UserRepository {

    private Map<Long, User> repository = new ConcurrentHashMap<>();

    private AtomicLong idCounter = new AtomicLong(1);

    @Override
    public boolean saveUser(String name) {
        long id = idCounter.getAndIncrement();

        User user = new User(id, name);
        return repository.putIfAbsent(id, user) == null;
    }

    @Override
    public User getUser(Long id) {
        return repository.get(id);
    }
}
                                                  