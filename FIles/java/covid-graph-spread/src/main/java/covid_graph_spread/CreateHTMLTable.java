package covid_graph_spread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This class will make an HTML table from a concatenation of a given header, a table that it will build and a footer. It will also do some tweaking of the HTML style of the page.
 * @author Frederico Correia, João Pinto
 *
 */
public class CreateHTMLTable {

	public final String headerPath = "HTML/header.html";
	public final String footerPath = "HTML/footer.html";
	public final String dataPath = "HTML/covid-graph-spread.html";

	/**
	 * 
	 * Creates an html page containing a table which it will build according to the
	 * entry parameters. It will construct the HTML page with the help of a
	 * concatenation of a header and a footer that will get from a defined Path,
	 * along with some html formatation. It will fill the table according to the
	 * number of different tags received.
	 * 
	 * @author Frederico Correia, João Pinto
	 * @param fileTimeStamp Timestamps of the files
	 * @param fileName Names of the files
	 * @param fileTag Tags that have files in the commit the file was created/modified
	 * @param tagDescription Description of the Tag 
	 * @param spreadVisualizationLink Links to an external website to visualize the contents of the File
	 */
	public void buildHtmlPage(List<String> fileTimeStamp, List<String> fileName, List<String> fileTag,
			List<String> tagDescription, List<String> spreadVisualizationLink) {
		try {
			List<String> htmlContentsListlist = new ArrayList<String>();

			String header = Files.readString(Paths.get(headerPath));
			String correctedHeader = header.replaceAll("Scientific Discoveries", "Graph Spread");
			String documentPre = "<html><style> table, th, td { border: 1px solid black; </style> </head> <body>";
			htmlContentsListlist.add(correctedHeader);
			htmlContentsListlist.add(documentPre);
			String headerColumn = "<table style=\"background-color: rgba(0, 0, 0, 0.6); color: white\"> <tr> <th>File Timestamp</th> <th>File Name</th> <th>File Tag</th> <th>Tag Description</th> <th>Spread Visualization Link</th> </tr>";
			htmlContentsListlist.add(headerColumn);
			for (int i = 0; i < fileTimeStamp.size(); i++) {
				String htmlColumn = "<tr>" + "<td>" + fileTimeStamp.get(i) + "</td>" + "<td>" + fileName.get(i)
						+ "</td> <td>" + fileTag.get(i) + "<td>" + tagDescription.get(i) + "<td><a href="
						+ spreadVisualizationLink.get(i) + ">" + "Link" + "</a></td>";
				htmlContentsListlist.add(htmlColumn);
			}
			String documentPost = " </table></body></html>";
			htmlContentsListlist.add(documentPost);
			String footer = Files.readString(Paths.get(footerPath));
			htmlContentsListlist.add(footer);
			writeHtmlPage(htmlContentsListlist);
		} catch (IOException e1) {
			System.err.println(
					"Error reading the header/footer in one of these directory : " + headerPath + " or " + footerPath);
			e1.printStackTrace();
		}

	}

	/**
	 * Writes to a new HTML file the concatenation of the contents in the list received.
	 * @param htmlContentslist Contents of the html page in String form and <b>not</b> concatenated.
	 */
	public void writeHtmlPage(List<String> htmlContentslist) {
		File f = new File(dataPath);
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {

			for (String html : htmlContentslist) {
				bw.write(html);
			}

		} catch (IOException e) {
			System.err.println("Failed in writing the HTML file: " + f.getName());
			e.printStackTrace();
		}
	}
}
