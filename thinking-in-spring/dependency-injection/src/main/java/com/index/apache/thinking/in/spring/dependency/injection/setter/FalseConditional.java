package com.index.apache.thinking.in.spring.dependency.injection.setter;

import org.springframework.context.annotation.Condition;
import org.springframework.context.annotation.ConditionContext;
import org.springframework.core.type.AnnotatedTypeMetadata;

/**
 * TODO
 *
 * @Author: Xiao Xuezhi
 * @Date: 2020/6/1 21:33
 * @Version： 1.0
 */
public class FalseConditional implements Condition {
    @Override
    public boolean matches(ConditionContext context, AnnotatedTypeMetadata metadata) {
        return false;
    }
}
