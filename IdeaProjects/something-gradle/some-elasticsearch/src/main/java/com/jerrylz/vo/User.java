package com.jerrylz.vo;

import com.jerrylz.annotation.InSession;

public class User{
        @InSession("abc")
        String name;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }
    }