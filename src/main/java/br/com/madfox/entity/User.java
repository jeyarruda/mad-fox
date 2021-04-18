package br.com.madfox.entity;

import java.util.Set;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.JoinTable;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.madfox.controller.View;

@Entity
@Table(name = "user")
public class User {


    @JsonView(View.UserComplete.class)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView({View.UserSummary.class, View.AuthorizerSummary.class, View.PostSummary.class})
    @Column(name = "username")
    private String username;

    @JsonView(View.UserSummary.class)
    @Column(name = "nickname")
    private String nickname;

    @Column(name = "user_password")
    private String password;

    @OneToMany(fetch = FetchType.LAZY, mappedBy = "user")
    private Set<Post> post;

    @JsonView(View.UserSummary.class)
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "user_authorizer", 
            joinColumns = { @JoinColumn(name = "user_id") }, 
            inverseJoinColumns = { @JoinColumn(name = "authorizer_id") })
    private Set<Authorizer> authorizations;

    public Long getId() {
        return this.id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getUsername() {
        return this.username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return this.nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Authorizer> getAuthorizations() {
        return authorizations;
    }

    public void setAuthorizations(Set<Authorizer> authorizations) {
        this.authorizations = authorizations;
    }

}