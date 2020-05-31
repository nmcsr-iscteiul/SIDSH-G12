package covid_sci_discoveries.covid_sci_discoveries;

import org.jdom.Element;
import pl.edu.icm.cermine.ContentExtractor;
import pl.edu.icm.cermine.content.model.ContentStructure;
import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.exception.TransformationException;
import pl.edu.icm.cermine.metadata.affiliation.CRFAffiliationParser;
import pl.edu.icm.cermine.metadata.model.DateType;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.tools.timeout.TimeoutException;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * Hello world!
 *
 */
public class App{
	 
	List<String> title = new ArrayList<String>();
	List<String> journal = new ArrayList<String>();
	List<String> year = new ArrayList<String>();
	List<List<DocumentAuthor>> authors = new ArrayList<List<DocumentAuthor>>();
	List<String> filep = new ArrayList<String>();
	List<String> authorToS = new ArrayList<String>();
	int i=0;
	
	public void startExtract(File f) throws AnalysisException, IOException, TimeoutException, TransformationException, InterruptedException{
		ContentExtractor extractor = new ContentExtractor();
        InputStream inputStream=new FileInputStream(f);
        extractor.setPDF(inputStream);
        title.add(extractor.getMetadata().getTitle());
        journal.add(extractor.getMetadata().getJournal());
        year.add(extractor.getMetadata().getDate(DateType.PUBLISHED).getYear());
        authors.add(extractor.getMetadata().getAuthors());
	}
	public void getHtml(List<String> title2, List<String> journal2, List<String> year2, List<String> authorToS2, List<String> filep2){
		List<String> list = new ArrayList<String>();
		String documentPre = "<html><style> table, th, td { border: 1px solid black; </style> </head> <body>";
		list.add(documentPre);
		String headerColumn = "<table> <tr> <th>Title</th> <th>Journal</th> <th>Year</th> <th>Author</th> </tr>";
		list.add(headerColumn);
		for(int i=0; i < title2.size(); i++) {
		String htmlColumn = "<tr>"+ "<td><a href="+ "Covid19ScientificArticles/" + filep2.get(i)+">"+ title2.get(i)+"</a></td> <td>"+journal2.get(i)+"<td>"+year2.get(i)+"<td>"+authorToS2.get(i)+"</td>";
		list.add(htmlColumn);
		}
		String documentPost = " </table></body></html>";
		list.add(documentPost );
		File f = new File("HTML/data.html");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {

		    for (String html : list) {
		        bw.write(html);
		    }

		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	public static void main( String[] args ) throws AnalysisException, IOException, TimeoutException, TransformationException, InterruptedException {
		App a = new App();
		File f = new File("HTML/Covid19ScientificArticles/");
		File[] listOfFiles = f.listFiles();		
		for(File ff:listOfFiles) {
			a.startExtract(ff);
			a.filep.add(ff.getName());
		}
		for(int i=0; i<a.authors.size(); i++) {
			String s = "";
			for(DocumentAuthor auth:a.authors.get(i)) {
				s = (s + auth.getName() + ". ");
				
			}
			a.authorToS.add(s);
		}
		a.getHtml(a.title, a.journal, a.year, a.authorToS, a.filep);
	}
}
