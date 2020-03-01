package com.example.demo.resources;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.services.UserService;

@SpringJUnitWebConfig(classes = { UserResource.class })
public class UserResourceTestWithSpringJunitWebConfig2 {

	@MockBean
	private UserService service;

	@Autowired
	private UserResource userResource;

	@Test
	public void findUserById() throws Exception {

		final User expectedUser = User.builder().userId(1).userName("userName1").build();
		expectedUser.setPosts(Arrays.asList(Post.builder().postId(1).message("message1").build()));

		when(service.findUserById(1)).thenReturn(expectedUser);

		User actualUser = userResource.findUserById(1);
		assertEquals(expectedUser.getUserName(), actualUser.getUserName());
	}

	@Test
	public void findAllUser() throws Exception {

		User expectedUser = User.builder().userId(1).userName("user1").build();
		expectedUser.setPosts(Arrays.asList(Post.builder().postId(1).message("post1").build(),
				Post.builder().postId(2).message("post2").build()));
		final List<User> expectedUserList = Arrays.asList(expectedUser);

		when(service.findAllUser()).thenReturn(expectedUserList);

		List<User> actualUserList = userResource.findAllUser();
		assertThat(actualUserList).hasSize(1);
	}

	@Test
	public void addUser() throws Exception {

		User expectedUser = User.builder().userId(1).userName("user1").build();
		expectedUser.setPosts(Arrays.asList(Post.builder().postId(1).message("post1").user(expectedUser).build()));

		when(service.addUser(any())).thenReturn(expectedUser);

		User actualUser = userResource.addUser(expectedUser).getContent();
		assertEquals(expectedUser.getUserId(), actualUser.getUserId());
	}

	@Test
	public void updateUser() throws Exception {

		User expectedUser = User.builder().userId(1).userName("user2").build();
		expectedUser.setPosts(Arrays.asList(Post.builder().postId(1).message("post1").build()));

		when(service.updateUser(anyInt(), any())).thenReturn(expectedUser);

		User actualUser = userResource.updateUser(1, expectedUser);
		assertEquals(expectedUser.getUserName(), actualUser.getUserName());
	}

	@Test
	public void deleteUser() throws Exception {

		userResource.deleteUser(1);
		verify(service, times(1)).deleteUser(1);

	}

}
