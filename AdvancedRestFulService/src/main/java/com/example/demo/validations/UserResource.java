package com.example.demo.validations;

import java.net.URI;
import java.util.Optional;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

@RestController
public class UserResource {

	@Autowired
	private UserRepo repo;

	@GetMapping(path = { "/users/{userId}" },produces = {"application/json"})
	public @ResponseBody User getUser(@PathVariable Integer userId) {
		Optional<User> user = repo.findById(userId);

		if (!user.isPresent())
			throw new UserNotFoundException("user not found");

		return user.get();
	}

	@PostMapping(path = "/users",produces = {"application/json"})
	public @ResponseBody ResponseEntity<User> addUser(@Valid @RequestBody User user) {
		User outputUser = repo.save(user);
		URI uri = ServletUriComponentsBuilder.fromCurrentRequestUri().path("{userId}")
				.buildAndExpand(new Object[] { outputUser.getUserId() }).toUri();
		return ResponseEntity.created(uri).body(outputUser);
	}

}
