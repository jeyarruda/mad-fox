package br.com.madfox.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import br.com.madfox.entity.Post;
import br.com.madfox.service.PostService;

@RestController
@RequestMapping(value = "/post")
@CrossOrigin
public class PostController {

    @Autowired
    private PostService postService;

    @JsonView(View.PostSummary.class)
    @PostMapping
    public ResponseEntity<Post> writeAPost(@RequestBody Post post, UriComponentsBuilder urlComponentBuilder) {
        post = postService.registerPost(post.getContent(), post.getCategory(), post.getUser().getNickname());
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(urlComponentBuilder.path("/post/" + post.getId()).build().toUri());
        return new ResponseEntity<Post>(post, responseHeaders, HttpStatus.CREATED);
    }

    @JsonView(View.PostSummary.class)
    @GetMapping
    public List<Post> getPostsByUser(@PathParam("nickname") String nickname) {
        return postService.findPostsByUser(nickname);
    }

    @JsonView(View.PostComplete.class)
    @GetMapping(value="/all")
    public List<Post> getPosts() {
        return postService.getPosts();
    }

    @JsonView(View.PostComplete.class)
    @PutMapping(value = "/{id}")
    public ResponseEntity<Post> editPostById(@RequestBody Post post, @PathVariable("id") Long id) {
        Post oldPost = postService.findPostById(id);
        if (oldPost != null) {
            Post newPost = postService.editPost(post, oldPost);
            HttpHeaders responseHeaders = new HttpHeaders();
            return new ResponseEntity<Post>(newPost, responseHeaders, HttpStatus.OK);
        }
        return new ResponseEntity<Post>(null, null, HttpStatus.BAD_REQUEST);
    }

    @JsonView(View.PostSummary.class)
    @DeleteMapping(value = "/{id}")
    public ResponseEntity<Post> deletePost(@PathVariable("id") Long id) {
        postService.deletePost(id);
        return new ResponseEntity<Post>(null, null, HttpStatus.NO_CONTENT);
    }
}
