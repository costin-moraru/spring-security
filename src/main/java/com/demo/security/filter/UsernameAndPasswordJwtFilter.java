package com.demo.security.filter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.Date;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import com.demo.security.UsernamePasswordAuthModel;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;


public class UsernameAndPasswordJwtFilter extends UsernamePasswordAuthenticationFilter{
	
	private final AuthenticationManager authenticationManager;

	public UsernameAndPasswordJwtFilter(AuthenticationManager authenticationManager) {
		this.authenticationManager = authenticationManager;
	}

	@Override
	public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response)
			throws AuthenticationException {
		

		try {
			UsernamePasswordAuthModel authBody = new ObjectMapper().readValue(request.getInputStream(), UsernamePasswordAuthModel.class);
			Authentication authentication = new UsernamePasswordAuthenticationToken(
					authBody.getUsername(), 
					authBody.getPassword()
					);
			Authentication resultAuthentication = authenticationManager.authenticate(authentication);
			return resultAuthentication;
		} catch (IOException e) {
			throw new RuntimeException();
		}
	}

	@Override
	protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain,
			Authentication authResult) throws IOException, ServletException {
		
		String secretKey = "qwertyuiopasdfghjklzxcvbnm1234567890qwertyuiopasdfghjklzxcvbnm1234567890";
		
		// genero il mio token custom
		String token = Jwts.builder()
				.setSubject(authResult.getName())
				.claim("authorities", authResult.getAuthorities())
				.setIssuedAt(new Date())
				.setExpiration(java.sql.Date.valueOf(LocalDate.now().plusDays(30)))
				.signWith(Keys.hmacShaKeyFor(secretKey.getBytes()))
				.compact();
				
		response.addHeader("Authorization", "Bearer " + token);
	}

	
	


}
