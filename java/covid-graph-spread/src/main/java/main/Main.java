package main;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import covid_graph_spread.CreateHTMLTable;
import covid_graph_spread.ReadFileFromRepository;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		ReadFileFromRepository rd = new ReadFileFromRepository();
		List<List<String>> partialList;
		try {
			partialList = rd.createRepository();
			CreateHTMLTable ct = new CreateHTMLTable();
			ct.getHtml(partialList.get(0), partialList.get(1), partialList.get(2), partialList.get(3), partialList.get(4));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
