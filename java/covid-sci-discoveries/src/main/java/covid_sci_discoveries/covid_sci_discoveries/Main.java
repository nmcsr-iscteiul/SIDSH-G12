package covid_sci_discoveries.covid_sci_discoveries;

import java.io.File;
import java.io.IOException;

import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.exception.TransformationException;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.tools.timeout.TimeoutException;

/**This Class will be the main method to execute the App. 
 * @author Frederico Correia
 *
 */
public class Main {
	/**This is the Main method. It will create a new App and execute the startApp method which will extract the PDFs and create the HTML table with the desired metadata.
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
		a.startApp();
	}
}