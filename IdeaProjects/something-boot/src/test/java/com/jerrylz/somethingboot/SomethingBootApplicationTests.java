package com.jerrylz.somethingboot;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.ApplicationContext;


@SpringBootTest
class SomethingBootApplicationTests {

    @Autowired
    private ApplicationContext ioc;

    @Test
    void contextLoads() {
        boolean hello = ioc.containsBean("hello");
//        assert hello:true;
    }

}
