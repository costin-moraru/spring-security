package com.demo.security.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.UserModel;

@Repository
public interface UserRepository extends JpaRepository<UserModel, Integer>{

	Optional<UserModel> findOneByUsername(String username);

	Optional<UserModel> findOneById(Integer userId);
}
