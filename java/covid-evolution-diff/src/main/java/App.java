import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.LogCommand;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.diff.DiffEntry;
import org.eclipse.jgit.diff.DiffFormatter;
import org.eclipse.jgit.errors.IncorrectObjectTypeException;
import org.eclipse.jgit.errors.MissingObjectException;
import org.eclipse.jgit.lib.*;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.revwalk.RevTree;
import org.eclipse.jgit.revwalk.RevWalk;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.eclipse.jgit.treewalk.CanonicalTreeParser;
import org.eclipse.jgit.treewalk.TreeWalk;
import org.eclipse.jgit.treewalk.filter.PathFilter;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class App {
    private Git git;
    private String repositoryUrl = "https://github.com/vbasto-iscte/ESII1920";

    public App(String URL) {
        this.repositoryUrl = URL;
    }

    public String getRepositoryUrl() {
        return repositoryUrl;
    }

    public Git getGit() {
        return git;
    }

    public Git getGitRepository() {
        FileRepositoryBuilder builder = new FileRepositoryBuilder();
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
        TreeWalk treeWalk = new TreeWalk(getGit().getRepository());
        treeWalk.addTree(tree);
        treeWalk.setRecursive(true);
        treeWalk.setFilter(PathFilter.create(fileName));
        if (!treeWalk.next()) {
            throw new IllegalStateException("Did not find expected file '" + fileName + "'");
        }

        ObjectId objectId = treeWalk.getObjectId(0);
        ObjectLoader loader = getGit().getRepository().open(objectId);
        loader.copyTo(stream);
        return stream.toString();
    }

    public String getDiff(List<Ref> tagList, String fileName) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        try {
            AbstractTreeIterator oldTreeParser = prepareTreeParser(getGit().getRepository(), tagList.get(0).getObjectId().getName());
            AbstractTreeIterator newTreeParser = prepareTreeParser(getGit().getRepository(), tagList.get(1).getObjectId().getName());
            List<String> oldLinesFromString = getFileText(fileName, oldTreeParser).lines().collect(Collectors.toList());
            List<String> newLinesFromString = getFileText(fileName, newTreeParser).lines().collect(Collectors.toList());

            List<DiffEntry> diff = git.diff().
                    setOldTree(oldTreeParser).
                    setNewTree(newTreeParser).
                    setPathFilter(PathFilter.create(fileName)).call();
            for (DiffEntry entry : diff) {
                DiffFormatter formatter = new DiffFormatter(stream);
                formatter.setRepository(getGit().getRepository());
                formatter.format(entry);
            }

            List<String> diffStringStream = stream.toString().lines().collect(Collectors.toList());
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
            htmlFormatter(oldLinesFromString, newLinesFromString);

        } catch (IOException e) {
            e.printStackTrace();
        } catch (GitAPIException e) {
            e.printStackTrace();
        }
        return stream.toString();
    }

    private void htmlFormatter(List<String> oldLinesFromString, List<String> newLinesFromString) {
        List<String> HtmlFormattedString = new ArrayList<>();
        Iterator<String> newLines = newLinesFromString.iterator();
        Iterator<String> oldLines = oldLinesFromString.iterator();
        HtmlFormattedString.add("<table><tr><th>New File</th><th>Old File</th></tr>");
        while (newLines.hasNext() || oldLines.hasNext()) {
            if (!newLines.hasNext()) {
                HtmlFormattedString.add(addHtmlTags("", oldLines.next()));
            } else if (!oldLines.hasNext()) {
                HtmlFormattedString.add(addHtmlTags(newLines.next(), ""));
            } else {
                HtmlFormattedString.add(addHtmlTags(newLines.next(), oldLines.next()));
            }
        }
        HtmlFormattedString.add("</table>");
        for (String s : HtmlFormattedString) {
            System.out.println(s);
        }
    }

    private String addHtmlTags(String newString, String oldString) {
        return "<tr><th>" + newString + "</th><th>" + oldString + "</th></tr>";
    }

    public static void main(String[] args) {
        App app = new App("https://github.com/vbasto-iscte/ESII1920");
        Git git = app.getGitRepository();
        List<Ref> tagList = app.getTags(git);
        String string = app.getDiff(tagList, "covid19spreading.rdf");
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