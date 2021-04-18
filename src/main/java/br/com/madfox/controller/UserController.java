package br.com.madfox.controller;

import java.util.List;

import javax.websocket.server.PathParam;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.util.UriComponentsBuilder;

import br.com.madfox.entity.User;
import br.com.madfox.service.SecurityService;

@RestController
@RequestMapping(value = "/user")
@CrossOrigin
public class UserController {

    @Autowired
    private SecurityService securityService;

    @JsonView(View.UserSummary.class)
    @GetMapping(value = "/all")
    public List<User> findAll() {
        return securityService.listAllUsers();
    }

    @JsonView(View.UserComplete.class)
    @GetMapping(value = "/{id}")
    public User findById(@PathVariable("id") Long id) {
        return securityService.findUserById(id);
    }

    @JsonView(View.UserSummary.class)
    @GetMapping
    public User findByNickname(@PathParam("nickname") String nickname) {
        return securityService.findUserByNickname(nickname);
    }
    
    @JsonView(View.UserComplete.class)
    @PostMapping
    public ResponseEntity<User> registerUser(@RequestBody User user, UriComponentsBuilder urlComponentBuilder) {
        user = securityService.registerUser(user.getUsername(), user.getNickname(), user.getPassword(), "ROLE_USER");
        HttpHeaders responseHeaders = new HttpHeaders();
        responseHeaders.setLocation(urlComponentBuilder.path("/user/" + user.getId()).build().toUri());
        return new ResponseEntity<User>(user, responseHeaders, HttpStatus.CREATED);
    }
}
