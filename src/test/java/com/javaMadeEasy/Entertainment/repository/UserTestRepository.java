package com.javaMadeEasy.Entertainment.repository;

import com.javaMadeEasy.Entertainment.authentication.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserTestRepository extends JpaRepository<User,Integer> {
    List<User> findByEmail(String email);
}
