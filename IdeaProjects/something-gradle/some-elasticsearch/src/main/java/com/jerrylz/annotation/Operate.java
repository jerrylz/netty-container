package com.jerrylz.annotation;

import org.springframework.cache.annotation.Cacheable;

/**
 * @author jerrylz
 * @date 2020/9/3
 */
public interface Operate {

    @Cacheable
    String doSomething();
}
