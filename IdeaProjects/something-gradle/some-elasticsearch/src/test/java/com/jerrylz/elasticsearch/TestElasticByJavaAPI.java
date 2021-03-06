package com.jerrylz.elasticsearch;

import cn.hutool.json.JSONUtil;
import com.jerrylz.annotation.KafkaData;
import com.jerrylz.common.Car;
import com.jerrylz.common.User;
import com.jerrylz.service.FieldService;
import com.jerrylz.service.KafkaService;
import com.jerrylz.vo.Pepole;
import lombok.extern.slf4j.Slf4j;
import org.elasticsearch.action.admin.indices.delete.DeleteIndexRequest;
import org.elasticsearch.action.get.GetRequest;
import org.elasticsearch.action.get.GetResponse;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.support.master.AcknowledgedResponse;
import org.elasticsearch.action.update.UpdateRequest;
import org.elasticsearch.action.update.UpdateResponse;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.CreateIndexResponse;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.unit.TimeValue;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.search.fetch.subphase.FetchSourceContext;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

/**
 * @author jerrylz
 * @date 2020/8/26
 */
@SpringBootTest
@RunWith(SpringRunner.class)
public class TestElasticByJavaAPI {
    private Logger log = LoggerFactory.getLogger(TestElasticByJavaAPI.class);
    @Autowired
    private RestHighLevelClient restHighLevelClient;
    @Autowired
    private KafkaService service;

    @Autowired
    private FieldService fieldService;

    @Test
    public void testB(){
        Pepole pepole = new Pepole();
        fieldService.fileData(pepole);
    }

    @Test
    public void testA(){
        User user = new User();
        Car car = new Car();
        car.setSize(23);
        user.setCar(car);
        user.setName("jerrylz");
        service.test(user);
    }

    /**
     * 创建索引
     * @throws IOException
     */
    @Test
    public void createIndex() throws IOException {
        CreateIndexRequest createIndexRequest = new CreateIndexRequest("ylz");
        CreateIndexResponse response = restHighLevelClient.indices().create(createIndexRequest, RequestOptions.DEFAULT);
        log.info(">>>>>>{}", response.isAcknowledged());
    }

    /**
     * 判断索引是否存在
     * @throws IOException
     */
    @Test
    public void testQueryIndex() throws IOException {
        GetIndexRequest index = new GetIndexRequest("ylz");
        boolean exists = restHighLevelClient.indices().exists(index, RequestOptions.DEFAULT);
        log.info(">>>>>>{}", exists);
    }

    /**
     * 删除索引
     * @throws IOException
     */
    @Test
    public void deleteIndex() throws IOException {
        DeleteIndexRequest index = new DeleteIndexRequest("ylz");
        AcknowledgedResponse delete = restHighLevelClient.indices().delete(index, RequestOptions.DEFAULT);
        log.info(">>>>>>{}", delete.isAcknowledged());
    }

    /**
     * 创建文档
     * @throws IOException
     */
    @Test
    public void addDocment() throws IOException {
        IndexRequest request = new IndexRequest("ylz");
        Map<String, String> map = new HashMap<>();
        map.put("name", "jerrylz");
        map.put("sex", "man");
        request.id("1").timeout(TimeValue.timeValueSeconds(1)).source(JSONUtil.toJsonStr(map), XContentType.JSON);
        IndexResponse resp = restHighLevelClient.index(request, RequestOptions.DEFAULT);
        log.info(">>>>>添加文档: {}", JSONUtil.toJsonStr(resp));
    }

    /**
     * 是否存在文档
     * @throws IOException
     */
    @Test
    public void existDocment() throws IOException {
        GetRequest req = new GetRequest("ylz", "1");
        req.fetchSourceContext(new FetchSourceContext(false)).storedFields("_none_");
        boolean exists = restHighLevelClient.exists(req, RequestOptions.DEFAULT);
        log.info(">>>>>是否存在文档: {}", exists);
    }

    /**
     * 获取文挡信息
     * @throws IOException
     */
    @Test
    public void getDocmentContent() throws IOException {
        GetRequest request = new GetRequest("ylz", "1");
        GetResponse response = restHighLevelClient.get(request, RequestOptions.DEFAULT);
        log.info(">>>>获取文档信息：{}", JSONUtil.toJsonStr(response));
    }


    /**
     * 更新文档
     * @throws IOException
     */
    @Test
    public void updateDocument() throws IOException {
        UpdateRequest request = new UpdateRequest("ylz", "1");
        Map<String, String> map = new HashMap<>();
        map.put("name", "jerrylz1");
        map.put("sex", "man");
        request.doc(JSONUtil.toJsonStr(map), XContentType.JSON).timeout(TimeValue.timeValueSeconds(1));
        UpdateResponse response = restHighLevelClient.update(request, RequestOptions.DEFAULT);
        log.info(">>>>更新文档: {}", JSONUtil.toJsonStr(response));
    }
}
