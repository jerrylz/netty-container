package com.abchina.pipeline;


import com.abchina.core.handler.ServletContext;
import com.abchina.http.HttpRequest;
import com.abchina.http.HttpResponse;

/**
 * @Author: xiantang
 * @Date: 2020/4/26 22:56
 */
public abstract class BoundPipeline implements Pipeline{
    private Pipeline next;
    private final ServletContext context;

    protected BoundPipeline(ServletContext context) {
        this.context = context;
    }

    public ServletContext getContext() {
        return context;
    }

    public void setNext(Pipeline nextPipeline) {
        next = nextPipeline;
    }

    Pipeline getNext() {
        return next;
    }

    public abstract void doHandle(HttpRequest request, HttpResponse response);
}
