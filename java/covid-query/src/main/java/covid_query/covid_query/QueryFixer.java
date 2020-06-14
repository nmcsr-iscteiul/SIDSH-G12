package covid_query.covid_query;

import javax.xml.xpath.XPathConstants;

import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.NodeList;

/**Classe encarregue de "limpar" as queries, ja que estas perdem "''" na transicao de html para java
 * 
 * @author Tomas Godinho
 *
 */
public class QueryFixer {
/**Atributo em que e armazenada a query de entrada e onde ira ser armazenada a query "limpa"
 * 
 */
	private String query;
/**Construtor da classe QueryFixer, que ira receber o query que deu entrada na App e correr o metodo para a "limpar"
 * 
 * @param s String de query que deu entrada na App
 */
	public QueryFixer(String s) {
		query = s;
		fixQuery();
	}
/**Metodo que retorna o parametro query que se encontra definido como private
 * 
 * @return parametro query que se encontra private
 */
	public String getQuery() {
		return query;
	}
/**metodo que opera sobre a query que deu entrada na App, devolvendo os simbolos "''" perdidos na transicao html para java
 * 
 */
	public void fixQuery() {
		switch (this.query) {

		case "/RDF/NamedIndividual/@*":
			break;
		case "//*[contains(@about,Algarve)]/Testes/text()":
			query = "//*[contains(@about,'Algarve')]/Testes/text()";
			break;
		case "//*[contains(@about,Algarve)]/Infecoes/text()":
			query = "//*[contains(@about,'Algarve')]/Infecoes/text()";
			break;
		case "//*[contains(@about,Algarve)]/Internamentos/text()":
			query = "//*[contains(@about,'Algarve')]/Internamentos/text()";
			break;
		default:
			break;
		}
	}

}
