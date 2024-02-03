package it.betacom.main;

import java.util.Scanner;

import org.apache.commons.lang3.StringUtils;

import it.betacom.conto.GestioneConto;
import it.betacom.conto.TipoMovimento;
import it.betacom.model.ContoCorrente;

public class Main {

	public static void main(String[] args) {
		
		Scanner scanner = new Scanner(System.in);
		
		ContoCorrente contoCorrente = GestioneConto.aperturaConto(scanner);
		if(contoCorrente != null) {
			System.out.println("\nIl conto è aperto e il tuo saldo è " + contoCorrente.getSaldo());
			
			TipoMovimento ultimoMovimento = TipoMovimento.APERTURA;
			while(!ultimoMovimento.equals(TipoMovimento.CHIUSURA)) {
				System.out.println(StringUtils.rightPad("", 60, "-"));
				System.out.println("Vuoi fare un'operazione sul conto? "
						+ "(n -> no) "
						+ "(s -> sì)");
				switch(scanner.nextLine()) {
					case "s" :
						ultimoMovimento = GestioneConto.scegliOperazione(scanner, contoCorrente);
						break;
					case "n" :
						System.out.println("Il tuo saldo è: " + contoCorrente.getSaldo());
						break;
					default :
						System.out.println("Opzione non valida!");
				}
				System.out.println(StringUtils.rightPad("", 60, "-"));
			}
		}
		
		scanner.close();
	}
}
