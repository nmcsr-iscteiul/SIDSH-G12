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

/** Cria uma instancia App que vai aceder ao repositorio indicado e retirar o ficheiro rdf, gerando um html com o resultado da query
 * 
 * @author Tomas Godinho
 *
 */
public class App 
{
	/**Atributo que guarda a query que da entrada na App
	 * 
	 */
	private String query;
	/**Flag que vira true apos ser retirado o ficheiro rdf do repositorio
	 * 
	 */
	private boolean fileFlag = false;
	/**Recebe a string query como argumento, retira o ficheiro rdf mais recente do repositorio, cria uma instancia XmlProject que ira tratar a query e criar um ficheiro html
	 * 
	 * @param args
	 * @throws IOException
	 * @throws NoFilepatternException
	 * @throws GitAPIException
	 */
    public static void main( String[] args ) throws IOException, NoFilepatternException, GitAPIException
    {
    	String query = args[0];
    	QueryFixer fix = new QueryFixer(query);
    	query = fix.getQuery();
        System.out.println( "Iniciando covid-query.." );
        App a = new App();
        a.setQuery(query);
        a.getRDFFile();
        XmlProject xml = new XmlProject(a.query);
        xml.run();
        xml.generateHTML();
    }
  
    /**acede ao repositorio indicado e copia o ficheiro covid19spreading.rdf na pasta repCopy, garantindo a versao mais recente ao usar o master branch. 
     * 
     * @throws InvalidRemoteException
     * @throws TransportException
     * @throws GitAPIException
     */
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
    	         fileFlag = true;
    }
/**Metodo para ler a Query
 * 
 * @return devolve query que deu entrada na App
 */
	public String getQuery() {
		return query;
	}
	
/**
 * 
 * @param query String que ira substituir a query
 */
	public void setQuery(String query) {
	this.query = query;
}
/**Metodo que devolve o estado da fileFlag
 * 
 * @return Estado da fileFlag
 */
	public boolean isFileFlag() {
		return fileFlag;
	}
    
    
}