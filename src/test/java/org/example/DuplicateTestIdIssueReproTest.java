package org.example;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Factory;
import org.testng.annotations.Test;

/**
 * This is a dummy test class to trigger a "Received a start event with duplicate id" issue with Gradle Enterprise
 * when using maven-surefire-plugin version >= 2.15 and a combination of TestNG @Test annotation on the class,
 * the test method, and having an unannotated method and using @Factory annotation.
 */
// this annotation triggers the issue
@Test
public class DuplicateTestIdIssueReproTest {
    @DataProvider(name = "mode")
    public static Object[][] mode() {
        return new Object[][]{
                {true},
                {false}
        };
    }

    private final boolean mode;

    // the factor annotation is also required to trigger the issue
    @Factory(dataProvider = "mode")
    public DuplicateTestIdIssueReproTest(boolean mode) {
        this.mode = mode;
    }

    @Test
    public void testSomething() {
        // always passes
    }

    // this method triggers the issue
    public void thisMethodTriggersTheIssue() throws Exception {

    }
}
