package com.gmail.insta.transfer;

import com.gmail.insta.model.User;

public class UserProfile {
	
	private String name;
	
	public static UserProfile from(User user) {
		UserProfile userProfile = new UserProfile();
		userProfile.name = user.getName();
		return userProfile;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	

}
