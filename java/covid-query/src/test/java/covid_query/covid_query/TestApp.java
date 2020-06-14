package covid_query.covid_query;

import static org.junit.jupiter.api.Assertions.*;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.junit.jupiter.api.Test;

class TestApp {

	@Test
	void test() throws InvalidRemoteException, TransportException, GitAPIException {
		App a = new App();
    	a.getRDFFile();
    	assertEquals(true, a.isFileFlag());
	}
	
	@Test
	void testGetSetQuery() {
		App a = new App();
		a.setQuery("teste");
		assertEquals("teste", a.getQuery());
	}
}
