package com.abchina.pipeline;


import com.abchina.core.handler.ServletContext;
import com.abchina.http.HttpRequest;
import com.abchina.http.HttpResponse;

public class ContentOutBoundPipeline extends BoundPipeline {
    public ContentOutBoundPipeline(ServletContext servletContext) {
        super(servletContext);
    }

    @Override
    public void doHandle(HttpRequest request, HttpResponse response) {
        int length = response.getBodyBytes().length;
        response.addHeader("Content-Length",length+"");
    }
}
