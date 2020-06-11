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
			List<String> list4 = new ArrayList<String>();
			list4.add(" 1" );
			list4.add(" 2" );
			list4.add(" 3" );
			ct.getHtml(partialList.get(0), partialList.get(1), partialList.get(2), partialList.get(3), list4);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
