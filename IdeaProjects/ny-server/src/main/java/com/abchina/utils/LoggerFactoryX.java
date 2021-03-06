package com.abchina.utils;

/**
 * @author jerrylz
 * @date 2021/3/4
 */
public class LoggerFactoryX {
    public static LoggerX getLogger(Class clazz){
        return new LoggerX(clazz);
    }

    public static LoggerX getLogger(String clazz){
        return new LoggerX(clazz);
    }
}
