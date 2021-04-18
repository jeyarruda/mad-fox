package br.com.madfox.service;

import java.util.List;

import br.com.madfox.entity.Authorizer;
import br.com.madfox.entity.User;

public interface SecurityService {

    public List<User> listAllUsers();

    public User findUserById (Long id);

    public User findUserByNickname (String nickname);

    public User registerUser(String username, String nickname, String password, String authorization);

    public Authorizer findAuthByAuthname(String authname);
}
