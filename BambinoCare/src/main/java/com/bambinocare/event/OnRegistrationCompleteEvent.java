package com.bambinocare.event;

import org.springframework.context.ApplicationEvent;

import com.bambinocare.model.entity.UserEntity;

public class OnRegistrationCompleteEvent extends ApplicationEvent {

	private static final long serialVersionUID = -5975506595145106350L;
	private String appUrl;
	private UserEntity user;

	public OnRegistrationCompleteEvent(UserEntity user, String appUrl) {
		super(user);
		
		this.user = user;
		this.appUrl = appUrl;
	}

	public String getAppUrl() {
		return appUrl;
	}

	public void setAppUrl(String appUrl) {
		this.appUrl = appUrl;
	}

	public UserEntity getUser() {
		return user;
	}

	public void setUser(UserEntity user) {
		this.user = user;
	}

}
