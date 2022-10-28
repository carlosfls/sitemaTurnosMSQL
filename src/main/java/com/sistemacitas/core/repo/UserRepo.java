package com.sistemacitas.core.repo;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.sistemacitas.core.entity.User;

public interface UserRepo extends JpaRepository<User, Long>{

	Optional<User>findByUsername(String username);
}
