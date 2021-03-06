package com.jerrylz.config;

import cn.hutool.json.JSONUtil;
import com.jerrylz.common.EsRequest;

import java.util.ArrayList;
import java.util.List;

/**
 * @author jerrylz
 * @date 2020/8/30
 */
public class TetA {
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
