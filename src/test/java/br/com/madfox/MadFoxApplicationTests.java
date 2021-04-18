package br.com.madfox;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import br.com.madfox.entity.Authorizer;
import br.com.madfox.entity.User;
import br.com.madfox.repository.AuthorizerRepository;
import br.com.madfox.repository.UserRepository;
import br.com.madfox.service.SecurityService;

@SpringBootTest
@Transactional
@Rollback
class MadFoxApplicationTests {

	@Autowired
	private UserRepository userRepository; 

	@Autowired
	private AuthorizerRepository authRepository; 

	@Autowired
	private SecurityService secService; 

	@Test
	void contextLoads() {
	}

	@Test
	void testInsert(){
		User user = new User(); 
		user.setNickname("joaoarruda3");
		user.setUsername("João Arruda");
		user.setPassword("1234");
		userRepository.save(user);
		assertNotNull(user.getId());
	}

	@Test
	void testInsert2(){
		User user = new User(); 
		user.setNickname("joaoarruda5");
		user.setUsername("João Arruda");
		user.setPassword("1234");
		user.setAuthorizations(new HashSet<Authorizer>());
		Authorizer auth = new Authorizer(); 
		auth.setAuthname("ROLE_USER_2");
		authRepository.save(auth); 
		user.getAuthorizations().add(auth);
		userRepository.save(user);
		assertNotNull(user.getAuthorizations().iterator().next().getId());
	}

	@Test
	void testAuth(){
		User user = userRepository.findById(1L).get(); 
		assertEquals("ROLE_BLA", user.getAuthorizations().iterator().next().getAuthname());
	}


	@Test
	void testUser(){
		Authorizer auth = authRepository.findById(1L).get();
		assertEquals("joaoarruda", auth.getUsers().iterator().next().getNickname());
	}


	@Test
	void testFindByNameContains(){
		List<User> users = userRepository.findByNicknameContainsIgnoreCase("joao");
		assertFalse(users.isEmpty());
	}

	@Test
	void testFindByName(){
		User user = userRepository.findByNickname("joaoarruda");
		assertNotNull(user);
	}


	@Test
	void testFindByNameQuery(){
		User user = userRepository.buscaPorNome("joaoarruda");
		assertNotNull(user);
	}


	@Test
	void testLogon(){
		User user = userRepository.findByNicknameAndPassword("joaoarruda", "1234");
		assertNotNull(user);
	}

	@Test
	void testLogonQuery(){
		User user = userRepository.buscaUsuarioPorNomeESenha("joaoarruda", "1234");
		assertNotNull(user);
	}


	@Test
	void testFindUsersByAuthorizationName(){
		List<User> users = userRepository.findByAuthorizationsAuthname("ROLE_ADMIN");
		assertFalse(users.isEmpty());
	}


	@Test
	void testFindUsersByAuthorizationNameQuery(){
		List<User> users = userRepository.buscaPorNomeAutorizacao("ROLE_ADMIN");
		assertFalse(users.isEmpty());
	}

	@Test
	void testServiceRegisterUser(){
		User user = secService.registerUser("AmarildoArruda", "juarez", "passw0rd", "ROLE_USER");
		assertNotNull(user);
	}	


}
