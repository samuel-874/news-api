package com.javaMadeEasy.Entertainment.Repository;

import com.javaMadeEasy.Entertainment.Entity.Comment;
import com.javaMadeEasy.Entertainment.Entity.Like;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LikeRepository extends JpaRepository<Like,Integer> {
    Optional<Like> findByPostIdAndUserId(Long postId, int userId);
    
    List<Like> findByPostId(Long postId);


    void deleteByPostIdAndUserId(Long postId, int userId);
}
