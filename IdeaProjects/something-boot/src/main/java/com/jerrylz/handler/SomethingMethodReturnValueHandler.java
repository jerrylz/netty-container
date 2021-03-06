package com.jerrylz.handler;

import com.jerrylz.annotation.EnhanceReturnValue;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.MethodParameter;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodReturnValueHandler;
import org.springframework.web.method.support.ModelAndViewContainer;

/**
 * created by jerrylz
 * date 2020/3/28
 */
@Slf4j
public class SomethingMethodReturnValueHandler implements HandlerMethodReturnValueHandler {
    @Override
    public boolean supportsReturnType(MethodParameter methodParameter) {
        EnhanceReturnValue methodAnnotation = methodParameter.getMethodAnnotation(EnhanceReturnValue.class);
        return methodAnnotation != null;
    }

    @Override
    public void handleReturnValue(Object o, MethodParameter methodParameter, ModelAndViewContainer modelAndViewContainer, NativeWebRequest nativeWebRequest) throws Exception {
        log.info(">>>>>>SomethingRequestHandler<<<<<<");
    }
}
