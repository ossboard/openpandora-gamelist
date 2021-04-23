package com.pandora.spring.agent;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public class JSONUtil {

    public static String jsonStringFromObject(Object object) throws JsonProcessingException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.writeValueAsString(object);
    }

    public static Map jsonStringToMap(String t) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(t,HashMap.class);
    }

    public static Object jsonStringToObject(String t, Class<?> c) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.readValue(t, c );
    }

    public static Map objectToMap(Object c) throws IOException {
        ObjectMapper mapper = new ObjectMapper();
        return mapper.convertValue(c, Map.class);
    }
}
