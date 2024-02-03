package it.betacom.conto;

public class Movimento {

	TipoMovimento tipoMovimento;
	
	private float importo;
	
	private float saldoFinale;
	
	public Movimento(TipoMovimento tipoMovimento, float importo, float saldoFinale) {
		this.tipoMovimento = tipoMovimento;
		this.importo = importo;
		this.saldoFinale = saldoFinale;
	}

	public float getImporto() {
		return importo;
	}

	public float getSaldoFinale() {
		return saldoFinale;
	}
	
}
