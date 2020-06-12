package main;

import java.util.List;
import covid_graph_spread.CreateHTMLTable;
import covid_graph_spread.ReadFileFromRepository;

/**
 * It will create an HTML table with data from the same file that has multiple
 * versions - git tags and with the help of an external website, show the
 * differences in the file.
 * 
 * @author João Pinto
 *
 */
public class Main {
	/**
	 * Starts the main method of the Covid Graph Spread.
	 */
	public static void main() {
		ReadFileFromRepository rd = new ReadFileFromRepository();
		List<List<String>> htmlTableContentsList = rd.getHtmlTable();
		CreateHTMLTable ct = new CreateHTMLTable();
		ct.buildHtmlPage(htmlTableContentsList.get(0), htmlTableContentsList.get(1), htmlTableContentsList.get(2),
				htmlTableContentsList.get(3), htmlTableContentsList.get(4));

	}

}
