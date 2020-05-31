package covid_sci_discoveries.covid_sci_discoveries;

import java.io.File;
import java.io.IOException;

import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.exception.TransformationException;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.tools.timeout.TimeoutException;

public class Main {

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
