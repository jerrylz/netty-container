package com.jerrylz;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.util.ClassUtil;
import cn.hutool.json.JSONUtil;
import com.jerrylz.flink.Pepole;
import com.jerrylz.flink.TestAN;
import com.jerrylz.flink.User;
import org.apache.commons.lang3.reflect.FieldUtils;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.util.ReflectionUtils;

import java.beans.PropertyDescriptor;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.HashMap;

/**
 * @author jerrylz
 * @date 2020/9/4
 */
public class TestFlink {

    public static void main(String[] args) throws NoSuchFieldException {
        User user = new User();
        user.setName("jerrylz");
        HashMap<String, Object> map = new HashMap<>();
        map.put("user", user );
        map.put("age", "23");


        Pepole pepole = BeanUtil.toBean(map, Pepole.class);

        Pepole pepole1 = new Pepole();
        pepole1.setAge("333");


        doSoething(pepole1);
        System.out.println(JSONUtil.toJsonStr(pepole1));
    }

    public static void doSoething(Object obj){

        ReflectionUtils.doWithFields(obj.getClass(), field -> {
            field.setAccessible(true);
            if(!isBasicType(field.getType())){
                Object val = field.get(obj);
                if(val == null){
                    try {
                        val = field.getType().newInstance();
                        field.set(obj, val);
                    } catch (InstantiationException e) {
                        e.printStackTrace();
                    }
                }
                doSoething(val);
            }else{
                TestAN annotation = field.getAnnotation(TestAN.class);
                System.out.println(annotation.value());
                field.set(obj, annotation.value());
            }
//            if(field.isAnnotationPresent(TestAN.class)&& field.getAnnotation(TestAN.class).isBasicType()){
//                TestAN annotation = field.getAnnotation(TestAN.class);
//                System.out.println(annotation.value());
//                field.set(obj, annotation.value());
//            }

        }, field -> field.isAnnotationPresent(TestAN.class));


//        Field[] declaredFields = clazz.getDeclaredFields();
//        for (Field field : declaredFields) {
//            if(!ClassUtil.isBasicType(field.getType())){
//                doSoething(field.getType());
//            }
//            if(field.isAnnotationPresent(TestAN.class)){
//                TestAN annotation = field.getAnnotation(TestAN.class);
//                System.out.println(annotation.value());
//            }
//        }
    }


    private static boolean isBasicType(Class clzz){
        return ClassUtil.isBasicType(clzz) || clzz.getSimpleName().equals("String");
    }

}
