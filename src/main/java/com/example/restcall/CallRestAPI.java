package com.example.restcall;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.client.RestTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;

@Component
public class CallRestAPI {
	
	/*@Value("#{officeUrl}")
	private String officeUrl;
	
	@Value("#{bigbangtheoryUrl}")
	private String jackUrl;
	
	
	@Autowired
	private RestTemplate restTemplate;
	
	
	public RestTemplate officRestTemplate(){
		restTemplate = new RestTemplate();
		return restTemplate;
	}*/

}
