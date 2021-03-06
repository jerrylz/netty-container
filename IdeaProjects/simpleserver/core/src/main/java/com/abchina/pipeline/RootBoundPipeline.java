package com.abchina.pipeline;


import com.abchina.core.handler.ServletContext;
import com.abchina.http.HttpRequest;
import com.abchina.http.HttpResponse;

/**
 * @Author: xiantang
 * @Date: 2020/4/26 22:57
 */
public class RootBoundPipeline extends BoundPipeline {
    private ServletContext context;

    public RootBoundPipeline(ServletContext context) {
        super(context);
    }

    @Override
    public void doHandle(HttpRequest request, HttpResponse response) {
        Pipeline nextPipeLine = getNext();
        if (nextPipeLine != null) {
            nextPipeLine.doHandle(request, response);
        }
    }
}
