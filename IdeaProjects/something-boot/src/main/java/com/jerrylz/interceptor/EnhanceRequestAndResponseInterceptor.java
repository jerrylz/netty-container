package com.jerrylz.interceptor;

import com.jerrylz.annotation.EnhanceReturnValue;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.IOUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.List;

/**
 * created by jerrylz
 * date 2020/3/28
 */
@Slf4j
public class EnhanceRequestAndResponseInterceptor implements HandlerInterceptor {
    private final static ThreadLocal<HttpServletRequest> threaLocal = new ThreadLocal<>();

    /**
     *
     * @param request
     * @param response
     * @param handler
     * @return
     * @throws Exception
     */
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
//        threaLocal.set(request);
        if(!(handler instanceof HandlerMethod)){
            return true;
        }
        HandlerMethod handlerMethod = (HandlerMethod) handler;
        EnhanceReturnValue methodAnnotation = handlerMethod.getMethodAnnotation(EnhanceReturnValue.class);
        if(methodAnnotation == null){
            return true;
        }
        RequestWrapper requestWrapper = new RequestWrapper(request);
        List<String> body = IOUtils.readLines(requestWrapper.getReader());
        log.info(body.toString());

        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
//        RequestWrapper srw = (RequestWrapper) threaLocal.get();

        log.info(">>>>>>postHandle");
    }
}
