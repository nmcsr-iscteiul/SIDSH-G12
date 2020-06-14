package covid_query.covid_query;

import org.junit.platform.runner.JUnitPlatform;
import org.junit.platform.suite.api.SelectClasses;
import org.junit.runner.RunWith;
import org.junit.runners.Suite.SuiteClasses;

@RunWith(JUnitPlatform.class)
@SelectClasses({TestApp.class, QueryFixerTest.class, XmlProjectTest.class})
public class AllTests {

}