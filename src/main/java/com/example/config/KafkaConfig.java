package com.example.config;

import com.example.generic.GenericSerializer;
import com.fasterxml.jackson.databind.JsonSerializer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerConfig; //this is needed
import org.apache.kafka.common.serialization.StringSerializer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.HashMap;
import java.util.Map;

@Configuration
public class KafkaConfig {

    @Value("${kafka.bootstrap.server}")
    private String bootstrapServer;

    @Bean
    public KafkaProducer<String,String> kafkaProducer(){
        Map<String, Object> mapProperties = new HashMap<>();
        //set properties
        mapProperties.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, StringSerializer.class.getName());
        mapProperties.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, GenericSerializer.class.getName());
        mapProperties.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, bootstrapServer);

        //safe producer Start
        mapProperties.put(ProducerConfig.ENABLE_IDEMPOTENCE_CONFIG, true);
        //The following is not required for idempotent producer but just to memorize, these are set like these when we set idempotence = true
       // ProducerConfig.ENABLE_IDEMPOTENCE_DOC

        mapProperties.put(ProducerConfig.MAX_IN_FLIGHT_REQUESTS_PER_CONNECTION,"5");
        mapProperties.put(ProducerConfig.ACKS_CONFIG,"all");
        //mapProperties.put(ProducerConfig.DELIVERY_TIMEOUT_MS_CONFIG,120000);
        mapProperties.put(ProducerConfig.RETRIES_CONFIG,""+Integer.MAX_VALUE);
        //safe producerEnd

        //High Throughput producer at the expense of a bit delay and cpu processing start

        mapProperties.put(ProducerConfig.COMPRESSION_TYPE_CONFIG, "snappy");
        mapProperties.put(ProducerConfig.LINGER_MS_CONFIG,"20");
        mapProperties.put(ProducerConfig.BATCH_SIZE_CONFIG,""+32*1024);

        //Initiate Produce
        KafkaProducer<String, String> kafkaProducer = new KafkaProducer<>(mapProperties);
        return kafkaProducer;
    }
}
