package br.com.madfox.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.madfox.controller.View;

@Entity
@Table(name = "authorizer")
public class Authorizer {

    @JsonView(View.UserComplete.class)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView({View.UserSummary.class, View.AuthorizerSummary.class})
    @Column(name = "authname")
    private String authname;

    @JsonView(View.AuthorizerSummary.class)
    @ManyToMany(fetch = FetchType.LAZY, mappedBy = "authorizations")
    private Set<User> users;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAuthname() {
        return this.authname;
    }

    public void setAuthname(String authname) {
        this.authname = authname;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }

}