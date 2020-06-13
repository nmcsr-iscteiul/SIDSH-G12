package tests;

import static org.junit.jupiter.api.Assertions.*;

import java.io.File;
import org.eclipse.jgit.api.Git;
import org.junit.jupiter.api.Test;

import covid_graph_spread.FormatFileToList;
import covid_graph_spread.ReadFileFromRepository;

class FormatFileToListTest {

	@Test
	void testDummyGitRepository() {
			ReadFileFromRepository rd = new ReadFileFromRepository();
			FormatFileToList ft = new FormatFileToList();
				assertThrows(IllegalStateException.class,() -> ft.cleanAndCorrectFileFromRepo(Git.cloneRepository().setURI("https://github.com/vbasto-iscte/ESII1920")
						.setDirectory(File.createTempFile("TemporaryGitRepository", "")).setProgressMonitor(null).call(), rd.showTags().get(0), null));
	

	}

	@Test
	void testGetListToHTMLTable() {
		FormatFileToList ft = new FormatFileToList();
		assertDoesNotThrow(() -> {
			ft.getListToHTMLTable();
		});
	}

	@Test
	void testCleanAndCorrectFileFromRepo() {
		FormatFileToList ft = new FormatFileToList();
		assertThrows(Exception.class, () -> {
			ft.cleanAndCorrectFileFromRepo(null, null, null);
		});
	}

	@Test
	void testFillTables() {
		FormatFileToList ft = new FormatFileToList();
		assertDoesNotThrow(() -> {
			ft.fillTables();
		});
	}

}
