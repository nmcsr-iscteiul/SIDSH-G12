package covid_query.covid_query;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.Test;

class XmlProjectTest {

	@Test
	void testRegionName() throws IOException {
		XmlProject x = new XmlProject("/RDF/NamedIndividual/@*");
		x.generateHTML();
		assertEquals(true, x.isHtmlFlag());
	}

	@Test
	void testTestesAlg() {
		QueryFixer f = new QueryFixer("//*[contains(@about,Algarve)]/Testes/text()");
	}
	
	@Test
	void testInfecoesAlg() {
		QueryFixer f = new QueryFixer("//*[contains(@about,Algarve)]/Infecoes/text()");
	}
	
	@Test
	void testInternAlg() {
		QueryFixer f = new QueryFixer("//*[contains(@about,Algarve)]/Internamentos/text()");
	}
	
	@Test
	void testDefault() {
		QueryFixer f = new QueryFixer("hihi");
	}

}
