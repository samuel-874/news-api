package com.javaMadeEasy.Entertainment.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import com.javaMadeEasy.Entertainment.Responce.ResponseHandler;
import com.javaMadeEasy.Entertainment.authentication.repository.UserRepository;
import com.javaMadeEasy.Entertainment.authentication.user.User;
import com.javaMadeEasy.Entertainment.authentication.user.UserDto;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;

import com.javaMadeEasy.Entertainment.Entity.Post;
import com.javaMadeEasy.Entertainment.Repository.PostRepository;
import org.springframework.web.client.HttpClientErrorException;

@Service


public class PostService {
	@Autowired
	private  PostRepository postRepository;
	@Autowired
	private  UserRepository userRepository;

	public PostService(PostRepository postRepository) {
		this.postRepository = postRepository;
	}

	public Post save(Post post) {
		postRepository.save(post);
		return post;
	}

	public List<Post> getPost() {
		List<Post> postList = postRepository.findAll();
		return postList;
	}

	public ResponseEntity<Object> findById(int id) {
		return  ResponseHandler.responseBuilder("Post Fetched Successfully:", 200,postRepository.findById(id));
		}


	public ResponseEntity<Object> removeLike(int id) {
		try {
			Post post =postRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("post  not found"));
			return  ResponseHandler.responseBuilder("Post Fetched Successfully:", 200,postRepository.findById(id));
		} catch (EntityNotFoundException e) {
			return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
		} catch (HttpClientErrorException.Unauthorized e) {
			return ResponseEntity.status(HttpStatus.UNAUTHORIZED).body("You are not permitted to access this service.");
		}

	}



	public ResponseEntity<Object> savePost(Post post,int user_id ){
		Optional<User> optionalUser =  userRepository.findById(user_id);
		UserDto userDto = new UserDto();
		if(!optionalUser.isPresent()) {

			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		User user =  optionalUser.get();
			post.setUser(user);
		return  ResponseHandler.responseBuilder("Post Created Successfully :", 201,postRepository.save(post));
	}

	public ResponseEntity<Object> updatePost(@PathVariable int postId, @RequestBody Post post) {

		Optional<Post> optionalPost = postRepository.findById(postId);
		if (!optionalPost.isPresent()) {
			return new ResponseEntity<>(HttpStatus.NOT_FOUND);
		}
		Post existingPost = optionalPost.get();
		existingPost.setTitle(post.getTitle());
		existingPost.setBody(post.getBody());
		return  ResponseHandler.responseBuilder("Post Updated successfully:", 201, postRepository.save(existingPost));
	}
	
	
	
    public ResponseEntity<Void> deletePost(@PathVariable int id) {
        Optional<Post> optionalPost = postRepository.findById(id);
        if (!optionalPost.isPresent()) {
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        Post post = optionalPost.get();
        postRepository.delete(post);
        return new ResponseEntity<>(HttpStatus.ACCEPTED);
    }

   
    public ResponseEntity<Post> getPostById(@PathVariable("id") int id) {
        Post post = postRepository.getById(id);

        if (post == null) {
            return new ResponseEntity<Post>(HttpStatus.NOT_FOUND);
        } else {
            Map<String, Object> response = new HashMap<>();
            response.put("statusCode", HttpStatus.OK.value());
            response.put("message", "Post retrieved successfully");

            Map<String, Object> data = new HashMap<>();
            data.put("post", post);

            response.put("data", data);

            return new ResponseEntity<Post>(HttpStatus.OK);
        }
    }


    
}
