package com.demo.security;

import java.util.Set;

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
	
	
}
