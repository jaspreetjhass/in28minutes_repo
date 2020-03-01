package com.example.demo2.repositories;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

@DataJpaTest
public class UserRepositoryTest {

	@Autowired
	private UserRepository repo;

	@Test
	public void findByUserIdTest() {
		assertEquals("jaspreetjhass", repo.findById(123).get().getUserName());
	}

	@Test
	public void findAllUserTest() {

		List<User> expectedList = new ArrayList<User>();
		repo.findAll().forEach(expectedList::add);

		assertEquals(2, expectedList.size());

	}

	@Test
	public void saveUserTest() {

		assertNotNull(repo.save(new User()));
	}

	@Test
	public void deleteUserTest() {

		repo.deleteById(1);
	}

	@Test
	public void updateUserTest() {
		assertNotNull(repo.save(new User()));
	}

}
