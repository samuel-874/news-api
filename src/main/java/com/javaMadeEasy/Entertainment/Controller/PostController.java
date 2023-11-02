package com.javaMadeEasy.Entertainment.Controller;

import com.javaMadeEasy.Entertainment.Responce.ResponseHandler;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.javaMadeEasy.Entertainment.Entity.Comment;
import com.javaMadeEasy.Entertainment.Entity.Post;
import com.javaMadeEasy.Entertainment.Service.CommentService;
import com.javaMadeEasy.Entertainment.Service.LikeService;
import com.javaMadeEasy.Entertainment.Service.PostService;


@RestController
@RequestMapping("/posts")
public class PostController {



	@Autowired
	PostService postService;

	@Autowired
	LikeService likeService;

	@Autowired
	CommentService commentService;


	@GetMapping("/{id}")
	public ResponseEntity<Object> getOne(@PathVariable int id) {
		return   postService.findById(id);
	}

	@PostMapping
	public ResponseEntity<Object> createPost(@RequestBody Post post, @RequestParam int  user) {
		return  postService.savePost(post,user);
	}


	@PutMapping("/{id}")
	public ResponseEntity<Object> updatePost(@PathVariable int id, @RequestBody Post post) {
		return  postService.updatePost(id, post);
	}

	@DeleteMapping("/{id}")
	public ResponseEntity<Void> deleteAPost(@PathVariable int id) {
		return postService.deletePost(id);
	}

	@PostMapping("/{postId}/comment")
	public ResponseEntity<Comment> createNewComment(@PathVariable int postId, @RequestBody Comment comment ,@RequestParam int user) {
		return commentService.createComment(postId, comment,user);
	}

	@PostMapping("/{postId}/like")
	public ResponseEntity<String> LikePost(@PathVariable int postId, @RequestParam int userId) {
		return likeService.addPostLike(postId, userId);
	}

	@GetMapping("/{postId}/likes")
	public int getLikes(@PathVariable Long postId){
		return likeService.getLikeCount(postId);
	}


	@DeleteMapping("/{postId}/like/delete")
	public ResponseEntity<String> removePostLike(@PathVariable Long postId, @RequestParam int userId) {
		return likeService.removeLike(postId, userId);
	}

	@GetMapping("/{postId}/comments")
	public ResponseEntity<Object> retrieveAllCommentsForAPost(@PathVariable int postId) {
		return  ResponseHandler.responseBuilder("comment fetched  successfully:", 200, commentService.retrieveAllCommentsForAPost(postId));
	}


}




