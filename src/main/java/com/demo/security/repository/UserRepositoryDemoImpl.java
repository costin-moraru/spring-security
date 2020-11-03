package com.demo.security.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Repository;

import com.demo.model.UserModel;
import com.demo.security.UserRole;

@Repository("test-repository")
public class UserRepositoryDemoImpl implements UserRepository {
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public UserRepositoryDemoImpl(PasswordEncoder passwordEncoder) {
		this.passwordEncoder = passwordEncoder;
	}

	@Override
	public Optional<UserModel> findOneByUsername(String username) {
		return getUsers().stream().filter(u -> username.equals(u.getUsername())).findFirst();
	}
	
	private List<UserModel> getUsers() {
		List<UserModel> users = new ArrayList<UserModel>();
		users.add(new UserModel(
					UserRole.ADMIN.getAuthorities(),
					passwordEncoder.encode("admin"), 
					"admin", 
					true, 
					true, 
					true, 
					true)
			);
		users.add(new UserModel(
				UserRole.USER.getAuthorities(),
				passwordEncoder.encode("user"), 
				"user", 
				true, 
				true, 
				true, 
				true)
		);
		return users;
	}

}
