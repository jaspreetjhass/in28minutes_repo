package com.example.demo.hateoas;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.validations.User;
import com.example.demo.validations.UserRepo;

@RestController(value = "uResource")
public class UserResource {

	@Autowired
	private UserRepo repo;

	@GetMapping(path = { "/hateoas/users/{userId}" }, produces = { "application/json" })
	public @ResponseBody Resource<User> getUser(@PathVariable Integer userId) {
		Optional<User> user = repo.findById(userId);

		if (!user.isPresent())
			throw new UserNotFoundException("user not found");

		Link link = linkTo(methodOn(this.getClass()).getAllUser()).withRel("all-users");

		Resource<User> resource = new Resource<User>(user.get());
		resource.add(link);
		
		return resource;
	}

	@GetMapping(path = { "/hateoas/users" }, produces = { "application/json" })
	public @ResponseBody Iterable<User> getAllUser() {

		return repo.findAll();
	}

}
