package com.jerrylz.common;

import cn.hutool.json.JSONUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * @author jerrylz
 * @date 2020/8/27
 */
public class EsRequest extends LinkedHashMap<String, Object> {
    private List<Object> data;
    private EsRequest parent;


    public static EsRequest instance(){
        return new EsRequest();
    }

    public EsRequest(){
    }


    public EsRequest append(EsRequest esRequest){
        esRequest.parent = this;
        return this;
    }



    public EsRequest appendChild(EsRequest esRequest){
        esRequest.parent = this;
        return esRequest;
    }

    public EsRequest appendChild(String key){
        return appendChild(key, false);
    }

    public List<Object> getData() {
        return data;
    }

    public void setData(List<Object> data) {
        this.data = data;
    }

    public EsRequest getParent() {
        return parent;
    }

    public void setParent(EsRequest parent) {
        this.parent = parent;
    }

    public EsRequest appendChild(String key, boolean isArrayList){
        EsRequest child = new EsRequest();
        child.parent = this;
        if(isArrayList){
            List<Object> childData = new ArrayList<>();
            child.data = childData;
            this.put(key, childData);
        }else{
            this.put(key, child);
        }
        return child;
    }

    public <T> T appendChild(String key, Class<T> clazz){
        T t;
        try {
            t = clazz.newInstance();
        }catch (Exception e){
            throw new EsException();
        }

        this.put(key, t);
        return t;

    }

    public EsRequest root(){
        EsRequest esRequest = this;
        do{
            esRequest = esRequest.parent;
        }while (esRequest.parent != null);

        return esRequest;

    }

    public EsRequest append(String key, Object value){
        this.put(key, value);
        return this;
    }


    public static void main(String[] args) {

        EsRequest root = EsRequest.instance().appendChild("query")
                .appendChild("match")
                .append("name", 12).root();

        System.out.println(JSONUtil.toJsonStr(root));

        EsRequest esRequest = new EsRequest();
        List list = esRequest.appendChild("query")
                .appendChild("match", ArrayList.class);
        list.add("1212");
        System.out.println(JSONUtil.toJsonStr(esRequest));

    }
}
