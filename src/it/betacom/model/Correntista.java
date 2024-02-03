package it.betacom.model;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import it.betacom.conto.Movimento;

public class Correntista {
	
	private String nome;
	
	private LocalDate dataDiNascita;
	
	private List<Movimento> movimenti;

	public Correntista(String nome, LocalDate dataDiNascita) {
		this.nome = nome;
		this.dataDiNascita = dataDiNascita;
		this.movimenti = new ArrayList<Movimento>();
	}

	public String getNome() {
		return nome;
	}

	public LocalDate getDataDiNascita() {
		return dataDiNascita;
	}
	
	public void aggiungiMovimento(Movimento movimento) {
		this.movimenti.add(movimento);
	}

	public List<Movimento> getMovimenti() {
		return movimenti;
	}
	
}
