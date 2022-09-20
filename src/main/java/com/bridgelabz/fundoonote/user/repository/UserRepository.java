package com.bridgelabz.fundoonote.user.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.fundoonote.user.model.User;

@Repository
public interface UserRepository extends JpaRepository<User, Integer> {

	Optional<User> findByEmail(String email);

}