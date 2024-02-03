package it.betacom.conto;

import java.time.LocalDate;
import java.time.Period;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.Scanner;

import it.betacom.model.ContoCorrente;
import it.betacom.model.Correntista;

public class GestioneConto {
	
	public static ContoCorrente aperturaConto(Scanner scanner) {
		//inserimento nome
		System.out.println("Inserisci nome: ");
		String nome = scanner.nextLine();
		
		//inserimento data di nascita, se minorenne ritorna null
		LocalDate dataDiNascita = null;
		boolean isValida = false;
		while(!isValida) {
			System.out.println("\nInserisci data di nascita nel formato giornoMeseAnno (Es. 01011970):");
			try {
				dataDiNascita = LocalDate.parse(scanner.nextLine(), DateTimeFormatter.ofPattern("ddMMyyyy"));
				if(Period.between(dataDiNascita, LocalDate.now()).getYears() < 18) {
					System.out.println("Non è possibile aprire il conto! Età inferiore a quella minima richiesta.");
					return null;
				}
				else isValida = true;
			}
			catch(DateTimeParseException e) {
				System.out.println("Data inserita non valida!");
			}
		}
		
		//calcolo bonus e istanziamento ContoCorrente
		float importo = calcolaBonus(dataDiNascita);
		Correntista correntista = new Correntista(nome, dataDiNascita);
		correntista.aggiungiMovimento(new Movimento(TipoMovimento.APERTURA, importo, importo));
		return new ContoCorrente(correntista, importo);
	}
	
	private static float calcolaBonus(LocalDate dataDiNascita) {
		int età = Period.between(dataDiNascita, LocalDate.now()).getYears();
		
		if(età>=18 && età<=30) return 100;
		else if(età >= 31 && età <= 50) return 150;
		else return 200;
	}

	public static TipoMovimento scegliOperazione(Scanner scanner, ContoCorrente contoCorrente) {
		System.out.println("\nChe operazione vuoi fare? "
				+ "(p -> prelievo) "
				+ "(v -> versamento)"
				+ "(c -> chiusura)");
		String operazione = scanner.nextLine();
		
		//tenta prelievo, se viene effettuato ritorna TipoMovimento.PRELIEVO
		if(operazione.equals("p")) {
			if(tentaPrelievo(scanner, contoCorrente)) return TipoMovimento.PRELIEVO;
			else return TipoMovimento.ERRORE;
		}
		
		//effettua versamento e ritorna TipoMovimento.VERSAMENTO
		else if(operazione.equals("v")) {
			effettuaVersamento(scanner, contoCorrente);
			return TipoMovimento.VERSAMENTO;
		}
		
		//effettua chiusura e ritorna TipoMovimento.CHIUSURA
		else if(operazione.equals("c")) {
			effettuaChiusura(contoCorrente);
			return TipoMovimento.CHIUSURA;
		}
		
		//opzione non valida
		else {
			System.out.println("\nOpzione non valida!");
			return TipoMovimento.ERRORE;
		}
		
	}
	
	private static boolean tentaPrelievo(Scanner scanner, ContoCorrente contoCorrente) {
		try {
			float importo = inserisciCifra(scanner);
			contoCorrente.prelievo(importo);
			//se va a buon fine
			contoCorrente.getCorrentista().aggiungiMovimento(new Movimento(TipoMovimento.PRELIEVO, importo, contoCorrente.getSaldo()));
			System.out.println("\nOperazione effettuata! Saldo corrente: " + contoCorrente.getSaldo());
			return true;
		} catch(Exception e) {
			System.out.println(e.getMessage());
			return false;
		}
	}
	
	private static void effettuaVersamento(Scanner scanner, ContoCorrente contoCorrente) {
		float importo = inserisciCifra(scanner);
		contoCorrente.versamento(importo);
		contoCorrente.getCorrentista().aggiungiMovimento(new Movimento(TipoMovimento.VERSAMENTO, importo, contoCorrente.getSaldo()));
		System.out.println("\nOperazione effettuata! " + "Saldo corrente: " + contoCorrente.getSaldo());
	}
	
	private static void effettuaChiusura(ContoCorrente contoCorrente) {
		EstrattoConto.creaEstrattoContoInPdf(contoCorrente, LocalDate.now());
		EstrattoConto.creaEstrattoContoInCsv(contoCorrente, LocalDate.now());
		System.out.println("\nConto chiuso! Saldo finale: " + contoCorrente.getSaldo() + "\nIl tuo estratto conto è stato salvato nei formati pdf e csv!");
	}
	
	private static float inserisciCifra(Scanner scanner) {
		float cifra = 0;
		boolean isValida = false;
		while(!isValida) {
			try{
				System.out.println("\nInserisci cifra: ");
				cifra = Float.parseFloat(scanner.nextLine());
				isValida=true;
			} catch(NumberFormatException e) {
				System.out.println("\nCifra non valida!");
			}
		}
		return cifra;
	}
	
}
