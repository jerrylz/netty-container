package com.jerrylz.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * created by jerrylz
 * date 2020/3/26
 */

@Controller
@Slf4j
public class SomethingController {

    @GetMapping("/toIndex")
    public String toIndex(){
        log.info(">>>>>>跳转首页<<<<<<<<");
        return "index";
    }

}
