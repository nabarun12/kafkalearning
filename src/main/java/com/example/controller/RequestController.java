package com.example.controller;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.messaging.simp.SimpMessageSendingOperations;
import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;
import com.example.dataservice.DataService;
import com.example.model.Sampledata;
import com.example.model.UserOperationLocal;
import com.example.restcall.CallFeignClientApi;
import com.netflix.discovery.EurekaClient;
import com.netflix.discovery.shared.Application;

@CrossOrigin
@RestController
@RequestMapping(value = "/service")
public class RequestController {
	
	
	
	@Autowired
	EurekaClient eureka;
	
	/*@Autowired
	CallFeignClientApi callClient;*/
	
	@Autowired
	DataService dataService;
	
	@Autowired
	SimpMessageSendingOperations messageTemplate;
	
	
	@RequestMapping(value = "/getData", method = RequestMethod.GET , produces = "application/json")
	public List<Sampledata> getData(){
		
		return dataService.getData();
			 
		
	}
	
	@PostMapping(value = "/postData",  consumes = "application/json")
	public void posttData(@RequestBody List<Sampledata> sampleList){
		
		dataService.postData(sampleList);
			 		
	}
	
	/*@RequestMapping(value = "/getOfficeData", method = RequestMethod.GET , produces = "application/json")
	public String getOfficeData(){
		Application application = eureka.getApplication("office");
		System.out.println("Application host:"+ application.getInstances().get(0).getHostName());
		System.out.println("Application host:"+ application.getInstances().get(0).getPort());
		return callClient.getOfficeData();
		
		 
		
	}*/
	
	@RequestMapping(value = "/postFriendsDataTOUi", method = RequestMethod.POST , produces = "application/json")
	public void getSampleData(@RequestBody Sampledata sampleData){
		 
		
		messageTemplate.convertAndSend("/topic/messages", sampleData);
		
	}
	
	@RequestMapping(value = "/getTwitterName", method = RequestMethod.GET)
	public UserOperationLocal getTwitterData(){
		 		
		return dataService.userOperation();
		
	}
	
	@RequestMapping(value = "/getTweets", method = RequestMethod.GET)
	public List<Tweet> getTweets(){
		 		
		return dataService.getTweets();
		
	}
}
