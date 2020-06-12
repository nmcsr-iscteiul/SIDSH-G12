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
	            //do whatever you want to do here
	            //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
	            });
	}

	@Test
	void testCloneRepository() {
		ReadFileFromRepository rd = new ReadFileFromRepository();
		assertDoesNotThrow(
	            ()->{
	            	rd.cloneRepository();
	            //do whatever you want to do here
	            //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
	            });
	}

	@Test
	void testShowTags() {
		ReadFileFromRepository rd = new ReadFileFromRepository();
		assertThrows(Exception.class,
	            ()->{
	            	rd.showTags();
	            //do whatever you want to do here
	            //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
	            });
	}

	@Test
	void testCheckIfCommitHasTags() {
		ReadFileFromRepository rd = new ReadFileFromRepository();
		assertThrows(Exception.class,
	            ()->{
	            	rd.checkIfCommitHasTags(null, null);
	            //do whatever you want to do here
	            //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
	            });
	}

}
