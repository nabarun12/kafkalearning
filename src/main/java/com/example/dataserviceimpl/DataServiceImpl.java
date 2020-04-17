package com.example.dataserviceimpl;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Random;

import com.example.listener.StreamTweetListner;
import org.apache.kafka.clients.producer.Callback;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.social.twitter.api.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.example.dataservice.DataService;
import com.example.model.PeriodicTable;
import com.example.model.Sampledata;
import com.example.model.UserOperationLocal;

@Service
@EnableScheduling
public class DataServiceImpl implements DataService {

	Logger LOGGER = LoggerFactory.getLogger(DataServiceImpl.class);


	private Stream stream;


	@Value("${kafka.topic}")
	private String kafkaTopic;

	@Autowired
	RestTemplate restTemplate;
	
	private List<String> listElements = Arrays.asList("Hydrogen","Helium","Lithium","Beryllium","Boron","Carbon","Nitrogen","Oxygen","Fluorine","Neon");
	
	@Autowired
	SimpMessageSendingOperations messageTemplate;

	@Autowired
	KafkaProducer kafkaProducer;
	
	@Autowired
	Twitter twitter;
	
	@Override
	public List<Sampledata> getData() {

		List<Sampledata> dataList = new ArrayList<Sampledata>();
		Sampledata data1 = new Sampledata("monica", 29);
        Sampledata data2 = new Sampledata("chandler", 31);
		dataList.add(data1);
		dataList.add(data2);
		//return dataList;
        StringBuilder  strB = new StringBuilder("");
        strB.delete(0, strB.length());
        
        return dataList;
	}
	
	
	@SuppressWarnings("unchecked")
	@Override
	public void postData(List<Sampledata> sampleList) {

		List<Sampledata> dataList = new ArrayList<Sampledata>();
		dataList = restTemplate.postForObject("http://localhost:9004/office/service/postFriendsData", sampleList, List.class);
        System.out.println(dataList);
       
	}
	
	@Override
	@Scheduled(fixedRate = 1000, initialDelay = 10000)
	public void postDataElements() {

		DecimalFormat df = new DecimalFormat("###.###");
		
		Random random = new Random();;
		PeriodicTable p = new PeriodicTable();
		p.setName(listElements.get(random.nextInt(10)));
		p.setWeight(new Double(df.format(Double.valueOf(random.nextDouble() * random.nextInt(9)))));
		messageTemplate.convertAndSend("/post/messages", p);
		
	}
	
	public UserOperationLocal userOperation(){
		UserOperations userOperation = twitter.userOperations();
		return new UserOperationLocal(userOperation.getProfileId(),userOperation.getScreenName());
	}
	
	public List<Tweet> getTweets(){
		UserOperations userOperation = twitter.userOperations();
		//Create producer record
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

		List<Tweet> tweets = twitter.searchOperations().search("%23manutd", 20).getTweets();
		String key = "";
		String value = "";
		for (Tweet tweet : tweets) {
			key = "keyId_"+tweet.getId();
			producerRecord = new ProducerRecord<>(kafkaTopic,key,tweet);
			LOGGER.info("Key"+key);
			LOGGER.info("Value"+tweet);
			kafkaProducer.send(producerRecord, callback);

		}
		kafkaProducer.flush();

		return tweets;
	}

	public void listenToTweets (){
		StreamTweetListner streamTweetListner = new StreamTweetListner(kafkaProducer, kafkaTopic);
		List<StreamListener> streamListenerList = new ArrayList<>();
		streamListenerList.add(streamTweetListner);
		FilterStreamParameters filterStreamParameters = new FilterStreamParameters();
		filterStreamParameters.track("#theofficeus");
		filterStreamParameters.track("#Friends");
		filterStreamParameters.track("#PersonOfInterest");
		stream = twitter.streamingOperations().filter(filterStreamParameters,streamListenerList);
	}

	public void closeTweetStreaming (){
		stream.close();
	}

}
