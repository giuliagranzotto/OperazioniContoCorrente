package it.betacom.conto;

import java.io.FileWriter;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;

import org.apache.commons.lang3.StringUtils;

import com.itextpdf.kernel.font.PdfFont;
import com.itextpdf.kernel.font.PdfFontFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.element.Text;
import com.opencsv.CSVWriter;

import it.betacom.model.ContoCorrente;

public class EstrattoConto {
	
	public static void creaEstrattoContoInPdf(ContoCorrente contoCorrente, LocalDate dataChiusura){	
		String DEST = "EC_" + contoCorrente.getCorrentista().getNome() + "_" + dataChiusura.getYear() + dataChiusura.getMonthValue() + dataChiusura.getDayOfMonth() + ".pdf";
		
		try {
			//Inizializzazione documento PDF
			PdfDocument pdf = new PdfDocument(new PdfWriter(DEST));
			
			//Inizializzazione PDF con font specifico
			Document document = new Document(pdf);
			PdfFont courier_new = PdfFontFactory.createFont("C:/Windows/Fonts/cour.ttf");
			document.setFont(courier_new);
			
			//Definizione contenuti
			Paragraph intestazione = new Paragraph();
			intestazione.add(separatoreRighe(70));
			intestazione.add("\nEstratto Conto Finale | Correntista: " + contoCorrente.getCorrentista().getNome() + " | Data: " + dataChiusura.format(DateTimeFormatter.ofPattern("dd-MM-yyyy")) + "\n");
			intestazione.add(separatoreRighe(70));
			
			Paragraph movimenti = new Paragraph();
			Text intestazioneMovimenti = new Text(intestazioneMovimenti(20));
			intestazioneMovimenti.setBold();
			movimenti.add(intestazioneMovimenti);
			movimenti.add(movimentiCorrentista(contoCorrente.getCorrentista().getMovimenti(), 20));
			
			Paragraph fine = new Paragraph();
			fine.add("Saldo finale: " + contoCorrente.getSaldo() + " Euro\n");
			fine.add(separatoreRighe(70));
			
			//Aggiunta contenuti al documento
			document.add(intestazione);
			document.add(movimenti);
			document.add(fine);
			
			//Chiusura documento
			document.close();
			
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	private static String separatoreRighe(int lunghezza) {
		return StringUtils.rightPad("", lunghezza, "-");
	}
	
	private static String intestazioneMovimenti(int lunghezzaCampo) {
		String input = "";
		StringBuilder intestazione = new StringBuilder("");
		input = "Tipo Operazione";
		intestazione.append(StringUtils.rightPad(input, lunghezzaCampo));
		intestazione.append("| ");
		
		input = "Importo";
		intestazione.append(StringUtils.rightPad(input, lunghezzaCampo));
		intestazione.append("| ");
		
		input = "Saldo Dopo Operazione";
		intestazione.append(StringUtils.rightPad(input, lunghezzaCampo));
		intestazione.append("\n");
		
		return new String(intestazione);
	}
	
	private static String movimentiCorrentista(List<Movimento> movimenti, int lunghezzaCampo) {
		String input = "";
		StringBuilder listaMovimenti = new StringBuilder("");
		for(Movimento movimento : movimenti) {
			input = movimento.tipoMovimento.toString();
			listaMovimenti.append(StringUtils.rightPad(input, lunghezzaCampo));
			listaMovimenti.append("| ");
			
			input = String.valueOf(movimento.getImporto());
			listaMovimenti.append(StringUtils.rightPad(input, lunghezzaCampo));
			listaMovimenti.append("| ");
			
			input = String.valueOf(movimento.getSaldoFinale());
			listaMovimenti.append(StringUtils.rightPad(input, lunghezzaCampo));
			listaMovimenti.append("\n");
		}
		System.out.print("\nLista movimenti effettuati:\n" + listaMovimenti);
		return new String(listaMovimenti);
	}
	
	public static void creaEstrattoContoInCsv(ContoCorrente contoCorrente, LocalDate dataChiusura) {
		String DEST = "EC_" + contoCorrente.getCorrentista().getNome() + "_" + dataChiusura.getYear() + dataChiusura.getMonthValue() + dataChiusura.getDayOfMonth() + ".csv";

		try {
//			CSVWriter writer = new CSVWriter(new FileWriter(DEST), ',', CSVWriter.DEFAULT_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
//			CSVWriter writer = new CSVWriter(new FileWriter(DEST), char separator, char quotechar, char escapechar, String lineEnd);
			CSVWriter writer = new CSVWriter(new FileWriter(DEST), ',', CSVWriter.NO_QUOTE_CHARACTER, CSVWriter.DEFAULT_ESCAPE_CHARACTER, CSVWriter.DEFAULT_LINE_END);
			writer.writeNext(new String[] {"Estratto Conto Finale,Correntista: " + contoCorrente.getCorrentista().getNome() + ",Data: " + dataChiusura.format(DateTimeFormatter.ofPattern("dd-MM-yyyy"))}, true);
			writer.writeNext(new String[] {"Tipo operazione,Importo,Saldo dopo operazione"}, true);
			for(Movimento movimento : contoCorrente.getCorrentista().getMovimenti()) {
				writer.writeNext(new String[] {movimento.tipoMovimento.toString() + "," + String.valueOf(movimento.getImporto()) + "," + String.valueOf(movimento.getSaldoFinale())});
			}
			writer.writeNext(new String[] {""});
			writer.writeNext(new String[] {"Saldo finale: " + contoCorrente.getSaldo() + " Euro"});
			writer.close();
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
}
