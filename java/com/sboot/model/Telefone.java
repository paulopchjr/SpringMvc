package com.sboot.model;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.ManyToOne;
import javax.persistence.SequenceGenerator;

import org.hibernate.annotations.ForeignKey;

@Entity
@SequenceGenerator(name="tel_seq", sequenceName = "tel_seq", allocationSize = 1, initialValue = 1 )
public class Telefone {

	
	@Id
	@GeneratedValue(strategy = GenerationType.SEQUENCE, generator = "tel_seq")
	private Long id;
	private String numero;
	private String tipo;
	
	@ManyToOne
	@ForeignKey(name = "pesssoa_id")
	private Pessoa pessoa;
	
	
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getNumero() {
		return numero;
	}
	public void setNumero(String numero) {
		this.numero = numero;
	}
	public String getTipo() {
		return tipo;
	}
	public void setTipo(String tipo) {
		this.tipo = tipo;
	}
	
	public void setPessoa(Pessoa pessoa) {
		this.pessoa = pessoa;
	}
	public Pessoa getPessoa() {
		return pessoa;
	}
	
	
	
	
	
	
	
}
