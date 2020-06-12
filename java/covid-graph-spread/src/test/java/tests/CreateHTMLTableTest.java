package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
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
	void testBuildHtmlPage() {
		CreateHTMLTable chtml = new CreateHTMLTable();
		chtml.buildHtmlPage(fileTimeStamp, fileName, fileTag, tagDescription, spreadVisualizationlink);
		
		
	}

	@Test
	void testWriteHtmlPage() {
		fail("Not yet implemented");
	}

}
