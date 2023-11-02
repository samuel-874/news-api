package com.javaMadeEasy.Entertainment.repository;

import com.javaMadeEasy.Entertainment.Entity.Post;
import com.javaMadeEasy.Entertainment.authentication.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PostTestRepository extends JpaRepository<Post,Integer> {
}
