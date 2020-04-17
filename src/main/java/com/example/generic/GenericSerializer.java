package com.example.generic;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.apache.kafka.common.serialization.Serializer;
import org.springframework.social.twitter.api.Tweet;

import java.util.Map;

public class GenericSerializer implements Serializer {
    @Override
    public void configure(Map configs, boolean isKey) {

    }

    @Override
    public byte[] serialize(String topic, Object object ){
        ObjectMapper mapper = new ObjectMapper();
        byte [] values = null;
        try {
            values = mapper.writeValueAsBytes(object);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return values;
    }

    @Override
    public void close() {

    }
}
