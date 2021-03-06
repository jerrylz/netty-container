package com.abchina.pipeline;


import org.apache.http.HttpRequest;
import org.apache.http.HttpResponse;

/**
 * @Author: xiantang
 * @Date: 2020/4/26 23:04
 */
public interface Pipeline {


    void doHandle(HttpRequest request, HttpResponse response);
}
