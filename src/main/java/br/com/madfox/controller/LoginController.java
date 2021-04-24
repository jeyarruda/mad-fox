package br.com.madfox.controller;

import com.fasterxml.jackson.core.JsonProcessingException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.madfox.security.JwtUtils;
import br.com.madfox.security.Login;


@RestController
@RequestMapping(value = "/login")
@CrossOrigin
public class LoginController {

    @Autowired
    private AuthenticationManager authManager;
    
    @PostMapping
    public Login authenticate(@RequestBody Login login) throws JsonProcessingException{
        Authentication authentication = new UsernamePasswordAuthenticationToken(login.getUsername(), login.getPassword());
        authentication = authManager.authenticate(authentication);
        login.setPassword(null);
        login.setToken(JwtUtils.generateToken(authentication));
        return login;
    }
}
