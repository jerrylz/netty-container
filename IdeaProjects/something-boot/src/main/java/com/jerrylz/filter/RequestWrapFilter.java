package com.jerrylz.filter;

import com.jerrylz.interceptor.RequestWrapper;
import org.springframework.core.annotation.Order;

import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;

/**
 * 1、FilterRegistrationBean 方式注册filter，会执行两次doFilter方法
 * 2、@WebFilter + @ServletComponentScan 方式执行一次doFilter方法
 * created by jerrylz
 * date 2020/3/29
 */

@WebFilter(urlPatterns = "/*")
@Order(1)
public class RequestWrapFilter implements Filter {
    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        RequestWrapper requestWrapper = new RequestWrapper((HttpServletRequest) servletRequest);
        filterChain.doFilter(requestWrapper, servletResponse);
    }
}
