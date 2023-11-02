package com.javaMadeEasy.Entertainment.Service;

import com.javaMadeEasy.Entertainment.Entity.Like;
import com.javaMadeEasy.Entertainment.Entity.Post;
import com.javaMadeEasy.Entertainment.Repository.LikeRepository;
import com.javaMadeEasy.Entertainment.Repository.PostRepository;
import com.javaMadeEasy.Entertainment.Responce.ResponseHandler;
import com.javaMadeEasy.Entertainment.authentication.repository.UserRepository;
import com.javaMadeEasy.Entertainment.authentication.user.User;
import jakarta.persistence.EntityNotFoundException;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;
import java.util.Optional;

@Service
public class LikeService {
    @Autowired
    LikeRepository likeRepository;
    @Autowired
    PostRepository postRepository;

       @Autowired
       UserRepository userRepository;

    private static final Logger log = LoggerFactory.getLogger(LikeService.class);

    public ResponseEntity<Object> getLikesByPostId(long postId) {
        List<Like> likes = likeRepository.findByPostId(postId);
        if (!likes.isEmpty()) {
            return ResponseHandler.responseBuilder("Likes Fetched Successfully:", 200, likes);
        } else {
            throw new EntityNotFoundException("No likes found with postId: " + postId);
        }
    }


    public int getLikeCount(long postId){
        return likeRepository.findAll().size();
    }






    public ResponseEntity<String> removeLikeFromPost(Long postId, int userId) {

        Like like = likeRepository.findByPostIdAndUserId(postId, userId).orElseThrow(() -> new EntityNotFoundException("Like not found"));

        likeRepository.delete(like);
        return ResponseEntity.ok("post liked successfully");
    }
    
    public ResponseEntity<String> addPostLike(@PathVariable int postId,@RequestParam int userId) {
        try {
           Post post = postRepository.findById(postId).orElseThrow(() -> new EntityNotFoundException("Post not found"));
            Optional<User> optionalUser = userRepository.findById(userId);
            if(!optionalUser.isPresent()){
                log.error("user with id -" + userId + " not found");
                return new ResponseEntity<>(HttpStatus.NOT_FOUND);
            }
            User user = optionalUser.get();
           Like like =new Like(post,user);
           likeRepository.save(like);
            
            return ResponseEntity.ok("Post liked successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while liking the post.");
        }
    }


    public ResponseEntity<String> removeLike(Long postId, int userId) {
        try {
            Like like = likeRepository.findByPostIdAndUserId(postId, userId).orElseThrow(() -> new EntityNotFoundException("Like not found"));
            int likeId = Math.toIntExact(like.getId());
            likeRepository.deleteById(likeId);

            return ResponseEntity.ok("Post unliked successfully.");
        } catch (EntityNotFoundException e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while unliking the post.");
        }
    }

    
    


}
