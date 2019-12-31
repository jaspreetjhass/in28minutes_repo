package com.example.demo.resources;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyInt;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.util.Arrays;
import java.util.List;

import org.junit.jupiter.api.Test;
import org.skyscreamer.jsonassert.JSONAssert;
import org.skyscreamer.jsonassert.JSONCompareMode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockHttpServletResponse;
import org.springframework.test.context.junit.jupiter.web.SpringJUnitWebConfig;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.RequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;

import com.example.demo.models.Post;
import com.example.demo.models.User;
import com.example.demo.services.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;

@SpringJUnitWebConfig(classes = { UserResource.class })
@AutoConfigureMockMvc
@EnableWebMvc
public class UserResourceTestWithSpringJunitWebConfig {

	@Autowired
	private MockMvc mvc;

	@MockBean
	private UserService service;

	@Test
	public void findUserById() throws Exception {

		final String expectedString = "{userId:null,userName:\"jaspreet\",posts:[]}";

		when(service.findUserById(1)).thenReturn(new User(null, "jaspreet", Arrays.asList()));
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/users/1")
				.accept(MediaType.APPLICATION_JSON);
		final MvcResult result = mvc.perform(requestBuilder).andReturn();
		final String responseString = result.getResponse().getContentAsString();
		JSONAssert.assertEquals(expectedString, responseString, JSONCompareMode.LENIENT);

	}

	@Test
	public void findAllUser() throws Exception {

		final String expectedResponse = "[{userId:1,userName:\"user1\",posts:[{postId:1,message:\"post1\"},{postId:2,message:\"post2\"}]}]";

		final List<User> userList = Arrays
				.asList(new User(1, "user1", Arrays.asList(new Post(1, "post1"), new Post(2, "post2"))));

		when(service.findAllUser()).thenReturn(userList);
		final RequestBuilder requestBuilder = MockMvcRequestBuilders.get("http://localhost:8080/users")
				.accept(MediaType.APPLICATION_JSON_VALUE);
		final MvcResult result = mvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String actualResponse = response.getContentAsString();

		JSONAssert.assertEquals(expectedResponse, actualResponse, false);

	}

	@Test
	public void addUser() throws Exception {

		final String expectedResponse = "{userId:1,userName:\"user1\",posts:[{postId:1,message:\"post1\"}]}";
		final User user = new User(1, "user1", Arrays.asList(new Post(1, "post1")));
		when(service.addUser(any())).thenReturn(user);

		final ObjectMapper mapper = new ObjectMapper();

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.post("http://localhost:8080/users")
				.accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(user));

		final MvcResult result = mvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String actualResponse = response.getContentAsString();

		JSONAssert.assertEquals(expectedResponse, actualResponse, false);

	}

	@Test
	public void updateUser() throws Exception {

		final String expectedResponse = "{userId:1,userName:\"user2\",posts:[{postId:1,message:\"post1\"}]}";
		final User user = new User(1, "user2", Arrays.asList(new Post(1, "post1")));
		when(service.updateUser(anyInt(), any())).thenReturn(user);

		final ObjectMapper mapper = new ObjectMapper();

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.put("http://localhost:8080/users/1")
				.accept(MediaType.APPLICATION_JSON_VALUE).contentType(MediaType.APPLICATION_JSON_VALUE)
				.content(mapper.writeValueAsString(user));

		final MvcResult result = mvc.perform(requestBuilder).andReturn();
		final MockHttpServletResponse response = result.getResponse();
		final String actualResponse = response.getContentAsString();

		JSONAssert.assertEquals(expectedResponse, actualResponse, false);

	}

	@Test
	public void deleteUser() throws Exception {

		final RequestBuilder requestBuilder = MockMvcRequestBuilders.delete("http://localhost:8080/users/1");

		mvc.perform(requestBuilder).andExpect(status().isOk());
		verify(service, times(1)).deleteUser(1);

	}

}
