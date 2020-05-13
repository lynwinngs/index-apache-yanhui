package com.index.apache.thinking.in.spring.bean.scope;

import org.springframework.beans.factory.ObjectFactory;
import org.springframework.beans.factory.ObjectProvider;
import org.springframework.beans.factory.config.Scope;
import org.springframework.core.NamedThreadLocal;

import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * 自定义 ThreadLocal scope bean
 *
 * @Author xiaoxuezhi
 * @DATE 2020/5/13 16:06
 * @Version 1.0
 **/
public class ThreadLocalScope implements Scope {

    public static final String THREAD_LOCAL_SCOPE = "thread-local";

    private ThreadLocal<Map<String, Object>> threadLocal = new NamedThreadLocal("thread-local") {
        @Override
        protected Object initialValue() {
            return new HashMap<>();
        }
    };

    @Override
    public Object get(String name, ObjectFactory<?> objectFactory) {
        Map<String, Object> context = getContext();
        return Optional.ofNullable(context.get(name)).orElseGet(() -> {
            Object object = objectFactory.getObject();
            context.putIfAbsent(name, object);
            return object;
        });
    }

    private Map<String, Object> getContext() {
        return threadLocal.get();
    }

    @Override
    public Object remove(String name) {
        return null;
    }

    @Override
    public void registerDestructionCallback(String name, Runnable callback) {

    }

    @Override
    public Object resolveContextualObject(String key) {
        return null;
    }

    @Override
    public String getConversationId() {
        return String.valueOf(Thread.currentThread().getId());
    }
}
                                                  