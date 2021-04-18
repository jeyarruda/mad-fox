package br.com.madfox.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;

import com.fasterxml.jackson.annotation.JsonView;

import br.com.madfox.controller.View;


@Entity
@Table(name = "post")
public class Post {
    
    @JsonView(View.PostComplete.class)
    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonView(View.PostSummary.class)
    @Column(name = "content")
    private String content;

    @JsonView(View.PostSummary.class)
    @Column(name = "category")
    private String category;

    @JsonView(View.PostSummary.class)
    @ManyToOne()
    @JoinColumn(name = "user_id")
    private User user;

    @JsonView(View.PostSummary.class)
    @Column(name = "time_post")
    private Date timePost;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public Date getTimePost() {
        return timePost;
    }

    public void setTimePost(Date timePost) {
        this.timePost = timePost;
    } 



    
    
    
}
