package covid_graph_spread;

import java.io.File;
import java.io.IOException;
import java.util.List;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevWalk;

/**
 * Gets the remote repository and created a local temporary one. It will get all
 * the commits from that repository and all the tags. It will compare both of
 * those and if they have a same id, meaning that a commit has a tag then it
 * will call the responsible class to deal with the Tags.
 * 
 * @author Jo�o Pinto
 *
 */
public class ReadFileFromRepository {

	private Git git;
	private FormatFileToList prepareHTMLFields = new FormatFileToList();
	private static final String REMOTE_REPOSITORY_URL = "https://github.com/vbasto-iscte/ESII1920";

	/**
	 * It will do all the necessary operations in order to get the complete HTML
	 * table, including cloning the remote repository, getting all commits that have
	 * tags associated to them and fills the table.
	 * 
	 * @return List ready to be inserted into an HTML table
	 */
	public List<List<String>> getHtmlTable() {
		try {
			git = cloneRepository();
			Iterable<RevCommit> commits = git.log().all().call();
			List<Ref> list = showTags();
			checkIfCommitHasTags(commits, list);
			prepareHTMLFields.fillTables();
			return prepareHTMLFields.getListToHTMLTable();
		} catch (GitAPIException | IOException e) {
			System.err.println("Could not call the commits from the repo: " + git);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Clones the remote repository and makes a local, temporary one in the local
	 * machine.
	 * 
	 * @return The now temporary local repository
	 */
	public Git cloneRepository() {
		File localPath;
		try {

			localPath = File.createTempFile("TemporaryGitRepository", "");
			if (!localPath.delete()) {
				throw new IOException("Could not delete temporary file " + localPath);
			}
			return Git.cloneRepository().setURI(REMOTE_REPOSITORY_URL).setDirectory(localPath).setProgressMonitor(null)
					.call();
		} catch (IOException e) {
			System.err.println("Error creating file temporary git repository at:" + REMOTE_REPOSITORY_URL);
			e.printStackTrace();
		} catch (InvalidRemoteException e) {
			System.err
					.println("Invalid Remote when trying to clone the Remote Repository at: " + REMOTE_REPOSITORY_URL);
			e.printStackTrace();
		} catch (TransportException e) {
			System.err.println("Error in the transport operation  when trying to clone the Remote Repository at: "
					+ REMOTE_REPOSITORY_URL);
			e.printStackTrace();
		} catch (GitAPIException e) {
			System.err.println("Error when trying to clone the Remote Repository at: " + REMOTE_REPOSITORY_URL);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * It will get all tags from the repository - including the id.
	 * 
	 * @return List of tags
	 */
	public List<Ref> showTags() {
		List<Ref> call = null;
		try {
			call = git.tagList().call();
			for (Ref ref : call) {
				LogCommand log = git.log();
				Ref peeledRef = git.getRepository().getRefDatabase().peel(ref);
				if (peeledRef.getPeeledObjectId() != null) {
					log.add(peeledRef.getPeeledObjectId());
				} else {
					log.add(ref.getObjectId());
				}
			}
			return call;
		} catch (GitAPIException e) {
			System.err.println("Git exception from this git: " + git);
			e.printStackTrace();
		} catch (IOException e) {
			System.err.println("I/O exception from the git repository " + git);
			e.printStackTrace();
		}
		return null;
	}

	/**
	 * Compares a list of all the commits with the tags - if it has a tag
	 * associated, it will call the method responsible to add to the list that will
	 * in the end be a line in the html table.
	 * 
	 * @param commits List of the commits from the repository
	 * @param tags    List of tags from the repository
	 */
	public void checkIfCommitHasTags(Iterable<RevCommit> commits, List<Ref> tags) {
		for (RevCommit commit : commits) {
			for (Ref tag : tags) {
				if (tag.getObjectId().getName().equals(commit.getId().getName())) {
					try (RevWalk walk = new RevWalk(this.git.getRepository())) {
						prepareHTMLFields.cleanAndCorrectFileFromRepo(this.git, tag, commit);
					}
				}
			}
		}
	}
}
