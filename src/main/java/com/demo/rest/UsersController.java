package com.demo.rest;

import java.util.Arrays;
import java.util.List;

import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.UserModel;

@RestController
@RequestMapping("/admin/api/users")
public class UsersController {
	
	private static final List<UserModel> users = Arrays.asList(
			new UserModel(1, "admin", "user", "administrato"),
			new UserModel(2, "user", "user", "user"),
			new UserModel(3, "operator", "user", "operator")
			);
	
	@GetMapping
	public List<UserModel> getUsers() {
		return users;
	}
	
	@PostMapping
	public void save(@RequestBody UserModel user) {
		System.out.println("New user: " + user);
	}
	
	@DeleteMapping("{userId}")
	public void deleteUser(@PathVariable(name = "userId") Integer userId) {
		System.out.println("User id: " + userId);
	}

}
