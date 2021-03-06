package com.jerrylz.vo;

import com.jerrylz.annotation.FieldValue;
import com.jerrylz.annotation.InSession;

public class Pepole{
        @InSession
        User user;
        @FieldValue("23")
        String age;

        public User getUser() {
            return user;
        }

        public void setUser(User user) {
            this.user = user;
        }

        public String getAge() {
            return age;
        }

        public void setAge(String age) {
            this.age = age;
        }
    }