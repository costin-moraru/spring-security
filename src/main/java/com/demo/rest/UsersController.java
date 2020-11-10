package com.demo.rest;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.demo.model.UserModel;
import com.demo.security.AuthoritiesConstants;
import com.demo.security.MySecurityUserService;

@RestController
@RequestMapping("/admin/api/users")
public class UsersController {
	
	String name;
	private final Logger log = LoggerFactory.getLogger(UsersController.class);

	@Autowired
	private MySecurityUserService userService;
	
	@GetMapping
	@PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<List<UserModel>> getUsers() {
		log.debug("Get all users");
		List<UserModel> users = userService.findAll();
		log.debug("users: {}", users);
		return new ResponseEntity<List<UserModel>>(users, HttpStatus.OK);
	}
	
	@PostMapping
	@PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<UserModel> save(@RequestBody UserModel user) {
		return new ResponseEntity<UserModel>(userService.save(user), HttpStatus.CREATED);
	}
	
	@GetMapping("/{userId}")
	@PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public ResponseEntity<UserModel> getUserById(@PathVariable(name = "userId") Integer userId) {
		return new ResponseEntity<UserModel>(userService.getUserById(userId), HttpStatus.OK);
	}
	
	@DeleteMapping("{userId}")
	@PreAuthorize("hasAnyAuthority(\"" + AuthoritiesConstants.ADMIN + "\")")
	public void deleteUser(@PathVariable(name = "userId") Integer userId) {
		System.out.println("User id: " + userId);
	}

}
