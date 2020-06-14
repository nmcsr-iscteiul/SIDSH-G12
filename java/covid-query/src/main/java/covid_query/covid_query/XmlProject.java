package covid_query.covid_query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;

import org.apache.commons.lang3.StringUtils;

import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathExpression;
import javax.xml.xpath.XPathFactory;
import javax.xml.parsers.DocumentBuilder;
import org.w3c.dom.Document;
import org.w3c.dom.NodeList;

/**Classe encarregue de tratar as queries que dao entrada na App e de gerar o ficheiro HTML com o resultado da query
 * 
 * @author Tomas Godinho
 *
 */
public class XmlProject {
	
	/**Atributo que guarda a query que deu entrada na App
	 * 
	 */
	private String query;
	/**Atributo que indica se o html com a resposta da query ja foi gerado
	 * 
	 */
	private boolean htmlFlag = false;
	
	/**Atributo que guarda o(s) resultado(s) da query que deu entrada
	 * 
	 */
	private ArrayList<String> lista = new ArrayList<String>();
	/**Construtor da classe XmlProject
	 * 
	 * @param query Atributo que guarda a query que deu entrada na App
	 */
	public XmlProject(String query) {
		this.query = query;
	}
	/**metodo que arranca o processo de analise da query, ao aceder ao ficheiro covid19spreading.rdf, e ao correr a query que deu entrada. Existe um controlo sobre essa query que devolve como resultado uma string especifica para avisar o utilizador
	 * 
	 */
	public void run() {
		try {
			File inputFile = new File("repCopy/covid19spreading.rdf");
			DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
			DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
			Document doc = dBuilder.parse(inputFile);
			doc.getDocumentElement().normalize();
			XPathFactory xpathFactory = XPathFactory.newInstance();
			XPath xpath = xpathFactory.newXPath();
			XPathExpression expr = xpath.compile(query);

			switch (query) {
			case "/RDF/NamedIndividual/@*": 
				  System.out.println("Query para obter a lista das regiões: " + query);
				  NodeList nl = (NodeList) expr.evaluate(doc, XPathConstants.NODESET); 
				  for (int i = 0; i < nl.getLength(); i++){
					  System.out.println(StringUtils.substringAfter(nl.item(i).getNodeValue(),"#"));
					  lista.add(StringUtils.substringAfter(nl.item(i).getNodeValue(),"#") + "<br>");
				  } 
				  break;
			  case "//*[contains(@about,'Algarve')]/Testes/text()": 
				  System.out.println("Query para obter o número de testes feitos no Algarve: "+ query); 
				  expr = xpath.compile(query); 
				  lista.add(expr.evaluate(doc, XPathConstants.STRING).toString());
				  System.out.println(expr.evaluate(doc, XPathConstants.STRING).toString()); 
				  break;
			  case "//*[contains(@about,'Algarve')]/Infecoes/text()": 
				  System.out.println("Query para obter o número de infeções no Algarve: " +query);
				  expr = xpath.compile(query); 
				  System.out.println(expr.evaluate(doc, XPathConstants.STRING));
				  lista.add(expr.evaluate(doc, XPathConstants.STRING).toString());
				  break;
			  case "//*[contains(@about,'Algarve')]/Internamentos/text()": 
				  System.out.println("Query para obter o número de internamentos no Algarve: "+ query); 
				  expr = xpath.compile(query); 
				  System.out.println(expr.evaluate(doc, XPathConstants.STRING));
				  lista.add(expr.evaluate(doc, XPathConstants.STRING).toString());
				  break;
			  default: System.out.println("Query invalida");
			  	lista.add("Query invalida");
			  	break;
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	/**Metodo que gera o ficheiro HTML, atraves de Strings pre-concebidas e do resultado da query (lista)
	 * 
	 * @throws IOException
	 */
	public void generateHTML() throws IOException {
		String tagStarts = "<html><style> table, th, td { border: 1px solid black; </style> </head> <body> O Resultado "
				+ "da sua query é: <br>";
		String tagEnds =  " </table></body></html>";
		String header;
		header = Files.readString(Paths.get("HTML/header.html"));
		String footer = Files.readString(Paths.get("HTML/footer.html"));
		File f = new File("HTML/covid-queries.html");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {
			System.out.println("comecei a escrever a lista: " + lista);
			bw.write(header);
			bw.write(tagStarts);
			if(!lista.isEmpty()) {
				for (String queryResults : lista) {
					bw.write(queryResults);
				}
			}
			bw.write(tagEnds);
			bw.write(footer);
        } catch (IOException e) {
            e.printStackTrace();
        }
		setHtmlFlag(true);
	}
	
	/**indica o estado da flag html
	 * 
	 * @return estado da flag html
	 */
	public boolean isHtmlFlag() {
		return htmlFlag;
	}
	/**estipula o estado da flag html
	 * 
	 * @param htmlFlag estado 
	 */
	public void setHtmlFlag(boolean htmlFlag) {
		this.htmlFlag = htmlFlag;
	}
	
	
}
