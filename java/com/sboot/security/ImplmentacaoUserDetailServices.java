package com.sboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.sboot.model.Usuario;
import com.sboot.repository.UsuarioRepository;

@Service
@Transactional
public class ImplmentacaoUserDetailServices implements UserDetailsService {

	@Autowired
	private UsuarioRepository usuarioRepository;

	@Override
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

		Usuario usuario = usuarioRepository.buscarUsuarioporLogin(username);
		if (usuario == null) {
			throw new UsernameNotFoundException("Usuário não foi encontrado");
		}
		
		System.out.println(usuario.getLogin());
		return new User(usuario.getLogin(), usuario.getPassword(), true, true, true, true, usuario.getAuthorities());
	}

}
