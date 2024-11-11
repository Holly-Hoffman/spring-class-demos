package com.prs.db;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.prs.model.Users;

public interface UsersRepo extends JpaRepository<Users, Integer> {

	Optional<Users> findByUsernameAndPassword(String username, String password);

}
