package com.sboot.security;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.authentication.builders.AuthenticationManagerBuilder;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;

@Configuration
@EnableWebSecurity
public class WebConfigSecurity extends WebSecurityConfigurerAdapter {

	@Autowired
	private ImplmentacaoUserDetailServices implmentacaoUserDetailServices;

	/* config solicitações do http */
	@Override
	protected void configure(HttpSecurity http) throws Exception {
		http.csrf().disable() // Desativa as configurações padrão de memória.
				.authorizeRequests() // Pertimi restringir acessos
				.antMatchers(HttpMethod.GET, "/").permitAll() // Qualquer usuário acessa a pagina inicial
				.antMatchers(HttpMethod.GET, "/cadpessoa").hasAnyRole("ADMIN")
				.anyRequest().authenticated()
				.and()
				.formLogin().permitAll()// permite qualquer usuário
				.loginPage("/login").defaultSuccessUrl("/cadpessoa")
				.defaultSuccessUrl("/cadpessoa")
				.and()
				.logout()
				.logoutSuccessUrl("/login")
				// Mapeia URL de Logout e invalida usuário autenticado
				.logoutRequestMatcher(new AntPathRequestMatcher("/logout"));

	}

	@Override // cria autenticação do usuario com o banco de dados ou em memoria
	protected void configure(AuthenticationManagerBuilder auth) throws Exception {

		auth.userDetailsService(implmentacaoUserDetailServices).passwordEncoder(new BCryptPasswordEncoder());

	}



}
