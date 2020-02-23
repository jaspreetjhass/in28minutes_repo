package com.example.demo.models;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Post {

	@Id
	private Integer postId;
	private String message;
	@ManyToOne
	@JoinColumn(name="user_id", nullable=false,insertable=false,updatable=false )
	private User user;
	
}
