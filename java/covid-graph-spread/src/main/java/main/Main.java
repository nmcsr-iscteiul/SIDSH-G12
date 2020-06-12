package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.api.errors.InvalidRemoteException;
import org.eclipse.jgit.api.errors.TransportException;

import covid_graph_spread.CreateHTMLTable;
import covid_graph_spread.ReadFileFromRepository;

public class Main {

	public static void main(String[] args) throws InvalidRemoteException, TransportException, GitAPIException {
		ReadFileFromRepository rd = new ReadFileFromRepository();
		List<List<String>> htmlTableContentsList;
		try {
			htmlTableContentsList = rd.createRepository();
			CreateHTMLTable ct = new CreateHTMLTable();
			ct.getHtml(htmlTableContentsList.get(0), htmlTableContentsList.get(1), htmlTableContentsList.get(2), htmlTableContentsList.get(3), htmlTableContentsList.get(4));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
