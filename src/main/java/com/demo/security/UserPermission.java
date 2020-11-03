package com.demo.security;

public enum UserPermission {
	ADMIN_READ("ADMIN_READ"),
	ADMIN_WRITE("ADMIN_WRITE"),
	USER_READ("USER_READ"),
	USER_WRITE("USER_WRITE");
	
	private final String permission;

	private UserPermission(String permission) {
		this.permission = permission;
	}

	public String getPermission() {
		return permission;
	}
	
}
