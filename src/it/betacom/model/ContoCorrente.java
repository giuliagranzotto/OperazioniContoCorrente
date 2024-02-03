package it.betacom.model;

import java.time.LocalDate;
import java.time.Period;

public class ContoCorrente {
	
	private Correntista correntista;
	
	private float saldo;
	
	public ContoCorrente(Correntista correntista, float saldo) {
		this.correntista = correntista;
		this.saldo = saldo;
	}

	public Correntista getCorrentista() {
		return correntista;
	}

	public void setCorrentista(Correntista correntista) {
		this.correntista = correntista;
	}

	public float getSaldo() {
		return saldo;
	}

	public void setSaldo(float saldo) {
		this.saldo = saldo;
	}
	
	public void prelievo(float quantità) throws Exception {
		if((this.saldo - quantità) < 0)
			throw new Exception("\nOperazione errata! Cifra inserita superiore al saldo!");
		else this.saldo -= quantità;
	}
	
	public void versamento(float quantità) {
		this.saldo += quantità;
	}
	
	public int calcolaBonus(LocalDate dataDiNascita) {
		int età = Period.between(dataDiNascita, LocalDate.now()).getYears();
		
		if(età>=18 && età<=30) return 100;
		else if(età >= 31 && età <= 50) return 150;
		else return 200;
	}
	
}
