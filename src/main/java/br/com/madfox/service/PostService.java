package br.com.madfox.service;

import java.util.List;

import br.com.madfox.entity.Post;
import br.com.madfox.entity.User;

public interface PostService {
    public Post registerPost(String content, String category, String nickname);

    public List<Post> findPostsByUser(String nickname); 

    public Post findPostById(Long id); 

    public Post editPost(Post post, Post oldPost);

    public User getStarted(String username, String nickname, String password, String authorization, String content, String category);

    public String deletePost(Long id);
}
