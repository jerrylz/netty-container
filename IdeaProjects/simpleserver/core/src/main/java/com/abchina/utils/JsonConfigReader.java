package com.abchina.utils;


import com.abchina.core.config.Configuration;
import com.abchina.core.server.ServletWrapper;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.abchina.core.config.ConfigConstants.LOAD_ON_STARTUP;
import static com.abchina.core.config.ConfigConstants.PATH;
import static com.abchina.core.config.ConfigConstants.PORT;
import static com.abchina.core.config.ConfigConstants.ROUTER;
import static com.abchina.core.config.ConfigConstants.SERVLET_CLASS;
import static com.abchina.core.config.ConfigConstants.SERVLET_NAME;
import static com.abchina.core.config.ConfigConstants.SUB_REACTOR_COUNT;
import static com.abchina.utils.CastUtils.cast;


public class JsonConfigReader {
    String readAsString(String path, Charset encoding) throws Exception {
        URI uri = ClassLoader.getSystemResource(path).toURI();
        byte[] encoded = Files.readAllBytes(Paths.get(uri));
        return new String(encoded, encoding);
    }

    Configuration parseStringAsConfiguration(String raw) throws IOException {
        Map<String, Object> rawConfig;
        ObjectMapper objectMapper = new ObjectMapper();
        rawConfig = cast(objectMapper.readValue(raw, new TypeReference<HashMap>() {
        }));
        return mapToConfiguration(rawConfig);
    }

    private Configuration mapToConfiguration(Map<String, Object> rawConfigMap) {
        Integer port = (Integer)rawConfigMap.get(PORT);
        Integer subReactorCount = (Integer)rawConfigMap.get(SUB_REACTOR_COUNT);
        Map<String, ServletWrapper> router = parseMapsToRouter(rawConfigMap);
        return new Configuration(port, subReactorCount, router);
    }

    private Map<String, ServletWrapper> parseMapsToRouter(Map<String, Object> rawConfigMap) {
        List<Map<String, Object>> maps = cast(rawConfigMap.get(ROUTER));
        Map<String, ServletWrapper> router = new HashMap<>();
        for (Map<String, Object> rkv : maps) {
            String servletName = (String) rkv.get(SERVLET_NAME);
            String path = (String) rkv.get(PATH);
            String servletClass = (String) rkv.get(SERVLET_CLASS);
            Integer loadOnStartUp = (Integer) rkv.get(LOAD_ON_STARTUP);
            ServletWrapper wrapper = new ServletWrapper(servletName,
                    path,
                    servletClass,
                    loadOnStartUp, null, null);
            router.put(path, wrapper);
        }
        return router;
    }


}
