package com.demo.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.method.configuration.EnableGlobalMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.builders.WebSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;

import com.demo.security.filter.JwtCheckTokenFilter;
import com.demo.security.filter.UsernameAndPasswordJwtFilter;

@Configuration
@EnableWebSecurity
@EnableGlobalMethodSecurity(prePostEnabled = true)
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	
	private final MySecurityUserService mySecurityUserService;
	
	@Autowired
	public AppSecurityConfig(PasswordEncoder passwordEncoder, MySecurityUserService mySecurityUserService) {
		this.passwordEncoder = passwordEncoder;
		this.mySecurityUserService = mySecurityUserService;
	}
	
	

	@Override
	public void configure(WebSecurity web) throws Exception {
		web.ignoring()
			.antMatchers("/", "index", "js", "css", "images")
            .antMatchers(HttpMethod.OPTIONS, "/**")
            .antMatchers("/app/**/*.{js,html}")
            .antMatchers("/i18n/**")
            .antMatchers("/content/**")
            .antMatchers("/h2-console/**")
            .antMatchers("/swagger-ui/index.html")
            .antMatchers("/test/**");
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // disabilita il csrf attack
				.sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
				.and()
				.addFilter(new UsernameAndPasswordJwtFilter(authenticationManager()))
				.addFilterAfter(new JwtCheckTokenFilter(), UsernameAndPasswordJwtFilter.class)
				.authorizeRequests()
				.antMatchers("/api/**").authenticated()
				.and()
				.httpBasic();
	}

	
	
	@Override
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {
		auth.authenticationProvider(daoAuthenticationProvider());
	}

	@Bean
	public DaoAuthenticationProvider daoAuthenticationProvider() {
		DaoAuthenticationProvider authenticationProvider = new DaoAuthenticationProvider();
		authenticationProvider.setPasswordEncoder(passwordEncoder);
		authenticationProvider.setUserDetailsService(mySecurityUserService);
		
		return authenticationProvider;
	}
	
//	@Override
//	@Bean
//	protected UserDetailsService userDetailsService() {
//		UserDetails userAdmin = User.builder()
//				.username("admin")
//				.password(passwordEncoder.encode("admin"))
////				.roles(UserRole.ADMIN.name()) // ROLE_ADMIN
//				.authorities(UserRole.ADMIN.getAuthorities())
//				.build(); 
//		
//		UserDetails user = User.builder()
//				.username("user")
//				.password(passwordEncoder.encode("user"))
////				.roles(UserRole.USER.name()) // ROLE_USER
//				.authorities(UserRole.USER.getAuthorities())
//				.build();
//		
//		return new InMemoryUserDetailsManager(
//				userAdmin,
//				user
//		);
//		
//	}
	
	
	
	

}
