package com.example.demo.repositories;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.models.Post;
import com.example.demo.models.User;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repository;

	@Test
	public void findUserById() {
		final Optional<User> optional = repository.findById(1);
		assertNotNull(optional.get());
		assertEquals("userName1", optional.get().getUserName());
	}

	@Test
	public void findAllUser() {
		final List<User> list = new ArrayList<>();
		repository.findAll().forEach(list::add);
		assertThat(list).hasSize(4);
	}

	@Test
	public void addUser() {
		final User user = User.builder().userId(6).userName("userName6").build();
		user.setPosts(Arrays.asList(Post.builder().postId(6).message("message6").user(user).build()));
		final User outputUser = repository.save(user);
		assertThat(outputUser).isNotNull();
	}

	@Test
	public void updateUser() {
		final Optional<User> optional = repository.findById(1);
		final User outputUser = optional.get();
		outputUser.setUserName("userName11");
		List<Post> posts = outputUser.getPosts().stream().map(post -> {
			post.setMessage("updatedMessage");
			return post;
		}).collect(Collectors.toList());
		outputUser.setPosts(posts);
		final User expected = repository.save(outputUser);
		assertEquals("userName11", expected.getUserName());
	}

	@Test
	public void deleteUser() {
		final List<User> list = new ArrayList<>();
		repository.findAll().forEach(list::add);
		repository.deleteById(1);
		assertThat(list).hasSize(4);
	}
}
