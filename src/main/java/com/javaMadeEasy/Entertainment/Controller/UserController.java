package com.javaMadeEasy.Entertainment.Controller;


import com.javaMadeEasy.Entertainment.Responce.ResponseHandler;
import com.javaMadeEasy.Entertainment.Service.PostService;
import com.javaMadeEasy.Entertainment.authentication.AUth.AuthenticationRequest;
import com.javaMadeEasy.Entertainment.authentication.AUth.AuthenticationResponse;
import com.javaMadeEasy.Entertainment.authentication.AUth.AuthenticationService;
import com.javaMadeEasy.Entertainment.authentication.AUth.RegisterRequest;
import com.javaMadeEasy.Entertainment.authentication.repository.UserRepository;
import com.javaMadeEasy.Entertainment.authentication.user.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class UserController {
    @Autowired
    private PostService postService;
    @Autowired
    private AuthenticationService service;
    @Autowired
    private UserRepository userRepo;



    @GetMapping("/posts")
    public ResponseEntity<Object> getPost() {
      return   ResponseHandler.responseBuilder("Posts Fetched Successfully", 200,postService.getPost());

    }

    @PostMapping("/user/register")
    public ResponseEntity<?> register(
            @RequestBody RegisterRequest request) {
         ResponseEntity.ok(service.register(request));
         return service.getSavedUSer(request);
    }


    @PostMapping("/user/authenticate")
    public ResponseEntity<AuthenticationResponse> authenticate (@RequestBody AuthenticationRequest request){
            return  ResponseEntity.ok(service.authenticate(request));
    }
    @GetMapping("/getUsers")
    public List<User> getUsers(){
        List<User> allUsers = userRepo.findAll();
        return allUsers;
    };

    @GetMapping("/users/{id}")
    public ResponseEntity<?> getOneUser(@PathVariable int id){
        return  service.getOne(id);
    }

//    User user = new User("firstName","lastName","samuekk@gmail.com","Mack44");


}