package com.abchina.handler.processor;



import com.abchina.core.handler.ServletContext;
import com.abchina.http.HttpRequest;
import com.abchina.http.HttpResponse;
import com.abchina.utils.FileUtils;

import java.io.File;
import java.io.OutputStream;

public class HttpStaticResourcesProcessor implements Processor {

    private ServletContext context;

    HttpStaticResourcesProcessor(ServletContext context) {

        this.context = context;
    }

    @Override
    public void process(HttpRequest request, HttpResponse response) throws Exception {
        String path = request.getPath().replaceFirst("/", "");
        String suffix = path.substring(path.lastIndexOf(".") + 1);
        File jarFile = context.getJarFile();
        byte[] bytes = FileUtils.readFileFromJar(jarFile, path);
        ContentTypeMapper mapper = context.getContentTypeMapper();
        String contentType = mapper.get(suffix);
        if (contentType != null) {
            response.addHeader("Content-Type", contentType);
        }
        OutputStream outputStream = response.getResponseOutputStream();
        outputStream.write(bytes);
    }
}
