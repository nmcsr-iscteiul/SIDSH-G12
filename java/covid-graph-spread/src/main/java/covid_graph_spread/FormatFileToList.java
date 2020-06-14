package covid_graph_spread;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.JGitInternalException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;

/**
 * Fills the HTML table with the contents it receives from the files in the
 * remote repository. It will add per each tag one file timestamp, one file
 * name, one file tag, one tag description and one spread visualization link.
 * 
 * @author Joao Pinto
 *
 */
public class FormatFileToList {

	private List<List<String>> listToHTMLTable = new ArrayList<List<String>>();
	private List<String> fileTimeStamp = new ArrayList<String>();
	private List<String> fileName = new ArrayList<String>();
	private List<String> fileTag = new ArrayList<String>();
	private List<String> tagDescription = new ArrayList<String>();
	private List<String> spreadVisualizationlink = new ArrayList<>();

	/**
	 * Returns the complete HTML table.
	 * 
	 * @return Complete HTML table
	 */
	public List<List<String>> getListToHTMLTable() {
		return listToHTMLTable;
	}

	/**
	 * For one tag (that has an associated commit) it will add to a list that it
	 * will in the future become a line of the table in the HTML page. It will
	 * "format" the data in a way that can be visualized in an easy way for the
	 * user. It will concatenate a link from the data of a single file to show in a
	 * graphical manner the Covid Information. It will have to use the deprecated
	 * toGMTString() to show in a readable way to the user. It will get the complete
	 * tag description.
	 * 
	 * @param repository Repository that has the file(s) needed for the list
	 * @param tag Tag that has a commit associated to it
	 * @param commit Commit that has a tag associated to it.
	 */
	@SuppressWarnings("deprecation")
	public void cleanAndCorrectFileFromRepo(Git repository, Ref tag, RevCommit commit) {
		Map<ObjectId, String> names;
		try {
			names = repository.nameRev().add(tag.getObjectId()).addPrefix("refs/tags/").call();
			ObjectLoader loader = repository.getRepository().open(tag.getObjectId());
			String string = new String(loader.getBytes());
			tagDescription.add(
					string.substring(string.lastIndexOf("----") + 1).replace("---", "").trim().replaceAll(" +", " "));

			PersonIdent authorIdent = commit.getAuthorIdent();
			Date authorDate = authorIdent.getWhen();
			fileTimeStamp.add(authorDate.toGMTString());
			fileName.add("covid19spreading.rdf");
			fileTag.add(names.get(tag.getObjectId()));
			spreadVisualizationlink
					.add("http://visualdataweb.de/webvowl/#iri=https://raw.githubusercontent.com/vbasto-iscte/ESII1920/"
							+ tag.getObjectId().getName() + "/covid19spreading.rdf");
		} catch (MissingObjectException | JGitInternalException | GitAPIException e) {
			System.err.println("Could not correctly retrieve all the tags ");
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("Could not open the Ref Object: " + tag);
			e.printStackTrace();
		}
	}

	/**
	 * Adds to the list containing all data that will become the table that will be
	 * visualized in the HTML Page.
	 */
	public void fillTables() {
		listToHTMLTable.add(fileTimeStamp);
		listToHTMLTable.add(fileName);
		listToHTMLTable.add(fileTag);
		listToHTMLTable.add(tagDescription);
		listToHTMLTable.add(spreadVisualizationlink);
	}
}
