package com.javaMadeEasy.Entertainment.Controller;

import com.javaMadeEasy.Entertainment.Entity.Post;
import com.javaMadeEasy.Entertainment.repository.PostTestRepository;
import com.javaMadeEasy.Entertainment.repository.UserTestRepository;
import com.javaMadeEasy.Entertainment.authentication.AUth.AuthenticationResponse;
import com.javaMadeEasy.Entertainment.authentication.AUth.AuthenticationService;
import com.javaMadeEasy.Entertainment.authentication.user.Role;
import com.javaMadeEasy.Entertainment.authentication.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;


import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class UserControllerTest {


    private String baseUrl ="http://localhost";

    private static RestTemplate restTemplate;
    @Autowired
    private UserTestRepository userTest;
    @Autowired
    private PostTestRepository postTest;
  @Autowired
    private AuthenticationService userService;

    @LocalServerPort
    private int port;

    @Test
    void contextLoads() {
    }

    @BeforeAll
    static void init(){
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
      baseUrl = baseUrl.concat(":").concat(port + "").concat("/api");

    }

    @Test
    @Sql(statements="INSERT INTO `users` (id, first_name, last_name, email, password, role) VALUES (8, 'John', 'Doe', 'johndoe@example.com', '$2a$10$JdcRsyOy1Q', 'USER')", executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements ="INSERT INTO `post` (id, title, body, user_id, date_created) VALUES (1, 'My first post', 'This is the body of my first post.', 8, '2023-05-02 10:00:00');", executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements ="DELETE FROM `post` WHERE id=1;", executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements="DELETE FROM `users` WHERE id=8;", executionPhase =Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getPost() {
        Post post = restTemplate.getForObject(baseUrl + "/posts", Post.class);


        assertAll(
                ()-> assertNotNull(postTest.findAll()),
                ()->assertEquals(1, postTest.findAll().size())
        );
    }

    @Test
    @Sql(statements="INSERT INTO `users` (id, first_name, last_name, email, password, role) VALUES (3, 'bola', 'tito', 'bolatito@example.com', '$2a$10$JdcRsyOy1Q', 'USER')", executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements="INSERT INTO `users` (id, first_name, last_name, email, password, role) VALUES (4, 'ola', 'isreal', 'isrealola@example.com', '$2a$10$JdcRsyOy1Q', 'USER')", executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements="DELETE FROM `users` where id = 3;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements="DELETE FROM `users` where id = 4;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    void getUsers() {
    List<User> user = restTemplate.getForObject(baseUrl + "/getUsers", List.class);
    assertAll(
            ()-> assertNotNull(userTest.findAll()),
            ()-> assertEquals(user.size(),userTest.findAll().size())
    );
    }

    @Sql(statements="INSERT INTO `users` (id, first_name, last_name, email, password, role) VALUES (5, 'kola', 'wole', 'kolawole@example.com', '$2a$10$JdcRsyOy1Q', 'USER')", executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements="INSERT INTO `users` (id, first_name, last_name, email, password, role) VALUES (6, 'baba', 'joy', 'babajoy@example.com', '$2a$10$JdcRsyOy1Q', 'USER')", executionPhase =Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements="DELETE FROM `users` where id = 5;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements="DELETE FROM `users` where id = 6;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)

    @Test
    void testGetOneUser(){
        User user = restTemplate.getForObject(baseUrl + "/users/{id}", User.class,5);
        assertAll(
                ()-> assertNotNull(user),
                ()-> assertNotNull(user.getId()),
                ()-> assertEquals("kola",userTest.findById(5).get().getFirstName())
        );
    }
    @Test
    void testReqisterUser(){
        User user = new User(1, "samuel", "abey", "samuel@gmail.com", "mamzy123", Role.USER);
        User savedUser = restTemplate.postForObject(baseUrl + "/user/register",user,User.class);

        assertAll(
                ()->assertNotNull(user),
                ()->assertEquals(1,user.getId())
        );
    }

    @Test
    void testAuthenticate(){
        User user = new User(2, "olaz", "telecom", "olaztel@gmail.com", "olate123", Role.USER);
        User savedUser = restTemplate.postForObject(baseUrl + "/user/register",user,User.class);

        AuthenticationResponse authenticateUser = restTemplate.postForObject(baseUrl + "/user/authenticate",user, AuthenticationResponse.class);
        System.out.println(authenticateUser);

       assertNotNull(authenticateUser);

    }
}