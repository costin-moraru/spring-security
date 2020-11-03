package com.demo.security.repository;

import java.util.Optional;

import com.demo.model.UserModel;

public interface UserRepository {

	Optional<UserModel> findOneByUsername(String username);
}
