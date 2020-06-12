import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class HtmlGenerator {
    private List<String> newText;
    private List<String> oldText;
    private List<String> HtmlFormattedStrings = new ArrayList<>();

    public HtmlGenerator(List<String> oldText, List<String> newText) {
        this.newText = newText;
        this.oldText = oldText;
    }

    public void addHeader() throws IOException {
        String header = Files.readString(Paths.get("HTML/header.html"));
        String correctedHeader = header.replaceAll("Scientific Discoveries", "Evolution Diff");
        String documentPre = "<html><style> table, th, td { border: 1px solid black;} td {padding-left : 5px;} </style> </head> <body>";
        HtmlFormattedStrings.add(correctedHeader);
        HtmlFormattedStrings.add(documentPre);

    }

    public void htmlFormatter() {
        try {
            addHeader();
            HtmlFormattedStrings.add("<table>\n<tr><th>Old File</th><th>New File</th></tr>");
            HtmlFormattedStrings.add("<tr><td>");
            for(String s: oldText){
                HtmlFormattedStrings.add(addHtmlTags(s));
            }
            HtmlFormattedStrings.add("</td>\n<td>");
            for(String s : newText){
                HtmlFormattedStrings.add(addHtmlTags(s));
            }
            HtmlFormattedStrings.add("</td>\n</tr>");
            HtmlFormattedStrings.add("</table>");
            addFooter();
            for(String s: HtmlFormattedStrings)
            System.out.println(s);
            generateHtmlFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private void addFooter() throws IOException {
        String footer = Files.readString(Paths.get("HTML/footer.html"));
        HtmlFormattedStrings.add(footer);
    }

    private String addHtmlTags(String line) {
        if(line.contains("<")){
            line = line.replace("<" ,"< ");
        }
        if(line.contains("+++")){
            line = "<span style=\"color:green\">" + line + "</span>";
        }
        if(line.contains("---")){
            line = "<span style=\"color:red\">" + line + "</span>";
        }
        if (line.length() > 2){
            line = line + "<br>";
        }
        return line;
    }

    private void generateHtmlFile() throws IOException {
        File f = new File("HTML/covid-evolution-diff.html");
        BufferedWriter bw = new BufferedWriter(new FileWriter(f),20000);

        for (String html : HtmlFormattedStrings) {
            bw.write(html);
        }
        bw.close();
    }


}
