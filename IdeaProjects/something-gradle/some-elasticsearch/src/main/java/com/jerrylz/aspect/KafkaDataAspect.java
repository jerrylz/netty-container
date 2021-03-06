package com.jerrylz.aspect;

import cn.hutool.core.util.StrUtil;
import com.jerrylz.annotation.KafkaData;
import com.jerrylz.common.SplUtil;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.core.DefaultParameterNameDiscoverer;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.expression.spel.standard.SpelExpressionParser;
import org.springframework.expression.spel.support.StandardEvaluationContext;
import org.springframework.stereotype.Component;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Method;

/**
 * @author jerrylz
 * @date 2020/9/2
 */
@Aspect
@Component
public class KafkaDataAspect {
    private static final String ASPECT_TRUE = "true";

    @Pointcut("@annotation(com.jerrylz.annotation.KafkaData)")
    public void pointcut(){};

    @AfterReturning(pointcut = "pointcut() && @annotation(kafkaData) ")
    public void execute(JoinPoint joinPoint, KafkaData kafkaData){
        kafkaData.isValid();
        String s = SplUtil.parseKey(kafkaData.isValid(), joinPoint, String.class);
        System.out.println(s);

    }



}
