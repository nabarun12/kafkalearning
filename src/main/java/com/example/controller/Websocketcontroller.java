package com.example.controller;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;

import com.example.model.Sampledata;

@Controller
public class Websocketcontroller {

	
	@MessageMapping("/chat")
	@SendTo("/topic/messages")
	public Sampledata send(Sampledata sampledata) throws Exception {
	   
	    return new Sampledata(sampledata.getName(), sampledata.getAge());
	}
}
