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

public class XmlProject {
	
	private String query;
	private ArrayList<String> lista = new ArrayList<String>();
	
	public XmlProject(String query) {
		this.query = query;
	}
	
	
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
			  }
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
		


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
	}
}
