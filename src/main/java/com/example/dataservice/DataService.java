package com.example.dataservice;

import java.util.List;

import org.springframework.social.twitter.api.Tweet;
import org.springframework.social.twitter.api.UserOperations;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import com.example.model.Sampledata;
import com.example.model.UserOperationLocal;

@Component
@Repository
public interface DataService {
	
	public List<Sampledata> getData();
	
	public void postData(List<Sampledata> sampleList);

	void postDataElements();
	
	public UserOperationLocal userOperation();
	
	public List<Tweet> getTweets();
	

}
