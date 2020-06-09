package covid_sci_discoveries.covid_sci_discoveries;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import java.io.IOException;

import org.junit.Rule;
import org.junit.jupiter.api.Test;

import pl.edu.icm.cermine.exception.AnalysisException;
import pl.edu.icm.cermine.exception.TransformationException;
import pl.edu.icm.cermine.metadata.model.DocumentAuthor;
import pl.edu.icm.cermine.tools.timeout.TimeoutException;

class TestApp {
	
	@Test
	void test() throws TimeoutException, AnalysisException, IOException, TransformationException, InterruptedException {
		App a = new App();
		a.startExtract(new File("HTML/Covid Scientific Discoveries Repository/178-1-53.pdf"));
		assertEquals(0, a.getI());
		assertEquals("Pandemic versus Epidemic Influenza Mortality: A Pattern of Changing Age Distribution", a.getTitle(0));		
	}
	@Test
	void test1() throws TimeoutException, AnalysisException, IOException, TransformationException, InterruptedException {
		App a = new App();
		a.startExtract(new File("HTML/Covid Scientific Discoveries Repository/178-1-53.pdf"));
		assertEquals("The Journal of Infectious Diseases", a.getJournal(0));
		
	}
	@Test
	void test2() throws TimeoutException, AnalysisException, IOException, TransformationException, InterruptedException {
		App a = new App();
		a.startExtract(new File("HTML/Covid Scientific Discoveries Repository/178-1-53.pdf"));
		assertEquals("1998", a.getYear(0));
		
	}
	@Test
	void test3() throws TimeoutException, AnalysisException, IOException, TransformationException, InterruptedException {
		App a = new App();
		a.startExtract(new File("HTML/Covid Scientific Discoveries Repository/178-1-53.pdf"));
		assertEquals("Lone Simonsen", a.getAuthors(0, 0));
	}
	@Test
	void test4() throws TimeoutException, AnalysisException, IOException, TransformationException, InterruptedException {
		App a = new App();
		File f = new File("HTML/Covid Scientific Discoveries Repository/178-1-53.pdf");
		a.filep.add(f.getName());
		a.startExtract(new File("HTML/Covid Scientific Discoveries Repository/178-1-53.pdf"));
		assertEquals("178-1-53.pdf", a.getFilep(0));
	}
	
	@Test
	void test5() throws TimeoutException, AnalysisException, IOException, TransformationException, InterruptedException {
		App a = new App();
		a.startExtract(new File("HTML/Covid Scientific Discoveries Repository/178-1-53.pdf"));
		for(int i=0; i<a.authors.size(); i++) {
			String s = "";
			for(DocumentAuthor auth:a.authors.get(i)) {
				s = (s + auth.getName() + ". ");
				
			}
			a.authorToS.add(s);
		}
		assertNotEquals("Lone Simonsen. Matthew J. Clarke. Lawrence B. Schonberger. Nancy H. Arden. Nancy J. Cox. Keiji", a.getAuthorToS(0));
	}
	@Test
	void test6() throws TimeoutException, AnalysisException, IOException, TransformationException, InterruptedException {
		App a = new App();
		File f = new File("HTML/Covid Scientific Discoveries Repository/178-1-53.pdf");
		a.filep.add(f.getName());
		a.startExtract(new File("HTML/Covid Scientific Discoveries Repository/178-1-53.pdf"));
		for(int i=0; i<a.authors.size(); i++) {
			String s = "";
			for(DocumentAuthor auth:a.authors.get(i)) {
				s = (s + auth.getName() + ". ");
				
			}
			a.authorToS.add(s);
		}
		a.getHtml(a.title, a.journal, a.year, a.authorToS, a.filep);
		assertEquals("<html><style> table, th, td { border: 1px solid black; </style> </head> <body>", a.getHtmlBegin());
		assertEquals(" </table></body></html>",a.getHtmlEnd());
	}
}
