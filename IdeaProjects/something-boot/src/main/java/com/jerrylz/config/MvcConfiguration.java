package com.jerrylz.config;

import com.jerrylz.aspect.EnhanceReturnValueAspect;
import com.jerrylz.filter.RequestWrapFilter;
import com.jerrylz.handler.SomethingMethodReturnValueHandler;
import com.jerrylz.interceptor.EnhanceRequestAndResponseInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletComponentScan;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerAdapter;
import org.springframework.web.servlet.mvc.method.annotation.RequestResponseBodyMethodProcessor;

import javax.annotation.PostConstruct;
import javax.servlet.Filter;
import java.util.ArrayList;
import java.util.List;

/**
 * created by jerrylz
 * date 2020/3/28
 */
@Configuration
@ServletComponentScan
@EnableAspectJAutoProxy
@EnableCaching
public class MvcConfiguration implements WebMvcConfigurer {

    @Autowired
    private RequestMappingHandlerAdapter requestMappingHandlerAdapter;
    @Bean
    public EnhanceReturnValueAspect initSomethingAspect() {
        return new EnhanceReturnValueAspect();
    }

    @Bean
    public SomethingMethodReturnValueHandler initsSomethingRequestHandler(){
        return new SomethingMethodReturnValueHandler();
    }

    @Bean
    public Filter initSomethingFilter(){
        return new RequestWrapFilter();
    }

    /**
     * 注册过滤器
     * 出现执行两次doFilter方法
     * @return
     */
//    @Bean
//    public FilterRegistrationBean registrationSomethingFilter(){
//        FilterRegistrationBean<RequestWrapFilter> registrationBean = new FilterRegistrationBean<>();
//        registrationBean.setFilter(new RequestWrapFilter());
//        registrationBean.addUrlPatterns("/*");
//        return registrationBean;
//    }
    /**
     * 注册自定义拦截器
     * @param registry
     */
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new EnhanceRequestAndResponseInterceptor()).addPathPatterns("/**");
    }

    @Override
    public void addReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlers) {
        //spring处理链returnValueHandlers均未匹配才轮到自定义的Handler
        handlers.add(initsSomethingRequestHandler());
    }


    /**
     * 加载自定义处理器链
     */
    @PostConstruct
    public void initReturnValueHandlers(){
        List<HandlerMethodReturnValueHandler> returnValueHandlers = requestMappingHandlerAdapter.getReturnValueHandlers();
        List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers = new ArrayList<>(returnValueHandlers);
        decorateReturnValueHandlers(handlerMethodReturnValueHandlers);
        //暂时关闭
        requestMappingHandlerAdapter.setReturnValueHandlers(handlerMethodReturnValueHandlers);

    }

    /**
     * 在指定的RequestResponseBodyMethodProcessor之前装配
     * @param handlerMethodReturnValueHandlers
     */
    private void decorateReturnValueHandlers(List<HandlerMethodReturnValueHandler> handlerMethodReturnValueHandlers){
        for(HandlerMethodReturnValueHandler handler : handlerMethodReturnValueHandlers){
            if(handler instanceof RequestResponseBodyMethodProcessor){
                handlerMethodReturnValueHandlers.add(handlerMethodReturnValueHandlers.indexOf(handler), initsSomethingRequestHandler());
                break;

            }
        }
    }

}
