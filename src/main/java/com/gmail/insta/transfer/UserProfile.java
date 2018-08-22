package com.gmail.insta.transfer;

import com.gmail.insta.model.User;

public class UserProfile {
	
	private Long id;
	private String name;
	private String email;	

	public static UserProfile from(User user) {
		UserProfile userProfile = new UserProfile();
		userProfile.id = user.getId();
		userProfile.name = user.getName();
		userProfile.email = user.getEmail();
		return userProfile;
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}
	
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

}
