# Reproduce "Received a start event with duplicate id" issue with Gradle Enterprise

The problem reproduces with maven-surefire-plugin versions >= 2.15 . The test passes
when maven-surefire-plugin version is <= 2.14 .

Example build scan: https://scans.gradle.com/s/skpmqhfs446og/failure#1

The test class that reproduces the issue: [DuplicateTestIdIssueReproTest](src/test/java/org/example/DuplicateTestIdIssueReproTest.java)

It's a combination of using TestNG's @Test annotation on the class level and using @Factory method on the constructor. In addition, it's necessary to have a public non-annotated method in the test class. 

### Running

```bash
mvn test
```

### Making test pass

```bash
mvn -Dsurefire.version=2.14 test
```
