package covid_graph_spread;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import org.apache.commons.io.FileUtils;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoHeadException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.lib.ObjectId;
import org.eclipse.jgit.lib.ObjectLoader;
import org.eclipse.jgit.lib.PersonIdent;
import org.eclipse.jgit.lib.ProgressMonitor;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevObject;
import org.eclipse.jgit.revwalk.RevTag;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

public class ReadFileFromRepository {

	private static final String REMOTE_URL = "https://github.com/vbasto-iscte/ESII1920";

	public List<List<String>> createRepository() throws IOException {
		// prepare a new folder for the cloned repository
		List<List<String>> listToHTMLTable = new ArrayList<List<String>>() ;
		List<String> fileTimeStamp = new ArrayList<String>();
		List<String> fileName= new ArrayList<String>();
		List<String> fileTag= new ArrayList<String>();
		List<String> tagDescription= new ArrayList<String>();
		File localPath = null;
		try {
			localPath = File.createTempFile("TestGitRepository", "");
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		if (!localPath.delete()) {
			throw new IOException("Could not delete temporary file " + localPath);
		}

		// then clone
		System.out.println("Cloning from " + REMOTE_URL + " to " + localPath);
		try (Git result = Git.cloneRepository().setURI(REMOTE_URL).setDirectory(localPath)
				.setProgressMonitor(new SimpleProgressMonitor()).call()) {
			// Note: the call() returns an opened repository already which needs to be
			// closed to avoid file handle leaks!
			showTags(result);
			 Iterable<RevCommit> commits = result.log().all().call();
			 List<Ref> list = showTags(result);
             int count = 0;
             for (RevCommit commit : commits) {
            	 
                 //System.out.println("LogCommit: " + commit.getId().getName());
                 for(Ref call : list) {
                	 if(call.getObjectId().getName().equals(commit.getId().getName())) {
                		 //ObjectLoader loader = result.getRepository().open(call.getObjectId());
                        // loader.copyTo(System.out);
                         //System.out.println("tag description: " + call.getId().getName());
                		// System.out.println("Commits com tags: " + commit.getId().getName());
                		 try (RevWalk walk = new RevWalk(result.getRepository())) {
                             
                			
                             

                             // finally try to print out the tag-content
                             System.out.println("\nTag-Content: \n");
                             ObjectLoader loader = result.getRepository().open(call.getObjectId());
                             System.out.println("INICIO *******************************************");
                             OutputStream os = null;
                             loader.copyTo(os);
                             String s = new OutputStreamWriter(os).toString();
                             String parsed = s;
                             System.out.println(parsed);
                             System.out.println("FIM *******************************************");

                             walk.dispose();
                         }
                		 
                	
                		 Timestamp ts = new Timestamp(commit.getCommitTime());
                		 PersonIdent authorIdent = commit.getAuthorIdent();
                		 Date authorDate = authorIdent.getWhen();
                		 fileTimeStamp.add(authorDate.toGMTString());
                		 fileName.add("covid19spreading.rdf");
                		 fileTag.add(call.getObjectId().getName());
                		 tagDescription.add(call.getObjectId().getName());
                	     
                	 }
                 }
               
                 count++;
             }
             
             listToHTMLTable.add(fileTimeStamp);
             listToHTMLTable.add(fileName);
             listToHTMLTable.add(fileTag);
             listToHTMLTable.add(tagDescription);
             return listToHTMLTable;
         
		} catch (InvalidRemoteException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (TransportException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (GitAPIException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		// clean up here to not keep using more and more disk-space for these samples
		try {
			FileUtils.deleteDirectory(localPath);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	
		return null;
	}

	private static class SimpleProgressMonitor implements ProgressMonitor {
		@Override
		public void start(int totalTasks) {
			System.out.println("Starting work on " + totalTasks + " tasks");
		}

		@Override
		public void beginTask(String title, int totalWork) {
			System.out.println("Start " + title + ": " + totalWork);
		}

		@Override
		public void update(int completed) {
			System.out.print(completed + "-");
		}

		@Override
		public void endTask() {
			System.out.println("Done");
		}

		@Override
		public boolean isCancelled() {
			return false;
		}
	}
	
	public  List<Ref> showTags(Git git) {
		List<Ref> call = null;
		try {
			call = git.tagList().call();
		} catch (GitAPIException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
        for (Ref ref : call) {
            
            // fetch all commits for this tag
            LogCommand log = git.log();

            Ref peeledRef;
			try {
				peeledRef = git.getRepository().getRefDatabase().peel(ref);
				if(peeledRef.getPeeledObjectId() != null) {
	            	log.add(peeledRef.getPeeledObjectId());
	            	Iterable<RevCommit> logs = log.call();
	    			for (RevCommit rev : logs) {
	    				System.out.println("Commit: " + rev  + ", name: " + rev.getName() + ", id: " + rev.getId().getName() );
	    			}
	            } else {
	            	log.add(ref.getObjectId());
	            }
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (NoHeadException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (GitAPIException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
            

			
        }
		return call;
	}
	public static void main(String[] args) throws IOException {
		new ReadFileFromRepository().createRepository();
	}
}
