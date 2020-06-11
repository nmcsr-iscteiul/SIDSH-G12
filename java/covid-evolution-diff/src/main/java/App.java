import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;

import java.io.File;

public class App {
    public static void main(String[] args)  {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();

        try {Git result = Git.cloneRepository().setURI(REMOTE_URL).setDirectory(localPath)
                .setProgressMonitor(new SimpleProgressMonitor()).call())M

            Repository repository = builder.setGitDir(new File("."))
                .readEnvironment() // scan environment GIT_* variables
                .findGitDir() // scan up the file system tree
                .build();
            System.out.println(repository);
            // See e.g. GetRevCommitFromObjectId for how to use a SHA-1 directly
            Ref head = repository.findRef("HEAD");
            System.out.println("Ref of HEAD: " + head + ": " + head.getName() + " - " + head.getObjectId().getName());

            // a RevWalk allows to walk over commits based on some filtering that is defined
            RevWalk walk = new RevWalk(repository);
                RevCommit commit = walk.parseCommit(head.getObjectId());
                System.out.println("Commit: " + commit);

                // a commit points to a tree
                RevTree tree = walk.parseTree(commit.getTree().getId());
                System.out.println("Found Tree: " + tree);

                walk.dispose();

        } catch(Exception e){
            e.printStackTrace();
        }
    }
}