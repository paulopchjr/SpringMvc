package com.sboot.model;

public enum Cargo {

	TRAINNE("Trainne"),
	ASSISTENTE("Assistente"),
	JUNIOR("Júnior"),
	PLENO("Plênio"),
	SENIOR("Sênior");
	
	
	private String nome;
	
	private String valor;
	
	
	private Cargo(String nome) {
		this.nome = nome;
	}
	
	
	public void setNome(String nome) {
		this.nome = nome;
	}
	
	public String getNome() {
		return nome;
	}
	
	
	public void setValor(String valor) {
		this.valor = valor;
	}
	
	
	public String getValor() {
		return valor = this.name();
	}
	
	
}
