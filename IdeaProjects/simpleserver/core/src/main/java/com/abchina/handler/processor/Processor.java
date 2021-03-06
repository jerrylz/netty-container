package com.abchina.handler.processor;


import com.abchina.http.HttpRequest;
import com.abchina.http.HttpResponse;

public interface Processor {
    void process(HttpRequest request, HttpResponse response) throws Exception;
}
