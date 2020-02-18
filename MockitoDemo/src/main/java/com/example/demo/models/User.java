package com.example.demo.models;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.OneToMany;

@Entity
public class User {

	@Id
	@GeneratedValue
	private Integer userId;
	private String userName;
	@OneToMany(fetch = FetchType.EAGER,cascade = CascadeType.ALL)
	@JoinColumn(name="user_id",nullable=false)
	private Collection<Post> posts = new ArrayList<Post>();
	
	public User() {
	}
	
	public User(Integer userId, String userName, List<Post> posts) {
		super();
		this.userId = userId;
		this.userName = userName;
		this.posts = posts;
	}

	public Integer getUserId() {
		return userId;
	}
	public void setUserId(Integer userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public Collection<Post> getPosts() {
		return posts;
	}
	public void setPosts(Collection<Post> posts) {
		this.posts = posts;
	}

	@Override
	public String toString() {
		return "User [userId=" + userId + ", userName=" + userName + ", posts=" + posts + "]";
	}
}
