import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * Generates the html file that will be served by the WP-CMS. It shows two files
 * and the difference between them (git diff)
 *
 * @author Bernardo Sequeira (bernardosequeir)
 *
 */
public class HtmlGenerator {
    private final List<String> newText;
    private final List<String> oldText;
    private final List<String> HtmlFormattedStrings = new ArrayList<>();

    /**
     * Constructor for the class
     * 
     * @param oldText List of Strings with the contents of the "old" file of the
     *                diff
     * @param newText List of Strings with the contents of the "new" file of the
     *                diff
     */
    public HtmlGenerator(List<String> oldText, List<String> newText) {
        this.newText = newText;
        this.oldText = oldText;
    }

    /**
     * This method adds the header of the wordpress site to match the other pages of
     * the site, also creates the html table and styles it.
     * 
     * @throws IOException If the header file doesn't exist.
     */
    public void addHeader() {
        String header = null;
        try {
            header = Files.readString(Paths.get("HTML/header.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        String correctedHeader = null;
        if (header != null) {
            correctedHeader = header.replaceAll("Scientific Discoveries", "Evolution Diff");
        }
        String documentPre = "<html><style> table, th, td { border: 1px solid black;} td {padding-left : 5px;} </style> </head> <body>";
        HtmlFormattedStrings.add(correctedHeader);
        HtmlFormattedStrings.add(documentPre);

    }

    /**
     * Combines the header, the contents of the files and the footer of the file.
     * Also runs the method that creates the html file, generateHtmlFile().
     *
     */
    public void htmlFormatter() {
        try {
            addHeader();
            HtmlFormattedStrings.add("<table>\n<tr><th>Old File</th><th>New File</th></tr>");
            HtmlFormattedStrings.add("<tr><td>");
            for (String s : oldText) {
                HtmlFormattedStrings.add(addHtmlTags(s));
            }
            HtmlFormattedStrings.add("</td>\n<td>");
            for (String s : newText) {
                HtmlFormattedStrings.add(addHtmlTags(s));
            }
            HtmlFormattedStrings.add("</td>\n</tr>");
            HtmlFormattedStrings.add("</table>");
            addFooter();
            for (String s : HtmlFormattedStrings)
                System.out.println(s);
            generateHtmlFile();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * This method adds the footer of the wordpress site to match the other pages of
     * the site, also ends the html table and the body of the html.
     * 
     * @throws IOException If the footer file doesn't exist.
     */
    public void addFooter() {
        String footer = null;
        try {
            footer = Files.readString(Paths.get("HTML/footer.html"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        HtmlFormattedStrings.add(footer);
    }

    /**
     * This method adds the html tags so the file contents can be displayed on the
     * html page. It separates the opening of tags (<) beacuse they would get parsed
     * as html comments otherwise. Also checks if the lines contain either "+++" or
     * "---" and colors those lines accordingly.
     *
     * @param line The String that contains the text line to be formatted.
     * @return The line that was provided but formatted.
     */
    public String addHtmlTags(String line) {
        if (line.contains("<")) {
            line = line.replace("<", "< ");
        }
        if (line.contains("+++")) {
            line = "<span style=\"color:green\">" + line + "</span>";
        }
        if (line.contains("---")) {
            line = "<span style=\"color:red\">" + line + "</span>";
        }
        if (line.length() > 2) {
            line = line + "<br>";
        }
        return line;
    }

    /**
     * Writes the contents of the html page to a file in disk.
     * 
     * @throws IOException If it's unable to create the file or if it can't write to
     *                     it.
     */
    public void generateHtmlFile() {
        File f = new File("HTML/covid-evolution-diff.html");
        BufferedWriter bw;
        try {
            bw = new BufferedWriter(new FileWriter(f));
            for (String html : HtmlFormattedStrings) {
                bw.write(html);
            }
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
        bw.close();
    }

}
