package suites;

import org.junit.runner.RunWith;
import org.junit.runners.Suite;
import tests.ArticleTests;
import tests.GetStartedTest;
import tests.MyListTest;
import tests.SearchTest;

@RunWith(Suite.class)
@Suite.SuiteClasses({
        ArticleTests.class,
        GetStartedTest.class,
        MyListTest.class,
        SearchTest.class
})
public class testSuite {
}
