package com.index.apache.think.in.spring.entity;

/**
 * @ClassName RedisTemplateMock
 * @Description redis template 模拟
 * @Author xiaoxuezhi
 * @DATE 2020/4/13 14:51
 * @Version 1.0
 **/
public class RedisTemplateMock<K, V> {

    private K key;

    private V value;

    public K getKey() {
        return key;
    }

    public void setKey(K key) {
        this.key = key;
    }

    public V getValue() {
        return value;
    }

    public void setValue(V value) {
        this.value = value;
    }

    @Override
    public String toString() {
        return "RedisTemplateMock{" +
                "key=" + key +
                ", value=" + value +
                '}';
    }
}
                                                  