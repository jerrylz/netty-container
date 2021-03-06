package com.jerrylz.flink;

public class Pepole{
        @TestAN
        User user;
        @FieldValue("${age}")
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