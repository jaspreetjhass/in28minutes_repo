package com.example.demo.services;

import static org.hamcrest.CoreMatchers.equalTo;
import static org.hamcrest.CoreMatchers.is;
import static org.hamcrest.MatcherAssert.assertThat;
import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

@SpringJUnitConfig(classes = UserService.class)
public class UserServiceTest {

	@MockBean
	private UserRepository repo;

	@Autowired
	private UserService service;

	@Test
	public void findByUserIdTest() {

		List<Post> posts = Arrays.asList(new Post(1, "hello swagger"));
		User user = new User(1, "jaspreetjhass", posts);
		Optional<User> optional = Optional.of(user);

		when(repo.findById(anyInt())).thenReturn(optional);
		assertEquals("jaspreetjhass", service.findUserById(123).getUserName());
		verify(repo).findById(123);
		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		verify(repo).findById(captor.capture());
		assertEquals(new Integer(123), captor.getValue());

	}

	@Test
	public void findAllUserTest() {

		Iterable<User> iterable = Arrays.asList(new User(1, "jaspreetjhass", null), new User(2, "harrydosanjh", null));
		when(repo.findAll()).thenReturn(iterable);
		List<User> expectedList = service.findAllUser();
		assertEquals(2, expectedList.size());

	}

	@Test
	public void saveUserTest() {

		User user = new User();
		when(repo.save(any())).thenReturn(user);
		assertNotNull(service.addUser(new User()));

	}

	@Test
	public void deleteUserTest() {

		service.deleteUser(1);
		verify(repo, times(1)).deleteById(1);
	}

	@Test
	public void updateUserTest() {

		User user = new User(1, "user2", Arrays.asList(new Post(1, "message1"), new Post(2, "message2")));
		Optional<User> optional = Optional.of(new User(1, "user1", null));
	
		when(repo.findById(1)).thenReturn(optional);
		
		User outputUser = service.updateUser(1, user);
		assertEquals("user2", outputUser.getUserName());
		assertThat(outputUser.getPosts().size(), is(equalTo(2)));

	}

}
