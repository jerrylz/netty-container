package com.jerrylz.aspect;

import com.alibaba.fastjson.JSON;
import com.jerrylz.annotation.EnhanceReturnValue;
import com.jerrylz.vo.ResponseVO;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.*;

/**
 * 定义切面
 * created by jerrylz
 * date 2020/3/26
 */
@Aspect
@Slf4j
public class EnhanceReturnValueAspect {

    /**
     * 设置切点
     */
    @Pointcut("@annotation(com.jerrylz.annotation.EnhanceReturnValue)")
    public void annotationPointCut(){}


    @AfterReturning(value="annotationPointCut() && @annotation(enhanceReturnValue)", returning = "object")
    public void AfterReturnMethodExec(JoinPoint joinPoint, Object object, EnhanceReturnValue enhanceReturnValue){
        Object[] args = joinPoint.getArgs();
        log.info(JSON.toJSONString(args));
        ResponseVO vo = (ResponseVO) object;
        vo.setMessage("AfterReturning11111");
    }

}
