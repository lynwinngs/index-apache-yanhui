package com.index.apache.thinking.in.spring.bean.scope;

import com.index.apache.thinking.in.spring.beans.entity.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.context.annotation.RequestScope;
import org.springframework.web.context.annotation.SessionScope;

/**
 * web context scope 示例(request, session)
 * <p>
 * application websocket 略
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/13 11:40
 * @Version 1.0
 **/
@RestController
public class WebContextBeanScopeController {

    @Autowired
    @Qualifier("requestUser")
    private User requesstUser;

    @Autowired
    @Qualifier("sessionUser")
    private User sessionUser;

    @Bean
    @RequestScope
    public User requestUser() {
        return new User(System.nanoTime(), "request");
    }

    @Bean
    @SessionScope
    public User sessionUser() {
        return new User(System.nanoTime(), "session");
    }

    @GetMapping("/getRequestUser")
    public void getRequestUser() {
        System.out.println(requesstUser);
    }

    @GetMapping("/getSessionUser")
    public void getSessionUser() {
        System.out.println(sessionUser);
    }
}
                                                  