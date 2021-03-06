package com.jerrylz.elasticsearch;

import cn.hutool.extra.ftp.Ftp;
import com.jerrylz.utils.HttpClientUtil;
import com.jerrylz.utils.HttpResponse;
import org.junit.Test;

/**
 * @author jerrylz
 * @date 2020/8/26
 */
public class TestElasticByRestful {

    @Test
    public void test(){
        HttpResponse httpResponse = HttpClientUtil.httpPutRaw("http://192.168.1.151:9200/test", null, null, null);
        String body = httpResponse.getBody();
        System.out.println(body);
    }
}
