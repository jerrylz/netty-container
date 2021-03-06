package com.jerrylz.controller;

import com.jerrylz.vo.RequestVO;
import com.jerrylz.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

/**
 * created by jerrylz
 * date 2020/3/28
 */
@RestController
@Slf4j
public class ReturnValueController {

    //    @SomethingAnnotation
    @Cacheable(value="test", key = "#vo.termId")
    @PostMapping("/getSomething")
    public ResponseVO getSomething(@RequestBody RequestVO vo){
        ResponseVO responseVO = new ResponseVO();
        responseVO.setCode("0099009980");
        return responseVO;
    }
}
