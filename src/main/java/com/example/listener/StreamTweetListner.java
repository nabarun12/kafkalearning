package com.example.listener;

import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;

import java.util.List;


public class StreamTweetListner implements StreamListener {

    Logger LOGGER = LoggerFactory.getLogger(StreamTweetListner.class);


    KafkaProducer kafkaProducer;

    private String kafkaTopic;

    public StreamTweetListner(KafkaProducer kafkaProducer, String kafkaTopic){
        this.kafkaProducer = kafkaProducer;
        this.kafkaTopic = kafkaTopic;
    }

    @Override
    public void onTweet(Tweet tweet) {
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        ProducerRecord<String, Tweet> producerRecord ;
        //Send
        Callback callback = (metadata, exception) ->
        {
            if (exception == null) {
                LOGGER.info("Topic:" + metadata.topic());
                LOGGER.info("Partition:" + metadata.partition());
                LOGGER.info("Offset" + metadata.offset());
                LOGGER.info("Timestamp" + metadata.timestamp());

            } else
                LOGGER.error(exception.getMessage());
        };
        String key = "";
        String value = "";
        if(tweet.getText().toLowerCase().contains("#theofficeus"))
            key = "theofficeus";
        else if(tweet.getText().toLowerCase().contains("#Friends"))
            key = "friends";
        else if(tweet.getText().toLowerCase().contains("#PersonOfInterest"))
            key = "personofinterest";


        producerRecord = new ProducerRecord<>(kafkaTopic,key,tweet);
        LOGGER.info("Key"+key);
        LOGGER.info("Value"+tweet);
        kafkaProducer.send(producerRecord, callback);
        kafkaProducer.flush();
        LOGGER.info("Received Tweet:"+ tweet.getText()+ "  ; from " + tweet.getFromUser() );
       // LOGGER.info("Received Tweet:"+ tweet.getText()+ "from " + tweet.getFromUser());
    }

    @Override
    public void onDelete(StreamDeleteEvent deleteEvent) {

    }

    @Override
    public void onLimit(int numberOfLimitedTweets) {

    }

    @Override
    public void onWarning(StreamWarningEvent warningEvent) {

    }
}
