package com.sboot.repository;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sboot.model.Usuario;

@Repository
@Transactional
public interface UsuarioRepository extends CrudRepository<Usuario, Long>  {
	
	
	@Query("SELECT u FROM Usuario u where u.login = ?1")
	Usuario buscarUsuarioporLogin(String login);

}
