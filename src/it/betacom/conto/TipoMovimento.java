package it.betacom.conto;

public enum TipoMovimento {

	APERTURA {
		@Override
	    public String toString() {
	        return "Bonus Apertura";
	    }
	},
	
	PRELIEVO {
		@Override
	    public String toString() {
	        return "Prelievo";
	    }
	},
	
	VERSAMENTO {
		@Override
	    public String toString() {
	        return "Versamento";
	    }
	},
	
	CHIUSURA,
	
	ERRORE;
	
}
