package com.index.apache.thinking.in.spring.cloud.service;

import com.index.apache.thinking.in.spring.cloud.entity.User;
import com.index.apache.thinking.in.spring.cloud.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @ClassName UserServiceImpl
 * @Description 用户服务
 * @Author xiaoxuezhi
 * @DATE 2020/4/27 9:57
 * @Version 1.0
 **/
@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserRepository userRepository;

    @Override
    public boolean saveUser(String name) {
        return userRepository.saveUser(name);
    }

    @Override
    public User getUser(Long id) {
        return userRepository.getUser(id);
    }

}
                                                  