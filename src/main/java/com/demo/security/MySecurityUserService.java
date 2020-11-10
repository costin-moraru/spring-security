package com.demo.security;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.demo.model.UserModel;
import com.demo.security.repository.UserRepository;

@Service
public class MySecurityUserService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	private final PasswordEncoder passwordEncoder;
	
	@Autowired
	public MySecurityUserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
		this.userRepository = userRepository;
		this.passwordEncoder = passwordEncoder;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findOneByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
	}
	
	public List<UserModel> findAll() {
		List<UserModel> users = userRepository.findAll();
		return users;
	}
	
	public UserModel save(UserModel user) {
		// Codifico la password in chiaro prima di salvare l' entity
		user.setPassword(passwordEncoder.encode(user.getPassword()));
		return userRepository.save(user);
	}
	
	public UserModel getUserById(Integer userId) {
		return userRepository.findOneById(userId).orElseThrow(() -> new IllegalStateException(String.format("User %s not found!", userId)));
	}
	
	public void deleteUserById(Integer userId) {
		userRepository.deleteById(userId);
	}

}
