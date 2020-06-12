import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
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

public class DiffGenerator {
    private Git git;
    private String repositoryUrl;

    public DiffGenerator(String URL) {
        this.repositoryUrl = URL;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public Git getGit() {
        return git;
    }

    public Repository getRepo(){
        return git.getRepository();
    }

    public Git getGitRepository() {
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
            return git;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public List<Ref> getTags(Git result) {
        List<Ref> call = null;
        try {
            call = result.tagList().call();

            for (Ref ref : call) {
            }
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return call;
    }

    public String getFileText(String fileName, AbstractTreeIterator tree) throws IOException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
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
        return stream.toString();
    }

    public List<List<String>> getDiff(List<Ref> tagList, String fileName) {
        List<String> oldLinesFromString = new ArrayList<>();
        List<String> newLinesFromString = new ArrayList<>();
        try {
            AbstractTreeIterator oldTreeParser = prepareTreeParser(getRepo(), tagList.get(0).getObjectId().getName());
            AbstractTreeIterator newTreeParser = prepareTreeParser(getRepo(), tagList.get(1).getObjectId().getName());

            oldLinesFromString = getFileText(fileName, oldTreeParser).lines().collect(Collectors.toList());
            newLinesFromString = getFileText(fileName, newTreeParser).lines().collect(Collectors.toList());
            List<String> diffStringStream = getDiffText(oldTreeParser,newTreeParser, fileName);
            generateFormattedTexts(diffStringStream,oldLinesFromString,newLinesFromString);
            HtmlGenerator generator = new HtmlGenerator(newLinesFromString, oldLinesFromString);
            generator.htmlFormatter();
        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        List<List<String>> formattedStringLists = new ArrayList<>();
        formattedStringLists.add(oldLinesFromString);
        formattedStringLists.add(newLinesFromString);
        return formattedStringLists;
    }

    public List <String> getDiffText(AbstractTreeIterator oldTreeParser,AbstractTreeIterator newTreeParser, String fileName) throws IOException, GitAPIException {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        List<DiffEntry> diff = git.diff().
                setOldTree(oldTreeParser).
                setNewTree(newTreeParser).
                setPathFilter(PathFilter.create(fileName)).call();
        for (DiffEntry entry : diff) {
            DiffFormatter formatter = new DiffFormatter(stream);
            formatter.setRepository(getRepo());
            formatter.format(entry);
        }
        return stream.toString().lines().collect(Collectors.toList());
    }

    public void generateFormattedTexts(List<String> diffStringStream, List<String> oldLinesFromString, List<String> newLinesFromString){
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


    private static AbstractTreeIterator prepareTreeParser(Repository repository, String objectId) throws IOException {
        // from the commit we can build the tree which allows us to construct the TreeParser
        //noinspection Duplicates
        try (RevWalk walk = new RevWalk(repository)) {
            RevCommit commit = walk.parseCommit(ObjectId.fromString(objectId));
            RevTree tree = walk.parseTree(commit.getTree().getId());

            CanonicalTreeParser treeParser = new CanonicalTreeParser();
            try (ObjectReader reader = repository.newObjectReader()) {
                treeParser.reset(reader, tree.getId());
            }

            walk.dispose();

            return treeParser;
        }
    }
}