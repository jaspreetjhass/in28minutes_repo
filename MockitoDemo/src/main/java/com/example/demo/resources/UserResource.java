package com.example.demo.resources;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.models.User;
import com.example.demo.services.UserService;

@RestController
public class UserResource {

	@Autowired
	private UserService service;
	private static final Logger LOGGER = LoggerFactory.getLogger(UserResource.class);

	@GetMapping(path = { "/users/{userId}" }, headers = { "accept=application/json" })
	public User findUserById(@PathVariable final Integer userId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetch user having id " + userId);
		final User user = service.findUserById(userId);
		LOGGER.info("posts are: " + user.getPosts());
		LOGGER.trace("Exit from method");
		return user;
	}

	@GetMapping(path = { "/users" }, produces = { MediaType.APPLICATION_JSON_VALUE })
	public List<User> findAllUser() {
		LOGGER.trace("Enter into method");
		LOGGER.info("fetching users from database");
		LOGGER.trace("Exit from method");
		return service.findAllUser();
	}

	@PostMapping(path = { "/users" }, produces = { MediaType.APPLICATION_JSON_VALUE }, consumes = {
			MediaType.APPLICATION_JSON_VALUE })
	public Resource<User> addUser(@RequestBody final User user) {
		LOGGER.trace("Enter into method");
		LOGGER.info("saving user in database and adding relative links");
		
		final Link link = linkTo(methodOn(UserResource.class).findAllUser()).withRel("all-users");
		final Resource<User> resource = new Resource<User>(service.addUser(user));
		resource.add(link);

		LOGGER.trace("Exit from method");
		return resource;
	}

	@PutMapping(path = { "/users/{userId}" })
	public User updateUser(@PathVariable final Integer userId, @RequestBody final User user) {
		LOGGER.trace("Enter into method");
		LOGGER.trace("Exit from method");
		return service.updateUser(userId, user);
	}

	@DeleteMapping(path = { "/users/{userId}" })
	public void deleteUser(@PathVariable final Integer userId) {
		LOGGER.trace("Enter into method");
		LOGGER.info("delete user from database");
		service.deleteUser(userId);
		LOGGER.trace("Exit from method");
	}

}
