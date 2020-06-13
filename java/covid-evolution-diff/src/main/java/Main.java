import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.lib.Ref;

import java.util.ArrayList;
import java.util.List;

public class Main {
    public static void main(String[] args) {
        DiffGenerator diffGenerator = new DiffGenerator("https://github.com/vbasto-iscte/ESII1920");
        Git git = diffGenerator.getGit();
        ArrayList<Ref> tagList = (ArrayList<Ref>) diffGenerator.getTags();
        List<List<String>> generatedDiffStrings = diffGenerator.calculateDiff(tagList, "covid19spreading.rdf");
        HtmlGenerator generator = new HtmlGenerator(generatedDiffStrings.get(0), generatedDiffStrings.get(1));
        generator.htmlFormatter();
    }
}
