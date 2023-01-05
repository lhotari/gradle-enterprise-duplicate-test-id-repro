# Reproduce "Received a start event with duplicate id" issue with Gradle Enterprise

The problem reproduces with maven-surefire-plugin versions >= 2.15 . The test passes
when maven-surefire-plugin version is <= 2.14 .

Example build scan: https://scans.gradle.com/s/skpmqhfs446og/failure#1

Error message
```
There was an error in the forked process	
Received a start event for 'DefaultTestDescriptor{id=3164034040858250684, surefireForkId=1, name='org.example.DuplicateTestIdIssueReproTest', className='org.example.DuplicateTestIdIssueReproTest', parentId=3213794952218547303}' with duplicate id '3164034040858250684'.	
org.apache.maven.surefire.booter.SurefireBooterForkException: There was an error in the forked process	
Received a start event for 'DefaultTestDescriptor{id=3164034040858250684, surefireForkId=1, name='org.example.DuplicateTestIdIssueReproTest', className='org.example.DuplicateTestIdIssueReproTest', parentId=3213794952218547303}' with duplicate id '3164034040858250684'.	
	at org.apache.maven.plugin.surefire.booterclient.ForkStarter.fork(ForkStarter.java:657)	
	at org.apache.maven.plugin.surefire.booterclient.ForkStarter.run(ForkStarter.java:283)	
	at org.apache.maven.plugin.surefire.booterclient.ForkStarter.run(ForkStarter.java:246)	
	at org.apache.maven.plugin.surefire.AbstractSurefireMojo.executeProvider(AbstractSurefireMojo.java:1161)	
	at org.apache.maven.plugin.surefire.AbstractSurefireMojo.executeAfterPreconditionsChecked(AbstractSurefireMojo.java:1002)	
	at org.apache.maven.plugin.surefire.AbstractSurefireMojo.execute(AbstractSurefireMojo.java:848)	
	at com.gradle.maven.cache.extension.d.c.a(SourceFile:26)	
	at com.gradle.maven.cache.extension.d.n.a(SourceFile:23)	
	at com.gradle.maven.cache.extension.d.h.a(SourceFile:28)	
	at com.gradle.maven.cache.extension.d.m.a(SourceFile:27)	
	at com.gradle.maven.cache.extension.d.a.c(SourceFile:115)	
	at com.gradle.maven.cache.extension.d.a.a(SourceFile:61)	
	at com.gradle.maven.cache.extension.d.e.a(SourceFile:27)	
	at com.gradle.maven.cache.extension.d.l.a(SourceFile:17)	
	at com.gradle.maven.cache.extension.d.d.a(SourceFile:42)	
	at com.gradle.maven.cache.extension.d.b.a(SourceFile:26)	
	at com.gradle.maven.cache.extension.d.f$1.run(SourceFile:35)	
	at org.gradle.internal.operations.DefaultBuildOperationRunner$1.execute(SourceFile:29)	
	at org.gradle.internal.operations.DefaultBuildOperationRunner$1.execute(SourceFile:26)	
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(SourceFile:66)	
	at org.gradle.internal.operations.DefaultBuildOperationRunner$2.execute(SourceFile:59)	
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(SourceFile:157)	
	at org.gradle.internal.operations.DefaultBuildOperationRunner.execute(SourceFile:59)	
	at org.gradle.internal.operations.DefaultBuildOperationRunner.run(SourceFile:47)	
	at com.gradle.maven.cache.extension.d.f.a(SourceFile:31)	
	at com.gradle.maven.cache.extension.d.k.a(SourceFile:61)	
	at com.gradle.maven.cache.extension.h.b.lambda$createProxy$0(SourceFile:64)	
	at jdk.proxy7/jdk.proxy7.$Proxy72.execute(Unknown Source)	
	at com.gradle.maven.scan.extension.internal.e.a.executeMojo(SourceFile:114)	
	at org.apache.maven.lifecycle.internal.MojoExecutor.doExecute2(MojoExecutor.java:370)	
	at org.apache.maven.lifecycle.internal.MojoExecutor.doExecute(MojoExecutor.java:351)	
	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:215)	
	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:171)	
	at org.apache.maven.lifecycle.internal.MojoExecutor.execute(MojoExecutor.java:163)	
	at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:117)	
	at org.apache.maven.lifecycle.internal.LifecycleModuleBuilder.buildProject(LifecycleModuleBuilder.java:81)	
	at org.apache.maven.lifecycle.internal.builder.singlethreaded.SingleThreadedBuilder.build(SingleThreadedBuilder.java:56)	
	at org.apache.maven.lifecycle.internal.LifecycleStarter.execute(LifecycleStarter.java:128)	
	at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:298)	
	at org.apache.maven.DefaultMaven.doExecute(DefaultMaven.java:192)	
	at org.apache.maven.DefaultMaven.execute(DefaultMaven.java:105)	
	at org.apache.maven.cli.MavenCli.execute(MavenCli.java:960)	
	at org.apache.maven.cli.MavenCli.doMain(MavenCli.java:293)	
	at org.apache.maven.cli.MavenCli.main(MavenCli.java:196)	
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke0(Native Method)	
	at java.base/jdk.internal.reflect.NativeMethodAccessorImpl.invoke(NativeMethodAccessorImpl.java:77)	
	at java.base/jdk.internal.reflect.DelegatingMethodAccessorImpl.invoke(DelegatingMethodAccessorImpl.java:43)	
	at java.base/java.lang.reflect.Method.invoke(Method.java:568)	
	at org.codehaus.plexus.classworlds.launcher.Launcher.launchEnhanced(Launcher.java:282)	
	at org.codehaus.plexus.classworlds.launcher.Launcher.launch(Launcher.java:225)	
	at org.codehaus.plexus.classworlds.launcher.Launcher.mainWithExitCode(Launcher.java:406)	
	at org.codehaus.plexus.classworlds.launcher.Launcher.main(Launcher.java:347)
```


The test class that reproduces the issue: [DuplicateTestIdIssueReproTest](src/test/java/org/example/DuplicateTestIdIssueReproTest.java)

It's a combination of using TestNG's @Test annotation on the class level and using @Factory method on the constructor. In addition, it's necessary to have a public non-annotated method in the test class. 

### Running

```bash
mvn test
```

### Making test pass with maven-surefire-plugin version 2.14

```bash
mvn -Dsurefire.version=2.14 test
```
