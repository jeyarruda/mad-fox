package br.com.madfox.controller;

import com.fasterxml.jackson.annotation.JsonView;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.madfox.entity.Authorizer;
import br.com.madfox.service.SecurityService;

@RestController
@RequestMapping(value = "/authorizer")
@CrossOrigin
public class AuthorizerController {

    @Autowired
    private SecurityService securityService; 




    @JsonView(View.AuthorizerSummary.class)
    @GetMapping(value = "/{authorizer}")
    public Authorizer findAuthorizerByAuthname(@PathVariable("authorizer") String authname) {

        return securityService.findAuthByAuthname(authname);
    }
}
