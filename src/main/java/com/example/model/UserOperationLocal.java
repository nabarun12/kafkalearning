package com.example.model;

public class UserOperationLocal {

	public UserOperationLocal(Long profileId, String screenName) {
		super();
		this.profileId = profileId;
		this.screenName = screenName;
	}

	public UserOperationLocal() {

	}

	Long profileId;

	String screenName;

	public String getScreenName() {
		return screenName;
	}

	public void setScreenName(String screenName) {
		this.screenName = screenName;
	}

}
