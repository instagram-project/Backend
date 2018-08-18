package com.gmail.insta.model;

import com.fasterxml.jackson.annotation.JsonIgnore;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;


@Entity
@Table(name="likes")
public class Like {

	@JsonIgnore
	@Id
	@GeneratedValue(strategy=GenerationType.AUTO)
	private Long id;	

	@JsonIgnore
	@Column(name="message_id")
	private Long message;
	
	@Column(name="user_id")
	private Long user;

    public Like() {} // jpa constructor

    public Like(Long item, Long user) {
        this.message = item;
        this.user = user;
    }

    public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public Long getMessage() {
		return message;
	}

	public void setMessage(Long item) {
		this.message = item;
	}

	public Long getUser() {
		return user;
	}

	public void setUser(Long user) {
		this.user = user;
	}

}
