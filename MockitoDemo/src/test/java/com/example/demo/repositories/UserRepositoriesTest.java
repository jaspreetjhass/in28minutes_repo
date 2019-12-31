package com.example.demo.repositories;

import static org.junit.Assert.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import com.example.demo.models.Post;
import com.example.demo.models.User;

@SpringJUnitConfig
//@ContextConfiguration(classes = { AppTestConfig.class })
public class UserRepositoriesTest {

	@MockBean
	private UserRepository repo;

	@Test
	public void findByUserIdTest() {

		List<Post> posts = Arrays.asList(new Post(1, "hello swagger"));
		User user = new User(1, "jaspreetjhass", posts);
		Optional<User> optional = Optional.of(user);

		when(repo.findById(anyInt())).thenReturn(optional);
		assertEquals("jaspreetjhass", repo.findById(123).get().getUserName());
		verify(repo).findById(123);
		ArgumentCaptor<Integer> captor = ArgumentCaptor.forClass(Integer.class);
		verify(repo).findById(captor.capture());
		assertEquals(new Integer(123), captor.getValue());

	}

	@Test
	public void findAllUserTest() {

		Iterable<User> iterable = Arrays.asList(new User(1, "jaspreetjhass", null), new User(2, "harrydosanjh", null));
		when(repo.findAll()).thenReturn(iterable);
		List<User> expectedList = new ArrayList<User>();
		repo.findAll().forEach(expectedList::add);

		assertEquals(2, expectedList.size());

	}

	@Test
	public void saveUserTest() {

		User user = new User();
		
		when(repo.save(any())).thenReturn(user);
		assertNotNull(repo.save(new User()));

	}
	
	@Test
	public void deleteUserTest() {

		repo.deleteById(1);
		verify(repo,times(1)).deleteById(1);
	}
	
	@Test
	public void updateUserTest() {

		User user = new User();
		when(repo.save(any())).thenReturn(user);
		assertNotNull(repo.save(new User()));

	}

}
