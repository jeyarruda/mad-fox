package br.com.madfox.service;

import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import br.com.madfox.entity.Authorizer;
import br.com.madfox.entity.User;
import br.com.madfox.exception.NotFoundException;
import br.com.madfox.repository.AuthorizerRepository;
import br.com.madfox.repository.UserRepository;


@Service("securityService")
public class SecurityServiceImpl implements SecurityService {

    @Autowired
    private AuthorizerRepository authRepo;

    @Autowired
    private UserRepository userRepo;

    @Transactional
    @Override
    public User registerUser(String username, String nickname, String password, String authorization) {
        Authorizer auth = authRepo.findByAuthname(authorization);
        if (auth == null) {
            auth = new Authorizer();
            auth.setAuthname(authorization);
            authRepo.save(auth);
        }
        User user = new User();
        user.setNickname(nickname);
        user.setUsername(username);
        user.setPassword(password);
        user.setAuthorizations(new HashSet<Authorizer>());
        user.getAuthorizations().add(auth);
        userRepo.save(user);
        return user;
    }

    @Override
    public List<User> listAllUsers() {
        return userRepo.findAll();
    }

    @Override
    public User findUserById(Long id) {
        Optional<User> userOp = userRepo.findById(id);
        if(userOp.isPresent()){
            return userOp.get();
        }
        throw new NotFoundException("Usuário não encontrado");
    }

    @Override
    public User findUserByNickname(String nickname) {
        User user = userRepo.findByNickname(nickname);
        if(user != null){
            return user;
        }
        throw new NotFoundException("Usuário não encontrado");
    }

    @Override
    public Authorizer findAuthByAuthname(String authname) {
        Authorizer authorizer = authRepo.findByAuthname(authname);
        if(authorizer != null){
            return authorizer;
        }
        throw new NotFoundException("Autorização não encontrada"); 
    }

}
