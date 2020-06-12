package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.IOException;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import covid_graph_spread.FillHTMLTableFields;

class FillHTMLTableFieldsTest {

	@BeforeAll
	static void setUpBeforeClass() throws Exception {
	}

	@AfterAll
	static void tearDownAfterClass() throws Exception {
	}

	@BeforeEach
	void setUp() throws Exception {
	}

	@AfterEach
	void tearDown() throws Exception {
	}

	@Test
	void testGetListToHTMLTable() {
		FillHTMLTableFields ft = new FillHTMLTableFields();
		assertDoesNotThrow(
	            ()->{
	            	ft.getListToHTMLTable();
	            //do whatever you want to do here
	            //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
	            });
	}

	@Test
	void testCleanAndCorrectFileFromRepo() {
		FillHTMLTableFields ft = new FillHTMLTableFields();
		assertThrows(Exception.class,
	            ()->{
	            	ft.cleanAndCorrectFileFromRepo(null, null, null);
	            //do whatever you want to do here
	            //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
	            });
	}

	@Test
	void testFillTables() {
		FillHTMLTableFields ft = new FillHTMLTableFields();
		assertDoesNotThrow(
	            ()->{
	            	ft.fillTables() ;
	            //do whatever you want to do here
	            //ex : objectName.thisMethodShoulThrowNullPointerExceptionForNullParameter(null);
	            });
	}

}
