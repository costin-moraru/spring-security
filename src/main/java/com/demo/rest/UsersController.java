package com.demo.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.UserModel;

@RestController
@RequestMapping("/api/users")
public class UsersController {
	
	private static final List<UserModel> users = Arrays.asList(
			new UserModel(1, "admin", "user", "administrato"),
			new UserModel(1, "user", "user", "user"),
			new UserModel(1, "operator", "user", "operator")
			);
	
	@GetMapping
	public List<UserModel> getUsers() {
		return users;
	}

}
