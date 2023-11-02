package com.javaMadeEasy.Entertainment.Service;

import java.util.List;
import java.util.Optional;

import com.javaMadeEasy.Entertainment.authentication.repository.UserRepository;
import com.javaMadeEasy.Entertainment.authentication.user.User;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.javaMadeEasy.Entertainment.Entity.Comment;
import com.javaMadeEasy.Entertainment.Entity.Post;
import com.javaMadeEasy.Entertainment.Repository.CommentRepository;
import com.javaMadeEasy.Entertainment.Repository.PostRepository;

@Service
public class CommentService {
    @Autowired
    CommentRepository commentRepository;
    
    @Autowired
    PostRepository postRepository;
     @Autowired
     UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(CommentService.class);
    
    public Comment findAllById(long postId){
        return commentRepository.findAllCommentByPostId(postId);
    }
    
    
    public List<Comment> retrieveAllCommentsForAPost(@PathVariable int postId){
        Optional<Post> postOptional= postRepository.findById(postId);
        List<Comment> listOfComment = commentRepository.findByPostId(postId);

        if(!postOptional.isPresent()){
            throw  new RuntimeException("Post with id-"+postId+" was not found");
        }

         return listOfComment;
    }
    
    
    public ResponseEntity<Comment> createComment(@PathVariable int postId, @RequestBody Comment comment,int userId) {
        Optional<Post> optionalPost = postRepository.findById(postId);
        if (!optionalPost.isPresent()) {
            log.error("Post with id" + postId + "not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Optional<User> optionalUser= userRepository.findById(userId);
         if (!optionalUser.isPresent()) {
            log.error("user with id" + userId + "not found");
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }

        Post post = optionalPost.get();
         User user = optionalUser.get();
        comment.setPost(post);
        comment.setUser(user);
        Comment savedComment = commentRepository.save(comment);
        return new ResponseEntity<>(savedComment, HttpStatus.CREATED);
    }

}
