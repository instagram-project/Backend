package com.gmail.insta.model;


import javax.persistence.*;

import java.util.Date;
import java.util.Set;

@Entity
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String text;

    private String filename;

    private Date date;
    
    @OneToMany(mappedBy="message", fetch = FetchType.EAGER)
	private Set<Comment> comments;

    private Long userId;
    
    @OneToMany(mappedBy="message", fetch = FetchType.EAGER)
	private Set<Like> likes;
    

	public Message() {
    }
	
	public Set<Like> getLikes() {
		return likes;
	}

	public void setLikes(Set<Like> likes) {
		this.likes = likes;
	}

    public Set<Comment> getComments() {
		return comments;
	}

	public void setComments(Set<Comment> comments) {
		this.comments = comments;
	}	

    public Message(String text, String filename) {
        this.text = text;
        this.filename = filename;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getFilename() {
        return filename;
    }

    public void setFilename(String filename) {
        this.filename = filename;
    }

    public Date getDate() {
        return date;
    }

    public void setDate(Date date) {
        this.date = date;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getUserId() {
        return userId;
    }

    public void setUserId(Long userId) {
        this.userId = userId;
    }
}
