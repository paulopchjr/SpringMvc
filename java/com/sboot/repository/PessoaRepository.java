package com.sboot.repository;

import java.util.List;

import org.springframework.data.domain.Example;
import org.springframework.data.domain.ExampleMatcher;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.sboot.model.Pessoa;

@Repository
@Transactional
public interface PessoaRepository extends JpaRepository<Pessoa, Long> {

	@Query("SELECT p from Pessoa p where p.nome like %?1%")
	List<Pessoa> findPessoaByName(String nome);

	@Query("SELECT p from Pessoa p where p.nome like %?1% and p.sexo=?2")
	List<Pessoa> findPessoaByNameSexo(String nome, String sexo);

	@Query("SELECT p from Pessoa p where p.sexo=?1")
	List<Pessoa> findPessoaBySexo(String sexo);

	
	default Page<Pessoa> buscaPessoaporPaginacao(String nome, Pageable pageable) {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);

		/*
		 * Confingurando a pesquisa para consultar por parte do nome do banco de dados,
		 * igual a like com sql
		 */
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny().withMatcher("nome",
				ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());

		/* Une o objeto com o valor e a configuação para consultar */
		Example<Pessoa> example = Example.of(pessoa, exampleMatcher);

		Page<Pessoa> pessoas = findAll(example, pageable);

		return pessoas;
	}
	
	
	default Page<Pessoa> buscaPessoaporPaginacaoSexo(String nome, String sexo , Pageable pageable) {

		Pessoa pessoa = new Pessoa();
		pessoa.setNome(nome);
		pessoa.setSexo(sexo);

		/*
		 * Confingurando a pesquisa para consultar por parte do nome do banco de dados,
		 * igual a like com sql
		 */
		ExampleMatcher exampleMatcher = ExampleMatcher.matchingAny()
				.withMatcher("nome", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase())
				.withMatcher("sexo", ExampleMatcher.GenericPropertyMatchers.contains().ignoreCase());
				

		/* Une o objeto com o valor e a configuação para consultar */
		Example<Pessoa> example = Example.of(pessoa, exampleMatcher);

		Page<Pessoa> pessoas = findAll(example, pageable);

		return pessoas;
	}

}
