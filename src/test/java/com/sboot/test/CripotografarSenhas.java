package com.sboot.test;

import org.junit.jupiter.api.Test;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class CripotografarSenhas {
	
	@Test
	public void criptografiaSenha() {
		
		BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();
		String resultado = encoder.encode("321");
		System.out.println(resultado);
	}

}
