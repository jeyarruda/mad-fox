package br.com.madfox.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import br.com.madfox.entity.User;

public interface UserRepository extends JpaRepository<User, Long> {
    
    public List<User> findByNicknameContainsIgnoreCase(String nickname);

    @Query("select u from User u where u.nickname = ?1 ")
    public User buscaPorNome(String nickname);

    public User findByNickname(String nickname);

    @Query("select u from User u where u.nickname = ?1 and u.password = ?2")
    public User buscaUsuarioPorNomeESenha(String nickname, String password);

    public User findByNicknameAndPassword(String nickname, String password); 

    @Query("select u from User u inner join u.authorizations a where a.authname = ?1")
    public List<User> buscaPorNomeAutorizacao(String authorizer);

    public List<User> findByAuthorizationsAuthname(String authorizer); 


}
