package com.jerrylz.common;

/**
 * @author jerrylz
 * @date 2020/9/2
 */
public class User {
    private String name;
    private Car car;

    public Car getCar() {
        return car;
    }

    public void setCar(Car car) {
        this.car = car;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
