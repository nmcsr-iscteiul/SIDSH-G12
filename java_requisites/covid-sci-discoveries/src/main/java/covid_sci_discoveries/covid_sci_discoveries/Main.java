package covid_sci_discoveries.covid_sci_discoveries;

import java.io.File;
import java.io.IOException;

import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.exception.TransformationException;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.tools.timeout.TimeoutException;

/**This Class will start the extract the PDF files in a directory and put their metadata in a generated HTML table to create a HTML file 
 * @author Frederico Correia
 *
 */
public class Main {
	/**This is the Main method. It will create a new App and excute its methods of extracting the PDFs and creating the HTML table.<p>
	 * It will first have to check the directory to see how many files there are and then it will start extracting the desired metadata and saving the path of each file. <p>
	 * After the extraction it will create a String of all the authors of the DocumentAuthor class extracted for it to be in a readable format.<p>
	 * Finally it will generate the HTML table of every document by iterating the lists of saved information from the extraction and parsing into String.
	 * 
	 * @param args
	 * @throws AnalysisException
	 * @throws IOException
	 * @throws TimeoutException
	 * @throws TransformationException
	 * @throws InterruptedException
	 */
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
