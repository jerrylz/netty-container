package com.jerrylz.interceptor;

import org.apache.commons.io.IOUtils;

import javax.servlet.ReadListener;
import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletRequestWrapper;
import java.io.BufferedReader;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.List;

/**
 * created by jerrylz
 * date 2020/3/28
 */
public class RequestWrapper extends HttpServletRequestWrapper {

    //存储请求参数
    private String body;

    public RequestWrapper(HttpServletRequest request) throws IOException {
        super(request);
        loadBody(request);
    }

    public String getBody(){
        return body;
    }

    @Override
    public ServletInputStream getInputStream() throws IOException {
        return new RequestCachingInputStream(body);
    }

    @Override
    public BufferedReader getReader() throws IOException {
        return new BufferedReader(new InputStreamReader(getInputStream(), getCharacterEncoding()));
    }

    /**
     * 加载请求体
     * @param request
     * @throws IOException
     */
    private void loadBody(HttpServletRequest request) throws IOException {
        StringBuilder sb = new StringBuilder();
        List<String> params = IOUtils.readLines(request.getInputStream(), getCharacterEncoding());
        for(String param : params){
            sb.append(param);
        }
        body = sb.toString();
    }

    /**
     * 缓存请求输入流
     */
    private static class RequestCachingInputStream extends ServletInputStream {

        private final ByteArrayInputStream byteArrayInputStream;

        public RequestCachingInputStream(String body){
            byteArrayInputStream = new ByteArrayInputStream(body.getBytes());
        }

        @Override
        public boolean isFinished() {
            return byteArrayInputStream.available() == 0;
        }

        @Override
        public boolean isReady() {
            return true;
        }

        @Override
        public void setReadListener(ReadListener readListener) {

        }

        @Override
        public int read() throws IOException {
            return byteArrayInputStream.read();
        }
    }
}
