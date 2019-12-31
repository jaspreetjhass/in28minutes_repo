package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.example.demo.exceptions.ApplicationException;
import com.example.demo.models.User;
import com.example.demo.repositories.UserRepository;

@Service
public class UserService {

	@Autowired
	private UserRepository repository;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserService.class);

	public User findUserById(final Integer userId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetch user having id " + userId);
		final Optional<User> optional = repository.findById(userId);
		if (!optional.isPresent()) {
			LOGGER.error("user not found");
			throw new ApplicationException("user not found");
		}
		LOGGER.trace("Exit from method");
		return optional.get();
	}

	public List<User> findAllUser() {
		LOGGER.trace("Enter into method");
		final List<User> list = new ArrayList<>();
		LOGGER.info("fetching users from database");
		repository.findAll().forEach(list::add);
		LOGGER.trace("Exit from method");
		return list;
	}

	public User addUser(final User user) {
		LOGGER.trace("Enter into method");
		LOGGER.info("saving user in database");
		final User outputUser = repository.save(user);
		LOGGER.trace("Exit from method");
		return outputUser;
	}

	public User updateUser(final Integer userId, final User user) {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetch user with id "+userId);
		final Optional<User> optional = repository.findById(userId);
		if (!optional.isPresent()) {
			LOGGER.error("user not found");
			throw new ApplicationException("user not found");
		}
		LOGGER.info("updating user in the database");
		final User outputUser = optional.get();
		outputUser.setUserName(user.getUserName());
		outputUser.setPosts(user.getPosts());
		LOGGER.trace("Exit from method");
		return outputUser;
	}

	public void deleteUser(final Integer userId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("delete user from database");
		repository.deleteById(userId);
		LOGGER.trace("Exit from method");
	}
}
