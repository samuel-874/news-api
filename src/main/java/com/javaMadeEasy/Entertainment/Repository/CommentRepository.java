package com.javaMadeEasy.Entertainment.Repository;

import com.javaMadeEasy.Entertainment.Entity.Comment;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CommentRepository extends JpaRepository<Comment,Integer> {
    Comment findAllCommentByPostId(long postId);

    List<Comment> findByPostId(int postId);
}
