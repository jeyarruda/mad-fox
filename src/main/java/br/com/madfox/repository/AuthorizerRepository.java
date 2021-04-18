package br.com.madfox.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import br.com.madfox.entity.Authorizer;

public interface AuthorizerRepository extends JpaRepository<Authorizer, Long> {
    public Authorizer findByAuthname(String authname);
}
