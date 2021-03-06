package com.abchina.handler.processor;


import com.abchina.core.handler.ServletContext;
import com.abchina.core.pipeline.BoundPipeline;
import com.abchina.http.HttpRequest;
import com.abchina.http.HttpResponse;
import com.abchina.utils.StaticResourcesUtils;

public class HttpDispatchProcessor implements Processor {
    private ServletContext context;

    public HttpDispatchProcessor(ServletContext context) {
        this.context = context;
    }

    @Override
    public void process(HttpRequest request, HttpResponse response) throws Exception {
        Processor processor;
        boolean isStatic = StaticResourcesUtils.verifyPath(request.getPath());
        if (isStatic) {
            processor = new HttpStaticResourcesProcessor(context);
        } else {
            processor = new HttpServletProcessor(context);
        }
        // inbound pipeline
        BoundPipeline inboundPipeline = context.getInboundPipeline();
        inboundPipeline.doHandle(request, response);
        processor.process(request, response);
        // outbound pipeline
        BoundPipeline outBoundPipeline = context.getOutBoundPipeline();
        outBoundPipeline.doHandle(request,response);
    }
}
