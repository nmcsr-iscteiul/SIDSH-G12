import covid_evolution_diff.HtmlGenerator;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class HtmlGeneratorTest {

    @Test
    public void testAddHeader() {
        HtmlGenerator hg = new HtmlGenerator(null, null);
        assertDoesNotThrow(hg::addHeader);
    }

    @Test
    public void testAddFooter() {
        HtmlGenerator hg = new HtmlGenerator(null, null);
        assertDoesNotThrow(hg::addFooter);
    }

    @Test
    public void testAddHtmlTags() {
        HtmlGenerator hg = new HtmlGenerator(null, null);
        assertEquals("test<br>", hg.addHtmlTags("test"));
    }

    @Test
    public void testNullHtmlFormatter() {
        HtmlGenerator hg = new HtmlGenerator(null, null);
        assertThrows(NullPointerException.class,hg::htmlFormatter);
    }
    @Test
    public void testHtmlFormatter() {
        List<String> oldText = new ArrayList<>();
        oldText.add("aaaa");
        oldText.add("---bbbb");
        oldText.add("");
        List<String> newText = new ArrayList<>();
        newText.add("aaaa");
        newText.add("+++cccc");
        newText.add("");
        HtmlGenerator hg = new HtmlGenerator(oldText, newText);
        assertDoesNotThrow(hg::htmlFormatter);
    }
}
