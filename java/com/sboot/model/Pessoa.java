package com.sboot.model;

import java.io.Serializable;
import java.util.Date;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Lob;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.SequenceGenerator;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import org.springframework.format.annotation.DateTimeFormat;

@Entity
@SequenceGenerator(name = "usuario_seq", sequenceName = "usuario_seq", allocationSize = 1, initialValue = 1)
public class Pessoa implements Serializable {

	private static final long serialVersionUID = 1L;
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "usuario_seq")
	private Long id;

	@NotNull(message = "O Nome não pode ser Nulo")
	@NotEmpty(message = "O Nome não pode ser vazio")
	private String nome;
	private String sexo;

	/*
	 * mapeando para o objeto de telefone no atributo usuario, orphanRemoval remove
	 * a entidade e telefones
	 */
	@OneToMany(mappedBy = "pessoa", orphanRemoval = true, cascade = CascadeType.ALL)
	private List<Telefone> telefones;

	@DateTimeFormat(pattern = "yyyy-MM-dd") // padrao da data no banco
	@Temporal(TemporalType.DATE)
	private Date dataNascimento;

	@ManyToOne
	private Profissao profissao;

	@Enumerated(EnumType.STRING)
	private Cargo cargo;

	@Lob
	private byte[] foto;

	
	private String nomeArquivoFoto;
	
	private String tipoArquivoFoto;
	
	
	
	public Long getId() {
		return id;
	}

	public void setId(Long id) {
		this.id = id;
	}

	public String getNome() {
		return nome;
	}

	public void setNome(String nome) {
		this.nome = nome;
	}

	public List<Telefone> getTelefones() {
		return telefones;
	}

	public void setTelefones(List<Telefone> telefones) {
		this.telefones = telefones;
	}

	public String getSexo() {
		return sexo;
	}

	public void setSexo(String sexo) {
		this.sexo = sexo;
	}

	public void setCargo(Cargo cargo) {
		this.cargo = cargo;
	}

	public Cargo getCargo() {
		return cargo;
	}

	public void setDataNascimento(Date dataNascimento) {
		this.dataNascimento = dataNascimento;
	}

	public Date getDataNascimento() {
		return dataNascimento;
	}

	public void setProfissao(Profissao profissao) {
		this.profissao = profissao;
	}

	public Profissao getProfissao() {
		return profissao;
	}

	public void setFoto(byte[] foto) {
		this.foto = foto;
	}

	public byte[] getFoto() {
		return foto;
	}

	public String getNomeArquivoFoto() {
		return nomeArquivoFoto;
	}

	public void setNomeArquivoFoto(String nomeArquivoFoto) {
		this.nomeArquivoFoto = nomeArquivoFoto;
	}

	public String getTipoArquivoFoto() {
		return tipoArquivoFoto;
	}

	public void setTipoArquivoFoto(String tipoArquivoFoto) {
		this.tipoArquivoFoto = tipoArquivoFoto;
	}
	
	
	

}
