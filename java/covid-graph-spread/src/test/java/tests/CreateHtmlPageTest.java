package tests;

import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

import java.util.ArrayList;
import java.util.List;

import org.junit.jupiter.api.Test;

import covid_graph_spread.CreateHtmlPage;

class CreateHtmlPageTest {
	private List<String> fileTimeStamp = new ArrayList<String>();
	private List<String> fileName = new ArrayList<String>();
	private List<String> fileTag = new ArrayList<String>();
	private List<String> tagDescription = new ArrayList<String>();
	private List<String> spreadVisualizationlink = new ArrayList<>();

	@Test 
	void testDummyValues() {
		CreateHtmlPage ct = new CreateHtmlPage();
		fileTimeStamp.add("1");
		fileName.add("2");
		fileTag.add("3");
		tagDescription.add("4");
		spreadVisualizationlink.add("4");
		ct.buildHtmlPage(fileTimeStamp, fileName, fileTag, tagDescription, spreadVisualizationlink);
		assertEquals(ct.getHtmlContentsListlist().get(3),"<tr>" + "<td>" + fileTimeStamp.get(0) + "</td>" + "<td>" + fileName.get(0)
		+ "</td> <td>" + fileTag.get(0) + "<td>" + tagDescription.get(0) + "<td><a href="
		+ spreadVisualizationlink.get(0) + ">" + "Link" + "</a></td>");
		
	}
	@Test
	void testbuildHtmlPageArgumentsNotNull() {
		CreateHtmlPage ct = new CreateHtmlPage();
		assertDoesNotThrow(
				() -> ct.buildHtmlPage(fileTimeStamp, fileName, fileTag, tagDescription, spreadVisualizationlink));

	}

	@Test
	void testbuildHtmlPageArgumentsNull() {
		CreateHtmlPage ct = new CreateHtmlPage();
		List<String> fileTimeStamp = null;
		List<String> fileName = null;
		List<String> fileTag = null;
		List<String> tagDescription = null;
		List<String> spreadVisualizationlink = null;
		assertThrows(NullPointerException.class,
				() -> ct.buildHtmlPage(fileTimeStamp, fileName, fileTag, tagDescription, spreadVisualizationlink));

	}
	


}
