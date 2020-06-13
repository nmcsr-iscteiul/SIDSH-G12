package tests;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;


@RunWith(JUnitPlatform.class)
@SelectClasses({CreateHtmlPageTest.class,FormatFileToListTest.class,ReadFileFromRepositoryTest.class})
public class AllTests {

}
