package com.gmail.insta.model;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Following {
	
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;
	
	@Column(name="follower_id")
	private Long follower;
	
	@Column(name="user_id")
	private Long user;
	
	
	public Following() {}
	
	public Following(Long follower, Long user) {
		this.follower =follower;
		this.user = user;		
	}
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

	public Long getFollower() {
		return follower;
	}

	public void setFollower(Long follower) {
		this.follower = follower;
	}


}
