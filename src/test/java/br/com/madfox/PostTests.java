package br.com.madfox;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.util.Date;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
// import org.springframework.test.annotation.Rollback;

import br.com.madfox.entity.Post;
import br.com.madfox.entity.User;
import br.com.madfox.repository.PostRepository;
import br.com.madfox.repository.UserRepository;
import br.com.madfox.service.PostService;



@SpringBootTest
public class PostTests {
    @Autowired
	private PostService postService; 
    
	@Autowired
	private PostRepository postRepository; 

    @Autowired
	private UserRepository userRepository; 


    @Test 
	void testCreatePost(){
		User user = userRepository.findByNickname("joaoarruda");


		Post post = new Post(); 
		post.setCategory("bla");
		post.setContent("conteudo atualizado");
		post.setTimePost(new Date());
		post.setUser(user);
		
		postRepository.save(post);
		assertNotNull(post);
	}


    @Test 
	void testCreatePostByService(){
		Post post = postService.registerPost("conteudo service", "bla", "juarez");
		
		assertNotNull(post);
	}

    @Test 
	void testSelectPost(){
		Optional <Post> postOp  = postRepository.findById(7L); 
		assertNotNull(postOp.get());
	}

	@Test 
	@Transactional
	void testSelectPostByUser(){
		List<Post> posts  = postRepository.findPostsByUser("joaoarruda"); 
		assertFalse(posts.isEmpty());
	}



    @Test 
	void testSelectPostByUserervice(){
		List<Post> posts  = postService.findPostsByUser("joaoarruda"); 
		assertFalse(posts.isEmpty());
	}


	@Test
	void testGetStarted(){
		User user = postService.getStarted("username", "nickname", "password", "authorization", "content", "category");
		assertNotNull(user);
	}

    
}
