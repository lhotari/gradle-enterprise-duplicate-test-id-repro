# Reproduce "Received a start event with duplicate id" issue with Gradle Enterprise

The problem reproduces with maven-surefire-plugin versions >= 2.15 . The test passes
when maven-surefire-plugin version is <= 2.14 .

Example build scan: https://scans.gradle.com/s/skpmqhfs446og/failure#1

### Running

```bash
mvn test
```

### Making test pass

```bash
mvn -Dsurefire.version=2.14 test
```