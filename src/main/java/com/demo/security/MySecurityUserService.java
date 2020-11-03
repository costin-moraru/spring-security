package com.demo.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import com.demo.security.repository.UserRepository;

@Service
public class MySecurityUserService implements UserDetailsService {
	
	private final UserRepository userRepository;
	
	@Autowired
	public MySecurityUserService(@Qualifier("test-repository") UserRepository userRepository) {
		this.userRepository = userRepository;
	}


	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		return userRepository.findOneByUsername(username)
				.orElseThrow(() -> new UsernameNotFoundException("Utente non trovato"));
	}

}
