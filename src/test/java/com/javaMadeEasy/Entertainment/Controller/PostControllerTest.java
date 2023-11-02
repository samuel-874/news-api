package com.javaMadeEasy.Entertainment.Controller;

import com.javaMadeEasy.Entertainment.repository.CommentTestRepository;
import com.javaMadeEasy.Entertainment.Entity.Comment;
import com.javaMadeEasy.Entertainment.Entity.Like;
import com.javaMadeEasy.Entertainment.Entity.Post;
import com.javaMadeEasy.Entertainment.repository.PostTestRepository;
import com.javaMadeEasy.Entertainment.repository.UserTestRepository;
import com.javaMadeEasy.Entertainment.authentication.user.Role;
import com.javaMadeEasy.Entertainment.authentication.user.User;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class PostControllerTest {

    @LocalServerPort
    private int port;

    String baseUrl = "http://localhost";

    private static RestTemplate restTemplate;
    @Autowired
    PostTestRepository postTest;

    @Autowired
    UserTestRepository userTest;
    @Autowired
    CommentTestRepository comTest;


    @BeforeAll
    static void init() {
        restTemplate = new RestTemplate();
    }

    @BeforeEach
    void setUp() {
        baseUrl = baseUrl.concat(":").concat(port + "").concat("/posts");

    }


    @Test
    @Sql(statements = "INSERT INTO `users` (id, first_name, last_name, email, password, role) VALUES (2, 'kola', 'wole', 'kolawole@example.com', '$2a$10$JdcRsyOy1Q', 'USER')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "INSERT INTO `post` (id,title,body,user_id) VALUES (1, 'my title' , 'my post body' , 2 )", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(statements = "DELETE FROM `post` where id = 1;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    @Sql(statements = "DELETE FROM `users` where id = 2;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void getOne() {
       Post post = restTemplate.getForObject(baseUrl + "/{id}", Post.class, 1);

        assertNotNull(post);
        assertEquals("my title",post.getTitle());
    }

    @Test
    @Sql(statements = "INSERT INTO `users` (id, first_name, last_name, email, password, role) VALUES (4, 'samuel', 'abey', 'abeysam@example.com', '$2a$10$JdcRsyOy1Q', 'USER')", executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//    @Sql(statements = "DELETE FROM `users` where id = 4;", executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    void createPost() {

        User user = restTemplate.getForObject("http://localhost:"+ port + "/users/{id}",User.class,4);
        Post post = new Post(4, "my post title", "my post Body", user);
        Post savedPost = restTemplate.postForObject(baseUrl + "?user=4", post, Post.class);

        assertAll(
                () -> assertNotNull(postTest.findById(4)),
                ()-> assertEquals(user.getFirstName(),post.getUser().getFirstName()),
                ()-> assertNotNull(user),
                ()->assertNotNull(savedPost)
        );
    }

    @Test
    void updatePost() {
        User user = new User(1, "samuel", "abey", "samuel@gmail.com", "mamzy123", Role.USER);
        User savedUser = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user,User.class);
        Post post = new Post(1, "my post title", "my post Body", user);
        Post savedPost = restTemplate.postForObject(baseUrl + "?user=1", post, Post.class);
        Post editPost = new Post(1, "my post title" , "my post body almost all jamb result has been released",user);
         restTemplate.put(baseUrl + "/{id}",editPost,1);
        Optional<Post> updatedPost = postTest.findById(1);

        assertAll(
                ()-> assertNotNull(updatedPost),
                ()-> assertNotNull(savedUser),
                ()-> assertNotEquals(updatedPost,savedPost),
                ()->assertEquals(updatedPost.get().getUser().getFirstName(),editPost.getUser().getFirstName()),
                ()-> assertEquals("my post title",updatedPost.get().getTitle()),
                ()->assertEquals("my post body almost all jamb result has been released",updatedPost.get().getBody())
        );
    }

    @Test
    void testDeleteAPost() {
        User user = new User(1, "samuel", "abey", "samuel@gmail.com", "mamzy123", Role.USER);
        User savedUser = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user,User.class);
        Post post = new Post(1, "my post title", "my post Body", user);
        Post savedPost = restTemplate.postForObject(baseUrl + "?user=1", post, Post.class);

        restTemplate.delete(baseUrl + "/{id}",1);
        List<Post> repositoryPosts = postTest.findAll();

        assertAll(
                ()-> assertNotNull(savedUser),
                ()->assertNotNull(savedPost),
                ()->assertEquals(0,repositoryPosts.size())
        );
    }

    @Test
    void testCreateNewComment() {
        User user = new User(1, "samuel", "abey", "samuel@gmail.com", "mamzy123", Role.USER);
        User savedUser = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user,User.class);
        Post post = new Post(1, "my post title", "my post Body", user);
        Post savedPost = restTemplate.postForObject(baseUrl + "?user=1", post, Post.class);
        LocalDateTime dateCreated =  LocalDateTime.now();

        Comment comment = new Comment(1,"the main coment",dateCreated,post,user);
        Comment savedCom = restTemplate.postForObject(baseUrl + "/{postId}/comment?user=1",comment,Comment.class,1);


        assertAll(
                ()->assertNotNull(savedCom),
                ()->assertEquals("the main coment",savedCom.getContent())
        );
    }

    @Test
    void likePostTest() {
        User user = new User(1, "samuel", "abey", "samuel@gmail.com", "mamzy123", Role.USER);
        User savedUser = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user,User.class);
        Post post = new Post(1, "my post title", "my post Body", user);
        Post savedPost = restTemplate.postForObject(baseUrl + "?user=1", post, Post.class);

        Like like = new Like(post,user);
        String savedLikes = restTemplate.postForObject(baseUrl +"/{postId}/like?userId=1",like,String.class,1);

        assertAll(
                ()->assertNotNull(savedLikes),
                ()->assertEquals("Post liked successfully.",savedLikes)
        );
    }

    @Test
    void getLike() {
        User user = new User(1, "samuel", "abey", "samuel@gmail.com", "mamzy123", Role.USER);
        User savedUser = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user,User.class);
        Post post = new Post(1, "my post title", "my post Body", user);
        Post savedPost = restTemplate.postForObject(baseUrl + "?user=1", post, Post.class);

             User user2 = new User(2, "bigi", "cola", "bigi@gmail.com", "mamzy123", Role.USER);
        User savedUser2 = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user2,User.class);


        Like like = new Like(post,user);
        String savedLikes = restTemplate.postForObject(baseUrl +"/{postId}/like?userId=1",like,String.class,1);

         Like like2 = new Like(post,user);
        String savedLikes2 = restTemplate.postForObject(baseUrl +"/{postId}/like?userId=2",like,String.class,1);


    int numbersOfLikeForPost1 = restTemplate.getForObject(baseUrl + "/{postId}/likes",Integer.class,1);


    assertEquals(2,numbersOfLikeForPost1);

    }

    @Test
    void removePostLikeTest() {
        User user = new User(1, "samuel", "abey", "samuel@gmail.com", "mamzy123", Role.USER);
        User savedUser = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user,User.class);
        Post post = new Post(1, "my post title", "my post Body", user);
        Post savedPost = restTemplate.postForObject(baseUrl + "?user=1", post, Post.class);


        Like like = new Like(post,user);
        String savedLikes = restTemplate.postForObject(baseUrl +"/{postId}/like?userId=1",like,String.class,1);

        restTemplate.delete(baseUrl +"/{postId}/like/delete?userId=1",1);
//                                      "/{postId}/like/delete"
       List<Post> availablePost = postTest.findAll();




    }

    @Test
    void retrieveAllCommentsForAPost() {

        User user = new User(1, "samuel", "abey", "samuel@gmail.com", "mamzy123", Role.USER);
        User savedUser = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user,User.class);


        User user2 = new User(2, "asebiomo", "roland", "asebiomo@gmail.com", "mamzy123", Role.USER);
        User savedUser2 = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user2,User.class);

        User user3 = new User(3, "olamide", "olabode", "olbode@gmail.com", "mamzy123", Role.USER);
        User savedUser3 = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user3,User.class);

        User user4 = new User(4, "fatiu", "abul", "fatiuabdul@gmail.com", "mamzy123", Role.USER);
        User savedUser4 = restTemplate.postForObject("http://localhost:"+ port + "/api/user/register",user4,User.class);




        Post post = new Post(1, "my post title", "my post Body", user);
        Post savedPost = restTemplate.postForObject(baseUrl + "?user=1", post, Post.class);
        LocalDateTime dateCreated =  LocalDateTime.now();

        Comment firstComment = new Comment(1,"the main coment",dateCreated,post,user);
        Comment savedCom = restTemplate.postForObject(baseUrl + "/{postId}/comment?user=1",firstComment,Comment.class,1);



        Comment secondComment = new Comment(2,"another coment",dateCreated,post,user2);
        Comment savedCom2 = restTemplate.postForObject(baseUrl +  "/{postId}/comment?user=2",secondComment,Comment.class,1);


        Comment thirdComment = new Comment(3,"best coment",dateCreated,post,user3);
        String savedCom3 = restTemplate.postForObject(baseUrl + "/{postId}/comment?user=3",thirdComment,String.class,1);


        Comment fifthComment = new Comment(4,"last coment",dateCreated,post,user4);
        String savedCom4 = restTemplate.postForObject(baseUrl + "/{postId}/comment?user=4",fifthComment,String.class,1);

        List<Comment> allComments = comTest.findAll();
        Integer numbersOfCommentOnPost = comTest.findAll().size();


        assertAll(
                ()->assertNotNull(allComments),
                ()->assertEquals(4,numbersOfCommentOnPost)
        );

    }
}