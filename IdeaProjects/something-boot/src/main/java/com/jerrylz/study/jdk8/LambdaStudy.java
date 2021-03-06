package com.jerrylz.study.jdk8;

import java.util.function.Consumer;
import java.util.stream.Stream;

/**
 * created by jerrylz
 * date 2020/3/31
 */

public class LambdaStudy {
    public static void main(String[] args) {
        //消费型接口 void accept(T t)
        Consumer<String> consumer = x -> System.out.println(x);
        consumer.accept("消费型接口Consumer");
//        var a = 0;
        Stream.ofNullable("");



    }
}
