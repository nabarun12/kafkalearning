package com.example.dataserviceimpl;

import com.netflix.discovery.converters.Auto;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;

@Service
public class StartTwitterStreaming {

    @Autowired
    DataServiceImpl dataService;

    @PostConstruct
    public void startTweetsInflow(){
        dataService.listenToTweets();
    }

    @PreDestroy
    public void stopTweet(){
        dataService.closeTweetStreaming();
    }
}
