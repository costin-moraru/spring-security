package com.demo.security;

import java.util.concurrent.TimeUnit;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.provisioning.InMemoryUserDetailsManager;
import org.springframework.security.web.authentication.rememberme.PersistentTokenRepository;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class AppSecurityConfig extends WebSecurityConfigurerAdapter {
	
	private final PasswordEncoder passwordEncoder;
	
	private final MySecurityUserService mySecurityUserService;
	
	@Autowired
	public AppSecurityConfig(PasswordEncoder passwordEncoder, MySecurityUserService mySecurityUserService) {
		this.passwordEncoder = passwordEncoder;
		this.mySecurityUserService = mySecurityUserService;
	}

	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http
				.csrf().disable() // disabilita il csrf attack
				.authorizeRequests()
				.antMatchers("/", "index", "js", "css", "images").permitAll() // permetti il traffico verso / e verso index, ecc
				.antMatchers("/api/**").hasAuthority(UserPermission.USER_WRITE.getPermission())
				.antMatchers(HttpMethod.POST, "/admin/api/**").hasAuthority(UserPermission.ADMIN_WRITE.getPermission())
				.antMatchers(HttpMethod.DELETE, "/admin/api/**").hasAnyAuthority(UserPermission.ADMIN_WRITE.getPermission())
				.antMatchers(HttpMethod.GET, "/admin/api/**").hasAnyAuthority(UserPermission.ADMIN_WRITE.getPermission(), UserPermission.ADMIN_READ.getPermission())
				.anyRequest()
				.authenticated()
			.and()
				.formLogin()
					.loginPage("/login").permitAll()
					.defaultSuccessUrl("/area-riservata", true).permitAll()
			.and()
				.rememberMe()
				.tokenValiditySeconds((int)TimeUnit.DAYS.toSeconds(30))
				.key("qwertyuijhgdfsabgtfg")
			.and()
				.logout()
					.logoutUrl("/logout")
					.clearAuthentication(true)
					.invalidateHttpSession(true)
					.deleteCookies("remember-me", "JSESSIONID")
					.logoutRequestMatcher(new AntPathRequestMatcher("/logout", "POST"));
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
