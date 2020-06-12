package tests;



import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Assert;
import org.junit.jupiter.api.Test;

import covid_graph_spread.CreateHTMLTable;

class CreateHTMLTableTest {
	private List<List<String>> listToHTMLTable = new ArrayList<List<String>>();
	private List<String> fileTimeStamp = new ArrayList<String>();
	private List<String> fileName = new ArrayList<String>();
	private List<String> fileTag = new ArrayList<String>();
	private List<String> tagDescription = new ArrayList<String>();
	private List<String> spreadVisualizationlink = new ArrayList<>();

	@Test
	void test1() {
		CreateHTMLTable ct = new CreateHTMLTable();
		assertDoesNotThrow(() -> ct.buildHtmlPage(fileTimeStamp, fileName,fileTag,
				tagDescription,spreadVisualizationlink));
		

	}

}
