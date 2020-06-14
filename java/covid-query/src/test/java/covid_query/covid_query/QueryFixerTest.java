package covid_query.covid_query;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

class QueryFixerTest {

	@Test
	void testRegionName() {
		QueryFixer f = new QueryFixer("/RDF/NamedIndividual/@*");
		assertEquals("/RDF/NamedIndividual/@*", f.getQuery());
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
