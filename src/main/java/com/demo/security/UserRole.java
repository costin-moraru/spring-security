package com.demo.security;

import java.util.Set;
import java.util.stream.Collectors;

import org.springframework.security.core.authority.SimpleGrantedAuthority;

import com.google.common.collect.Sets;

public enum UserRole {
	ADMIN(Sets.newHashSet(UserPermission.ADMIN_READ, UserPermission.ADMIN_WRITE, UserPermission.USER_READ)),
	USER(Sets.newHashSet(UserPermission.USER_READ, UserPermission.USER_WRITE));
	
	private final Set<UserPermission> permissions;

	private UserRole(Set<UserPermission> permissions) {
		this.permissions = permissions;
	}

	public Set<UserPermission> getPermissions() {
		return permissions;
	}
	
	public Set<SimpleGrantedAuthority> getAuthorities() {
		Set<SimpleGrantedAuthority> authorities = getPermissions().stream()
			.map(a -> new SimpleGrantedAuthority(a.getPermission()))
			.collect(Collectors.toSet());
		authorities.add(new SimpleGrantedAuthority("ROLE_" + this.name()));
		return authorities;
	}
	
}
