package com.demo.security.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.demo.model.Authority;

@Repository
public interface AuthorityRepository extends JpaRepository<Authority, String>{

}
