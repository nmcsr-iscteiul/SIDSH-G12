package covid_query.covid_query;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.NoFilepatternException;
import org.eclipse.jgit.api.errors.TransportException;
import org.eclipse.jgit.lib.Repository;
import org.eclipse.jgit.revwalk.RevCommit;
import org.eclipse.jgit.storage.file.FileRepositoryBuilder;


/**
 * Hello world!
 *
 */



public class App 
{
    public static void main( String[] args ) throws IOException, NoFilepatternException, GitAPIException
    {
    	String query = args[0];
    	System.out.println("QUERY:"+ query);
    	///RDF/NamedIndividual/@*/
    	
   // 	String query = "/RDF/NamedIndividual/@*";
    //	String query = "//*[contains(@about,'Algarve')]/Infecoes/text()";
        System.out.println( "Hello World!" );
        App a = new App();
        a.getRDFFile();
        XmlProject xml = new XmlProject(query);
        xml.run();
       // XmlProject.main("/RDF/NamedIndividual/@*");
        xml.generateHTML();
        
    }
    
  
    
    //APAGAR PASTA REPCOPY NO BASH FILE  
    public void getRDFFile() throws InvalidRemoteException, TransportException, GitAPIException{
    	
    	Git repo = Git.cloneRepository()
    	          .setURI("https://github.com/vbasto-iscte/ESII1920")
    	          .setDirectory(new File("repCopy/."))
    	          .setBranchesToClone(Arrays.asList("refs/heads/master"))
    	          .setCloneAllBranches(false)
    	          .setCloneSubmodules(true)
    	          .setNoCheckout(true)
    	          .call();

    	         repo.checkout().setStartPoint("origin/master").addPath("covid19spreading.rdf").call();
    }
    
    public void getHtml(List<String> title2, List<String> journal2, List<String> year2, List<String> authorToS2, List<String> filep2) throws IOException{
        List<String> list = new ArrayList<String>();
        String header = Files.readString(Paths.get("HTML/header.html"));
        String documentPre = "<html><style> table, th, td { border: 1px solid black; </style> </head> <body>";
        list.add(header);
        list.add(documentPre);
        String headerColumn = "<table style=\"background-color: rgba(0, 0, 0, 0.6); color: white\"> <tr> <th>Article title</th> <th>Journal name</th> <th>Publication year</th> <th>Authors</th> </tr>";
        list.add(headerColumn);
        for(int i=0; i < title2.size(); i++) {
        String htmlColumn = "<tr>"+ "<td><a href="+ "Covid%20Scientific%20Discoveries%20Repository/" + filep2.get(i)+">"+ title2.get(i)+"</a></td> <td>"+journal2.get(i)+"<td>"+year2.get(i)+"<td>"+authorToS2.get(i)+"</td>";
        list.add(htmlColumn);
        }
        String documentPost = " </table></body></html>";
        list.add(documentPost );
        String footer = Files.readString(Paths.get("HTML/footer.html"));
        list.add(footer);
        File f = new File("HTML/data.html");
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(f))) {

            for (String html : list) {
                bw.write(html);
            }

        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    
    
}
