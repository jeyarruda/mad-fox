package br.com.madfox.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import br.com.madfox.entity.Post;


public interface PostRepository extends JpaRepository<Post, Long> {
    
    public Optional<Post> findById(Long id); 

    @Query("select p from Post p join p.user u where u.nickname = ?1")
    public List<Post> findPostsByUser(String nickname);

    @Query("select p from Post p join p.user u where u.nickname = ?1 and p.category = ?2")
    public List<Post> findPostsByUserAndCategory(String nickname, String category); 


    @Modifying
    @Query("delete from Post p where p.id = ?1")
    public void deletePost(Long id);


    // @Query("select p from Post p WHERE p.time_post < ?1")
    // public List<Post> findPostsBeforeDate(Date dateTime); 


    
}
