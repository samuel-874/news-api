package com.javaMadeEasy.Entertainment.repository;

import com.javaMadeEasy.Entertainment.Entity.Comment;
import com.javaMadeEasy.Entertainment.authentication.user.User;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CommentTestRepository extends JpaRepository<Comment,Long> {
}
