package br.com.madfox.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.madfox.entity.Authorizer;
import br.com.madfox.entity.Post;
import br.com.madfox.entity.User;
import br.com.madfox.exception.NotFoundException;
import br.com.madfox.repository.AuthorizerRepository;
import br.com.madfox.repository.PostRepository;
import br.com.madfox.repository.UserRepository;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import javax.transaction.Transactional;

@Service("postService")
public class PostServiceImpl implements PostService {

    @Autowired
    private UserRepository userRepo; 

    @Autowired
    private PostRepository postRepo;

    @Autowired
    private AuthorizerRepository authRepo;

    

    @Transactional
    @Override
    public Post registerPost(String content, String category, String nickname) {
        User user = userRepo.findByNickname(nickname);
        if(user == null){
            throw new NotFoundException("Usuário não encontrado");   
        }
        Post post  = new Post(); 
        post.setUser(user);
        post.setTimePost(new java.util.Date());
        post.setCategory(category);
        post.setContent(content);
        postRepo.save(post);
        return post;
    }

    @Transactional
    @Override
    public User getStarted(String username, String nickname, String password, String authorization, String content, String category){
        Authorizer auth = authRepo.findByAuthname(authorization);
        if (auth == null) {
            auth = new Authorizer();
            auth.setAuthname(authorization);
            authRepo.save(auth);
        }
        
        User user = new User();
        Post post = new Post(); 
        user.setNickname(nickname);
        user.setUsername(username);
        user.setPassword(password);
        user.setAuthorizations(new HashSet<Authorizer>());
        user.getAuthorizations().add(auth);
        userRepo.save(user);

        post.setCategory(category);
        post.setContent(content);
        post.setTimePost(new java.util.Date());
        post.setUser(user);
        postRepo.save(post);
        return user;
    }

    @Override
    public List<Post> findPostsByUser(String nickname) {
        User user = userRepo.findByNickname(nickname);
        if(user == null){
            throw new NotFoundException("Usuário não encontrado");   
        }
        List<Post> posts  = postRepo.findPostsByUser(nickname); 
        return posts;
    }

    @Override
    public Post findPostById(Long id) {
        Optional<Post> post = postRepo.findById(id);
        if(post.isPresent()){
            return post.get();
        }
        throw new NotFoundException("Post não encontrado");
    }

    @Override
    public Post editPost(Post post, Post oldPost) {
        if(post.getCategory() != null){
            oldPost.setCategory(post.getCategory());
        }
        if(post.getContent() != null){
            oldPost.setContent(post.getContent());
        }
        postRepo.save(oldPost);
        return oldPost;
    }

    @Transactional
    @Override
    public String deletePost(Long id) {
        Optional<Post> postOp = postRepo.findById(id);
        if(postOp.isPresent()){
            postRepo.deletePost(id);
        }
        return "OK";
    }

    // @Override
    // public List<Post> findPostsBeforeDate(Date timePost) {
    //     List<Post> posts = postRepo.findPostsBeforeDate(timePost);
    //     return posts;
    // }



}
