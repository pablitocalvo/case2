import java.io.IOException;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;
public class DumpOnZone 
{

	/**
	 * @param args
	 * @throws IOException 
	 */
	public static void main(String[] args) throws IOException 
	{
		// TODO
		
		Document doc;
		String indirizzo;
		
		indirizzo=
				"http://www.immobiliare.it/ricerca.php?idCategoria=1&idContratto=1&idTipologia=&sottotipologia=&idTipologiaStanza=&idFasciaPrezzo=&idNazione=IT&idRegione=&idProvincia=&idComune=&idLocalita=Array&idAreaGeografica=&prezzoMinimo=&prezzoMassimo=&balcone=&balconeOterrazzo=&boxOpostoauto=&stato=&terrazzo=&bagni=&mappa=&foto=&boxAuto=&riscaldamenti=&giardino=&superficie=&superficieMinima=&superficieMassima=160&raggio=&locali=&localiMinimo=&localiMassimo=&criterio=rilevanza&ordine=desc&map=0&tipoProprieta=&arredato=&inAsta=&noAste=&aReddito=&fumatore=&animali=&franchising=&flagNc=&gayfriendly=&internet=&sessoInquilini=&vacanze=&categoriaStanza=&fkTipologiaStanza=&ascensore=&classeEnergetica=&verticaleAste=&vrt=41.882214787029575,12.49917984008789;41.888924203771914,12.520294189453125;41.881831370505594,12.53364086151123;41.87122260100435,12.539606094360352;41.86614127149594,12.535099983215332;41.86284937706691,12.52664566040039;41.86972061483428,12.50741958618164";
		
		doc =  Jsoup.connect(indirizzo).get();
		
		// estrae il numero di annunci
		Elements annListaCounterElement = doc.select(".count-ads");
		
		String annListaCounterElementString = annListaCounterElement.text();
		annListaCounterElementString = annListaCounterElementString.replaceAll("[^0-9]", "");
		
		
		int numeroAnnunci =  Integer.parseInt(annListaCounterElementString);
		
		int numeroPagine =  (numeroAnnunci / 25) + ((numeroAnnunci % 25 >0 ) ? 1 : 0) ;
		
		
		System.out.println(numeroPagine );
		System.out.println(annListaCounterElementString);
		
		int idCasa=0;
		
		
		//TODO for numero pagine doc=Jsoup.connect(indirizzo+pagina ).get();
for (int k=1 ; k<= numeroPagine ; k++ )	
{		
	
		String indirizzoPagina=indirizzo+"&pag="+k;
		
		
		doc =  Jsoup.connect(indirizzoPagina).get();
		//estrae gli annunci nella pagina 
		
		Elements annunci = doc.select(".text-primary").select("a");
		//System.out.println(annunci.size());
		
		
		Elements prezzi = doc.select(".listing-features__price").select("li");
		if (annunci.size() != prezzi.size())
			   {System.out.println("ERRORE Prezzi diversi da annunci");
			    return; }

		Elements stanze = doc.select(".listing-features__room").select("div");
		if (stanze.size() != prezzi.size())
			   {System.out.println("ERRORE stanze diversi da annunci");
			    return; }

		
		for (int i = 0; i < annunci.size() ; i++) 
		{
			System.out.println("<CASA>");
			
			idCasa++;
			
			System.out.println("      <ID_CASA>");
			System.out.println("             "+idCasa);
			System.out.println("      </ID_CASA>");
			
			
			Element annuncio=annunci.get(i);
			
			String linkCasa= annuncio.attr("href");
			
			System.out.println("      <LINK>");
			System.out.println("             "+linkCasa);
			System.out.println("      </LINK>");
			
			System.out.println("      <FONTE>");
			System.out.println("           immobiliare.it");
			System.out.println("      </FONTE>");
			
			System.out.println("      <DALLA_DATA>");
			System.out.println("           2017/02/05");
			System.out.println("      </DALLA_DATA>");
			
			
			
			String prezzo = prezzi.get(i).text().replaceAll("[^0-9]", "");;
			
			System.out.println("      <PREZZO>");
			System.out.println("             "+prezzo);
			System.out.println("      </PREZZO>");
		
				

			System.out.println("<CASA>");
					
			System.out.println();
			
		}
}

} // end main 
	
	
	static String estraiDaAnnuncioID ( Element annuncio)
	{
		return annuncio.id().replace("wrapper_riga_", "");
	};
	
	static String estraiDaAnnuncioURL ( Element annuncio)
	{
	 return annuncio.select("strong a").attr("href");
	};

}