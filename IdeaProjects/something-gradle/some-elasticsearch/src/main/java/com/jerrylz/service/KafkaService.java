package com.jerrylz.service;

import com.jerrylz.annotation.KafkaData;
import com.jerrylz.common.User;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

/**
 * @author jerrylz
 * @date 2020/9/2
 */
@Service
public class KafkaService {

    @Value("${test}")
    private String name;
    @KafkaData
    public void test(User user){
        System.out.println(">>>>执行service>>>>>");
    }
}
