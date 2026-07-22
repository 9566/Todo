package com.tap.spring_mvc.repository;

import com.tap.spring_mvc.model.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User,Long> {
    Optional<User> findByEmail(String email); //SELECT * FROM users WHERE email = .... and Optional<User> — The Return Type
}
