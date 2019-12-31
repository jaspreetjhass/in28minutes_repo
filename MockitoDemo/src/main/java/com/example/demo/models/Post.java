package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

@Entity
public class Post {

	@Id
	private Integer postId;
	private String message;
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false,insertable=false,updatable=false )
	private User user;
	
	public Post() {
	}

	public Post(Integer postId, String message) {
		super();
		this.postId = postId;
		this.message = message;
	}

	public Integer getPostId() {
		return postId;
	}
	public void setPostId(Integer postId) {
		this.postId = postId;
	}
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	
	@Override
	public String toString() {
		return "Post [postId=" + postId + ", message=" + message + "]";
	}
	
}
