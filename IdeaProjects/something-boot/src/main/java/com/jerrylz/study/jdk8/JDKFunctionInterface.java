package com.jerrylz.study.jdk8;

/**
 * created by jerrylz
 * date 2020/3/31
 */
@FunctionalInterface
public interface JDKFunctionInterface<T> {

    void study(T t);

    default void sayHi(){
        System.out.println("hi");
    }

    default String goToSchool(){
        return "no";
    }
}
