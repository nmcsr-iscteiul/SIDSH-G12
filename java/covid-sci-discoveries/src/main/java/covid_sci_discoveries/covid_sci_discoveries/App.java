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
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**This Class Extracts the metadata of PDFs in a directory and builds a HTML table with the extracted data.
 * @author Frederico Correia
 *
 */
public class App{
	/**
	 * Array List with the titles of each PDF file
	 */
	List<String> title = new ArrayList<String>();
	/**
	 * Array List with the journal name of each PDF file
	 */
	List<String> journal = new ArrayList<String>();
	/**
	 * Array List with the year of publishment of each PDF file
	 */
	List<String> year = new ArrayList<String>();
	/**
	 * Array List of Lists of the Authors of each PDF file
	 */
	List<List<DocumentAuthor>> authors = new ArrayList<List<DocumentAuthor>>();
	/**
	 * Array List of path of each file
	 */
	List<String> filep = new ArrayList<String>();
	/**
	 * Array List of Authors of each PDF file parsed into Strings
	 */
	List<String> authorToS = new ArrayList<String>();
	/**
	 * Int thats used to iterate in for loops
	 */
	int i=0;
	/**
	 * String to test if HTML body is working
	 */
	String htmlBegin;
	/**
	 * String to test if HTML is working
	 */
	String htmlEnd;
	
	/**This method will start by getting the files on the Covid_Scientific_Discoveries_Repository of the project, which will be a copy of the same repository of the website.<p>
	 * It will execute the startExtract method for each file, and add its path to the respective ArrayList.<p>
	 * Then it will create a String of the authors of the PDF document, translating it from DocumentAuthor to a readable format.<p>
	 * And after all information is gathered it will used it to execute the getHTML method to create the HTML table. 
	 * 
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws AnalysisException
	 * @throws TransformationException
	 * @throws InterruptedException
	 */
	public void startApp() throws IOException, TimeoutException, AnalysisException, TransformationException, InterruptedException {
		File f = new File("HTML/Covid_Scientific_Discoveries_Repository/");
		File[] listOfFiles = f.listFiles();		
		for(File ff:listOfFiles) {
			startExtract(ff);
			filep.add(ff.getName());
		}
		for(int i=0; i<authors.size(); i++) {
			String s = "";
			for(DocumentAuthor auth:authors.get(i)) {
				s = (s + auth.getName() + ". ");
				
			}
			authorToS.add(s);
		}
		getHtml(title, journal, year, authorToS, filep);
	}

	
	/**This method will receive a file extract the desired metadata and separate them in their own specified List<p> 
	 * The PDF title metadata will go to the title arraylist.<p>
	 * The PDF journal name metadata will go to the journal arraylist.<p>
	 * The PDF year of publishment metadata will go to the year arraylist.<p>
	 * The PDF authors metadata will go to the author arraylist.<p> 
	 * The format of PDF authors isnt String so we have to create a different string of each member of every list of the ArrayList for it to be in a readable format
	 * 
	 * @param f PDF file to be extracted 
	 * @throws AnalysisException
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws TransformationException
	 * @throws InterruptedException
	 */
	public void startExtract(File f) throws AnalysisException, IOException, TimeoutException, TransformationException, InterruptedException{
		ContentExtractor extractor = new ContentExtractor();
        InputStream inputStream=new FileInputStream(f);
        extractor.setPDF(inputStream);
        title.add(extractor.getMetadata().getTitle());
        journal.add(extractor.getMetadata().getJournal());
        year.add(extractor.getMetadata().getDate(DateType.PUBLISHED).getYear());
        authors.add(extractor.getMetadata().getAuthors());
	}
	
	/**This method will create a HTML table receiving every String Arraylist so it can populate the table in a readable format.
	 * The code of HTML table will be written in a new file thats in the HTML folder of the project folder.
	 * 
	 * @param title2 List of titles of each PDF document extracted to be added in the title column of the HTML table.
	 * @param journal2 List of journal name of each PDF document extracted to be added in the journal column of the HTML table.
	 * @param year2 List of year of publishment of each PDF document extracted to be added in the year column of the HTML table.
	 * @param authorToS2 List of authors (already parsed into Sting) of each PDF documents to be added in the authors column of the HTML table.
	 * @param filep2 List of file path of each PDF document extracted to be used to open the document in the HTML table.
	 * @throws IOException 
	 */
	public void getHtml(List<String> title2, List<String> journal2, List<String> year2, List<String> authorToS2, List<String> filep2) throws IOException{
		List<String> list = new ArrayList<String>();
		String header = Files.readString(Paths.get("HTML/header.html"));
		String documentPre = "<html><style> table, th, td { border: 1px solid black; </style> </head> <body>";
		htmlBegin= documentPre;
		list.add(header);
		list.add(documentPre);
		String headerColumn = "<table style=\"background-color: rgba(0, 0, 0, 0.6); color: white\"> <tr> <th>Article title</th> <th>Journal name</th> <th>Publication year</th> <th>Authors</th> </tr>";
		list.add(headerColumn);
		for(int i=0; i < title2.size(); i++) {
		String htmlColumn = "<tr>"+ "<td><a href="+ "Covid_Scientific_Discoveries_Repository/" + filep2.get(i)+">"+ title2.get(i)+"</a></td> <td>"+journal2.get(i)+"<td>"+year2.get(i)+"<td>"+authorToS2.get(i)+"</td>";
		list.add(htmlColumn);
		}
		String documentPost = " </table></body></html>";
		htmlEnd= documentPost;
		list.add(documentPost );
		String footer = Files.readString(Paths.get("HTML/footer.html"));
		list.add(footer);
		File f = new File("HTML/covid-sci-discoveries.html");
		try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {

		    for (String html : list) {
		        bw.write(html);
		    }

		} catch (IOException e) {
		    e.printStackTrace();
		}
	}
	
	/**Getter for PDF title in x position of Arraylist 
	 * 
	 * @param i Postion to look for in Arraylist
	 * @return Title of PDF document in given position
	 */
	public String getTitle(int i) {
		return title.get(i);
	}

	/**Getter for PDF journal name in x position of Arraylist 
	 * 
	 * @param i Postion to look for in Arraylist
	 * @return Journal name of PDF document in given position
	 */
	public String getJournal(int i) {
		return journal.get(i);
	}

	/**Getter for PDF year of publishment in x position of ArrayList
	 * 
	 * @param i Position to look for in ArrayList
	 * @return Year of publishment of PDF document in given position
	 */
	public String getYear(int i) {
		return year.get(i);
	}

	/**Getter for PDF author in y position of DocumentAuthor in x position of ArrayList
	 * 
	 * @param i Position to look for in ArrayList
	 * @param ii Position to look for in DocumentAuthor List
	 * @return Name of author in given position of the DocumentAuthor list of a PDF document in given position
	 */
	public String getAuthors(int i, int ii) {
		return authors.get(i).get(ii).getName();
	}

	/**Getter for PDF path in x position of ArrayList
	 * 
	 * @param i Position to look for in ArrayList
	 * @return Path of PDF document in given position
	 */
	public String getFilep(int i) {
		return filep.get(i);
	}

	/**Getter for PDF authors in a readable format in x position of ArrayList
	 * 
	 * @param i Position to look for in ArrayList
	 * @return Fully concated String of all authors of a DocumentAuthor list of a PDF document in given position
	 */
	public String getAuthorToS(int i) {
		return authorToS.get(i);
	}
	
	/**Getter to check current value of i
	 * 
	 * @return Value of i
	 */
	public int getI() {
		return i;
	}

	/**Getter to check start of HTML code
	 * 
	 * @return String of HTML code
	 */
	public String getHtmlBegin() {
		return htmlBegin;
	}
	
	/**Getter to check end of HTML code
	 * 
	 * @return String of HTML code
	 */
	public String getHtmlEnd() {
		return htmlEnd;
	}
}