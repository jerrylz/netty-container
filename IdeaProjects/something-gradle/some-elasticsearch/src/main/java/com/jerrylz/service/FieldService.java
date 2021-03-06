package com.jerrylz.service;

import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONUtil;
import com.jerrylz.annotation.FieldValue;
import com.jerrylz.annotation.InSession;
import com.jerrylz.annotation.KafkaData;
import com.jerrylz.vo.Pepole;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanExpressionContext;
import org.springframework.beans.factory.support.DefaultListableBeanFactory;
import org.springframework.context.expression.StandardBeanExpressionResolver;
import org.springframework.stereotype.Service;
import org.springframework.util.ReflectionUtils;

/**
 * @author jerrylz
 * @date 2020/9/6
 */
@Service
public class FieldService implements BeanFactoryAware{

    @KafkaData(isValid = "#pepole.age.equals('23')")
    public void fileData(Pepole pepole) {
        doSoething(pepole);
        System.out.println(JSONUtil.toJsonStr(pepole));
    }

    public void doSoething(Object obj) {

        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            if(field.isAnnotationPresent(InSession.class)){
                field.setAccessible(true);
                if (!isBasicType(field.getType())) {
                    Object val = field.get(obj);
                    if (val == null) {
                        try {
                            val = field.getType().newInstance();
                            field.set(obj, val);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                    doSoething(val);
                } else {
                    InSession annotation = field.getAnnotation(InSession.class);
                    System.out.println(annotation.value());
                    field.set(obj, annotation.value());
                }
            }

            if(field.isAnnotationPresent(FieldValue.class)){
                field.setAccessible(true);
                if (!isBasicType(field.getType())) {
                    Object val = field.get(obj);
                    if (val == null) {
                        try {
                            val = field.getType().newInstance();
                            field.set(obj, val);
                        } catch (InstantiationException e) {
                            e.printStackTrace();
                        }
                    }
                    doSoething(val);
                } else {
                    FieldValue annotation = field.getAnnotation(FieldValue.class);
                    System.out.println(annotation.value());
                    field.set(obj, resolveData(annotation.value()));
                }
            }
        });
    }

    private boolean isBasicType(Class clzz){
        return ClassUtil.isBasicType(clzz) || clzz.getSimpleName().equals("String");
    }
    private DefaultListableBeanFactory beanFactory;

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        this.beanFactory = (DefaultListableBeanFactory)beanFactory;
    }


    private String resolveData(String el){
        if(el == null){
            return null;
        }
        if(el.startsWith("$")){
            return beanFactory.resolveEmbeddedValue(el);
        }
        if(el.contains("#")){
            BeanExpressionContext beanExpressionContext = new BeanExpressionContext(beanFactory, null);
            StandardBeanExpressionResolver standardBeanExpressionResolver = new StandardBeanExpressionResolver();
            Object evaluate = standardBeanExpressionResolver.evaluate(el, beanExpressionContext);
            if(evaluate != null){
                return evaluate.toString();
            }
            return "";
        }
        return el;
    }
}
