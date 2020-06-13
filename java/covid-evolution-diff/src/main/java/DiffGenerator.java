import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.CorruptObjectException;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @author Bernardo Sequeira (bernardosequeir)
 */
public class DiffGenerator {
    private Git git;
    private String repositoryUrl;

    /**
     * @param URL The url of the repository that will be used to calculate the diff.
     */
    public DiffGenerator(String URL) {
        this.repositoryUrl = URL;
        initialiseGitRepository();
    }

    /**
     * Getter for the repo's url.
     *
     * @return the repo's url.
     */
    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    /**
     * Getter for the jGit Git object.
     *
     * @return the git repository jGit Object.
     */
    public Git getGit() {
        return git;
    }

    /**
     * Getter for the jGit Repository object
     *
     * @return the jGit Repository being currently used.
     */
    public Repository getRepo() {
        return git.getRepository();
    }

    /**
     * Initializes the git Object, using the url defined in the constructor. Also
     * creates a temp local directory to hold the repository.
     */
    public void initialiseGitRepository() {
        File localPath = null;
        try {
            localPath = File.createTempFile("TestGitRepository", "");

            if (!localPath.delete()) {
                throw new IOException("Could not delete temporary file " + localPath);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }

        try {
            git = Git.cloneRepository().setURI(getRepositoryUrl()).setDirectory(localPath).call();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }

    }

    /**
     * Gets a list of all the tags in the repository
     *
     * @return a list with the ref of each tag in the repository.
     */
    public List<Ref> getTags() {
        List<Ref> call = null;
        try {
            call = getGit().tagList().call();

            for (Ref ref : call) {
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return call;
    }

    /**
     * Gets the text content of a specific file of a specific commit.
     *
     * @param fileName the name of the file that will be retrieved.
     * @param tree     tree iterator of the specific commit from which the file will
     *                 be retrieved.
     * @return A string with all the text content of the file.
     */
    public String getFileText(String fileName, AbstractTreeIterator tree) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {

            TreeWalk treeWalk = new TreeWalk(getRepo());
            treeWalk.addTree(tree);
            treeWalk.setRecursive(true);
            treeWalk.setFilter(PathFilter.create(fileName));
            if (!treeWalk.next()) {
                throw new IllegalStateException("Did not find expected file '" + fileName + "'");
            }

            ObjectId objectId = treeWalk.getObjectId(0);
            ObjectLoader loader = getRepo().open(objectId);
            loader.copyTo(stream);


        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return stream.toString();
    }

    /**
     * Generates the Text of both versions of the file, with the diff information
     * already marked (old lines start with -, new lines start with +). Gets the
     * diff of the last two commits that have tags associated.
     *
     * @param tagList  a list of all the tag refs associated with the repository
     * @param fileName the name of the file that will be "diffed"
     * @return the Lists of strings of both the new file and the old one.
     */
    public List<List<String>> calculateDiff(ArrayList<Ref> tagList, String fileName) {
        List<String> oldLinesFromString;
        List<String> newLinesFromString;
        List<List<String>> formattedStringLists = new ArrayList<>();


        AbstractTreeIterator oldTreeParser = prepareTreeParser(getRepo(), tagList.get(2).getObjectId().getName());
        AbstractTreeIterator newTreeParser = prepareTreeParser(getRepo(), tagList.get(1).getObjectId().getName());

        oldLinesFromString = getFileText(fileName, oldTreeParser).lines().collect(Collectors.toList());
        newLinesFromString = getFileText(fileName, newTreeParser).lines().collect(Collectors.toList());
        List<String> diffStringStream = getDiffText(oldTreeParser, newTreeParser, fileName);
        generateFormattedTexts(diffStringStream, oldLinesFromString, newLinesFromString);

        formattedStringLists.add(oldLinesFromString);
        formattedStringLists.add(newLinesFromString);


        return formattedStringLists;
    }

    /**
     * Calls the jGit functions that create the diff text, and formats it so it's
     * easier to parse.
     *
     * @param oldTreeParser tree iterator of the oldest commit from where the diff
     *                      will be calculated.
     * @param newTreeParser tree iterator fo the newest commit from where the diff
     *                      will be calculated.
     * @param fileName      the name of the file from where the diff will be
     *                      calculated.
     * @return a list of strings containing the text content of the diff.
     */
    public List<String> getDiffText(AbstractTreeIterator oldTreeParser, AbstractTreeIterator newTreeParser, String fileName) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            List<DiffEntry> diff = git.diff().setOldTree(oldTreeParser).setNewTree(newTreeParser)
                    .setPathFilter(PathFilter.create(fileName)).call();
            for (DiffEntry entry : diff) {
                DiffFormatter formatter = new DiffFormatter(stream);
                formatter.setRepository(getRepo());
                formatter.format(entry);
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return stream.toString().lines().collect(Collectors.toList());
    }

    /**
     * Because the generated diff only presents us with the lines that have changed
     * and not the entire file, this method combines the diff text and both versions
     * of the file, and marks the differences on the entire files, so that the html
     * can present the whole of the files and their differences.
     *
     * @param diffStringStream   the text generated from the diff.
     * @param oldLinesFromString the text from the oldest file.
     * @param newLinesFromString the text from the newest file.
     */
    public void generateFormattedTexts(List<String> diffStringStream, List<String> oldLinesFromString,
                                       List<String> newLinesFromString) {
        for (String s : diffStringStream) {
            if (s.startsWith("-   ")) {
                String stringToReplace = null;
                for (String string : oldLinesFromString) {
                    if (s.contains(string) && string.length() > 4) {
                        stringToReplace = string;
                    }
                }
                if (stringToReplace != null) {
                    oldLinesFromString.set(oldLinesFromString.indexOf(stringToReplace), "--- " + stringToReplace);
                }
            } else if (s.startsWith("+   ")) {
                String stringToReplace = null;
                for (String string : newLinesFromString) {
                    if (s.contains(string) && string.length() > 4) {
                        stringToReplace = string;
                    }
                }
                if (stringToReplace != null) {
                    newLinesFromString.set(newLinesFromString.indexOf(stringToReplace), "+++ " + stringToReplace);
                }
            }
        }
    }

    /**
     * @param repository The repository that you need the iterator from.
     * @param commitId   The commit that you want the iterator from.
     * @return an AbstractTreeIterator that allows you to access the files from a
     * desired commit and repo.
     */
    public static AbstractTreeIterator prepareTreeParser(Repository repository, String commitId) {
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(ObjectId.fromString(commitId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;

        } catch (IOException exception) {
            exception.printStackTrace();
        }
        return null;
    }
}