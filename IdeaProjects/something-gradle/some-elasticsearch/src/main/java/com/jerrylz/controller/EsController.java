package com.jerrylz.controller;

import org.elasticsearch.client.RestHighLevelClient;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author jerrylz
 * @date 2020/8/26
 */
@RestController
@RequestMapping("/es")
public class EsController {

    @Autowired
    private RestHighLevelClient restHighLevelClient;


}
