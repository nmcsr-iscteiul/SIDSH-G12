package covid_graph_spread;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class CreateHTMLTable {

	
	private String htmlBegin;
	private String htmlEnd;

	public void getHtml(List<String> fileTimeStamp, List<String> fileName, List<String> fileTag, List<String> tagDescription, List<String> spreadVisualizationLink) throws IOException{
		List<String> list = new ArrayList<String>();
		String header = Files.readString(Paths.get("HTML/header.html"));
		String documentPre = "<html><style> table, th, td { border: 1px solid black; </style> </head> <body>";
		htmlBegin= documentPre;
		list.add(header);
		list.add(documentPre);
		String headerColumn = "<table style=\"background-color: rgba(0, 0, 0, 0.6); color: white\"> <tr> <th>Data Ficheiro</th> <th>Nome Ficheiro</th> <th>Tag ID</th> <th>Descrição Tag</th> <th>Authors</th> </tr>";
		list.add(headerColumn);
		for(int i=0; i < fileTimeStamp.size(); i++) {
		String htmlColumn = "<tr>"+ "<td>"+ fileTimeStamp.get(i) +"</td>"+ "<td>" +fileName.get(i)+"</td> <td>"+fileTag.get(i)+"<td>"+tagDescription.get(i)+"<td>"+spreadVisualizationLink.get(i)+"</td>";
		list.add(htmlColumn);
		}
		String documentPost = " </table></body></html>";
		htmlEnd= documentPost;
		list.add(documentPost );
		String footer = Files.readString(Paths.get("HTML/footer.html"));
		list.add(footer);
		File f = new File("HTML/covid-graph-spread.html");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {

		    for (String html : list) {
		        bw.write(html);
		    }

		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
}
