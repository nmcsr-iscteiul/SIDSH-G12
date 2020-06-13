package tests;

import static org.junit.jupiter.api.Assertions.*;

import org.junit.jupiter.api.Test;

import covid_graph_spread.ReadFileFromRepository;

class ReadFileFromRepositoryTest {

	@Test
	void testGetHtmlTable() {
		ReadFileFromRepository rd = new ReadFileFromRepository();
		assertDoesNotThrow(
	            ()->{
	            	rd.getHtmlTable();
	            });
	}

	@Test
	void testCloneRepository() {
		ReadFileFromRepository rd = new ReadFileFromRepository();
		assertDoesNotThrow(
	            ()->{
	            	rd.cloneRepository();
	            });
	}

	@Test
	void testShowTags() {
		ReadFileFromRepository rd = new ReadFileFromRepository();
		assertThrows(Exception.class,
	            ()->{
	            	rd.showTags();
	            });
	}

	@Test
	void testCheckIfCommitHasTags() {
		ReadFileFromRepository rd = new ReadFileFromRepository();
		assertThrows(Exception.class,
	            ()->{
	            	rd.checkIfCommitHasTags(null, null);
	            });
	}

}
