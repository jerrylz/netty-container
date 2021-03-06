package com.jerrylz;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.EnableAspectJAutoProxy;

/**
 * @author jerrylz
 * @date 2020/8/24
 */
@SpringBootApplication
@EnableAspectJAutoProxy
public class EsMain {

    public static void main(String[] args) {
        SpringApplication.run(EsMain.class, args);
    }
}
