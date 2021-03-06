package com.abchina.servlet;

public interface Servlet {
    void service(Request request, Response response) throws Exception;
}
