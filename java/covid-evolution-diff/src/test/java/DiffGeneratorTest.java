import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.lib.Ref;
import org.eclipse.jgit.treewalk.AbstractTreeIterator;
import org.junit.Test;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

public class DiffGeneratorTest {

 @Test
 public void testInitialiseWithValidRepoLink() {
  DiffGenerator dg = new DiffGenerator("https://github.com/vbasto-iscte/ESII1920");
  assertDoesNotThrow(dg::initialiseGitRepository);
 }
 @Test
 public void testGetFileTextWithInvalidFileName(){
  DiffGenerator dg = new DiffGenerator("https://github.com/vbasto-iscte/ESII1920");
  ArrayList<Ref> tagList = (ArrayList<Ref>) dg.getTags();
  AbstractTreeIterator treeIterator = DiffGenerator.prepareTreeParser(dg.getRepo(),tagList.get(0).getObjectId().getName());
  assertThrows(IllegalStateException.class, () -> dg.getFileText("test", treeIterator));
 }
 @Test
 public void testGetTags(){
  DiffGenerator dg = new DiffGenerator("https://github.com/vbasto-iscte/ESII1920");
  ArrayList<Ref> tagList = (ArrayList<Ref>) dg.getTags();
  assertEquals(3, tagList.size());
 }
 @Test
 public void testDiffText(){
  DiffGenerator dg = new DiffGenerator("https://github.com/vbasto-iscte/ESII1920");
  ArrayList<Ref> tagList = (ArrayList<Ref>) dg.getTags();
  AbstractTreeIterator oldTreeParser = DiffGenerator.prepareTreeParser(dg.getRepo(), tagList.get(2).getObjectId().getName());
  AbstractTreeIterator newTreeParser = DiffGenerator.prepareTreeParser(dg.getRepo(), tagList.get(1).getObjectId().getName());
  assertNotNull(dg.getDiffText(oldTreeParser,newTreeParser,"covid19spreading.rdf"));
 }
 @Test 
 public void testFormattedTests(){
  DiffGenerator dg = new DiffGenerator("https://github.com/vbasto-iscte/ESII1920");

  String fileName = "covid19spreading.rdf";
  ArrayList<Ref> tagList = (ArrayList<Ref>) dg.getTags();
  List<String> oldLinesFromString;
  List<String> newLinesFromString;
  AbstractTreeIterator oldTreeParser = DiffGenerator.prepareTreeParser(dg.getRepo(), tagList.get(2).getObjectId().getName());
  AbstractTreeIterator newTreeParser = DiffGenerator.prepareTreeParser(dg.getRepo(), tagList.get(1).getObjectId().getName());

  oldLinesFromString = dg.getFileText(fileName, oldTreeParser).lines().collect(Collectors.toList());
  newLinesFromString = dg.getFileText(fileName, newTreeParser).lines().collect(Collectors.toList());
  List<String> diffStringStream = dg.getDiffText(oldTreeParser, newTreeParser, fileName);
  assertDoesNotThrow(() -> dg.generateFormattedTexts(diffStringStream, oldLinesFromString, newLinesFromString));
    
 }
 @Test
 public void testcalculateDiff(){
  DiffGenerator diffGenerator = new DiffGenerator("https://github.com/vbasto-iscte/ESII1920");
  ArrayList<Ref> tagList = (ArrayList<Ref>) diffGenerator.getTags();
  List<List<String>> generatedDiffStrings = diffGenerator.calculateDiff(tagList, "covid19spreading.rdf");
  assertNotNull(generatedDiffStrings);
 }
}
