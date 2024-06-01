# Introduction / Learning Goals
This project was created by Devin Lo, in order to teach the following concepts in EECS 3311:
- Using Git
- .gitignore
- Using Maven
- What is JSON?
- Markdown files
- JUnit 5 ParameterizedTest (optional)
- Log4j2 (optional)

All knowledge from prerequisite courses such as EECS 1022, EECS 2030 and EECS 2031 is assumed.

## Using Git
Git is a popular version control system that is used by developers to collboratively work on code projects together.

Our in-depth reading material on Git is [here](https://docs.google.com/document/d/12TmFKePVelHxftiEZ_8il80g4cj-IeXMhKWBEY1GBWU/edit?usp=sharing).

## .gitignore
This file is used to tell Git to ignore certain files and directories from its scan, so those files or directories (and by extension anything inside those directories) won't be picked up by Git as having been added, modified, or deleted. Note: .gitignore does NOT work on files that are already being tracked by Git.

You can read more on how to use the file (including the syntax for adding entries) [here](https://git-scm.com/docs/gitignore).

When you initialize a repository through GitHub, you have the option of adding a .gitignore file automatically based on the templates that are provided [here](https://github.com/github/gitignore).

## Using Maven
Maven is a popular build automation tool primarily used for Java projects. From our original Lab 0 handout, Maven can manage a project’s build, reporting and documentation from a central piece of information.

One way to think about Maven is that it is like your phone's app store, where you can pick and choose dependencies (like your phone's apps) to use on your project. - credit to [Victor Lee](https://github.com/victorlee0505) (YorkU CS Class of 2020) for this analogy.

Detailed information of the structure of pom.xml can be found [here](https://maven.apache.org/pom.html)

In your IDE of choice, you may need to download some plugin or extension to enable Maven support in your project. Here's some instructions for some popular IDEs:
- Eclipse: https://github.com/eclipse-m2e/m2e-core#-installation
- VS Code: https://code.visualstudio.com/docs/java/java-build

### Breakdown of this project's POM file
We have the following elements:
- typical XML version declaration
- typical Apache Maven project declaration and model version
- groupId: usually, this identifies the organization that published the project
- artifactId: the specific identifier of this project
- version: this tells users which version of the artifact that they are looking at. Since a project could have many different releases, the version will be updated as you continue to make changes.
- properties: Inside here we have the source and targeting versions of Java defined as 1.8
- dependencies: other projects that our project will depend on, such as JUnit 5. You can also define the scope, ex. the JUnit dependency is only applicable to the test scope. Info on scopes [here](https://maven.apache.org/guides/introduction/introduction-to-dependency-mechanism.html#dependency-scope).
- build plugins: from the [Maven website](https://maven.apache.org/guides/introduction/introduction-to-plugins.html), plugins are the central feature of Maven that allow for the reuse of common build logic across multiple projects. In the context of this project, we have to bring in the Maven Surefire plugin to be able to run unit tests using Maven.

This is a popular website for finding Maven dependencies: https://mvnrepository.com/

### Maven Goals and Phases
Maven goals are specific commands that you tell Maven to execute to get some end result. Phases are mapped to underlying goals.

Some common examples are:
- `mvn clean` This command is used to clear all of your generated classes
- `mvn compile` This command compiles the project.
- `mvn test` This command runs the tests that you've defined in your code, using a suitable testing framework (usually you have defined the dependencies for your testing framework in your pom.xml)
- `mvn exec:java` This command finds the class with the `main` method to execute your project.
- `mvn package` This command packages your project into a file for distribution (usually JAR or WAR depending on what you specify in your pom.xml), which then could be used by other projects.
- `mvn install` This command packages your project into a JAR or WAR file, AND installs it to your Maven repository on your local computer, thus it is ready for use as a dependency defined in another Maven project on your computer.
- `mvn deploy` This command allows you to deploy your packaged project to some remote repository or artifacts feed.

You can combine some goals/phases, ex. `mvn clean compile exec:java` will clean the generated classes, then re-compile the project, then finally execute your Java project.

There are many commands, and some are also specific to what dependencies that you have specified in your project. For example, the Spring Boot dependency adds a specific goal such as `mvn spring-boot:run`

There are some useful flags too, such as:
- `-U` flag which forces an update of all of your dependencies (as long as they are SNAPSHOT versions and not release versions)
- `-DskipTests` which allows you to skip the unit tests that are run as part of `mvn package`

You can read more about goals [here](https://maven.apache.org/guides/introduction/introduction-to-the-lifecycle.html).

## What is JSON? And how can I make use of it in Java using Maven?
You can watch my YouTube video linked [here](https://www.youtube.com/watch?v=nVneIOMxw2A).

The unit tests show some usage of JSONObject and JSONArray, and assume that you have watched the video linked above.

## Markdown Files
Markdown files are popularly used in repositories to create help files, such as this README.md file.

There are plenty of resources available on this topic, so I won't dive into the syntax, but I just wanted to let you know why the README of this project is written in this particular format.

Here is a great resource for Markdown: https://www.markdownguide.org/

## JUnit 5 ParameterizedTest (optional)
**Note: we won't be using JUnit 5 in EECS 3311. The version you may use in the assignments and project is JUnit 4.** This is just here for exposure, so that you have this knowledge when you go into the industry.

JUnit 5 introduced a new way to allow you to pass parameters into your test methods, which may help to cut down on repetitive code in a test class.

The two test classes have some example uses of ParameterizedTest and also another JUnit 5 feature, RepeatedTest.

However, there is a scenario that can arise that you need to be careful of (full credit to my colleague at the Ontario Ministry of Transportation for pointing this out):
- Let's say you have a ParameterizedTest with some number of sets of parameters.
- One day, the 5th parameter set fails the test.
- The developer who is debugging to run this particular parameter set would have to do one of the following:
    - patiently wait for all the other cases to also run
    - write an extra test method to test ONLY that case
    - comment out the other sets of parameters in order to re-test that specific scenario only
- With the first two, it's just a matter of inconvenience
- With the last thing, if the developer is careless and forgets to undo their changes to the test case before checking in their code, the change could end up in the repository, and therefore all the other parameter sets would mistakenly go untested.

Regardless, it's still very advantageous to use ParameterizedTest if you have a large number of static parameters, say, 100 of them. Just be aware of the scenario above and do your debugging work carefully!!

## Log4j2 (optional)
The logging material is optional to learn, but I included it to expose you to this, as you will probably encounter it in the industry.

Log4j2 is a popular logging framework used with Java projects. It can output to your console and/or an output file, with formatted messages that include the time, the line of code triggering the log, etc.

Frankly, I am not an expert with configuring Log4j2, but I have included the following:
- log4j2.xml config file which goes in src/main/resources
- use of Logger in my Java classes (instantiation and calling with different levels)

# How to use this project
This project has been created in a manner such that it demonstrates all of the concepts listed above.
- **Git**: we ask you to clone the project to your local computer in order to make use of the Maven commands.
- **.gitignore**: sample shown that combines the GitHub provided template with a template that we used at the Ontario Ministry of Transportation
- **Maven**: the pom.xml defines the minimum Java version expected (1.8), and lists all the dependencies, such as the dependency for org.json
- **JSON**: we have some sample JSON files that will be read by the project during the unit test. We read the file into a JSONObject, which we can add, modify, or remove elements from.
- **Markdown**: you're reading a file formatted in Markdown right now!
- **ParameterizedTest**: we use different parameters to perform the same testing on different cases. Various different types of argument sources are demonstrated.
- **Log4j2**: when you run the app, there will be automated logging messages in your console.

Here are the instructions of how to interact with this project in your command line:
1. Try to clone the project using Git in your command line using `git clone`. You can get the URL of this project by clicking the Code button at the top right of the main repository page.
1. Once you have cloned the project to your computer and have used the `cd` command to change to the project directory, try the command `mvn clean compile` and see the output. There should be no build errors.
1. Try the command `mvn exec:java` and see what is output into your terminal by the logger.
1. Go to `logs/labzero.log` and verify that the same output in your terminal was also written to the log file.
1. Try the command `mvn exec:java -Dexec.mainClass="ca.yorku.eecs.softwaredesign.JSONDemo" -Dexec.args="whatever.json"` which demonstrates how we can pass command-line arguments in a Java execution using Maven.
1. Try the command `mvn test` to try to execute the JUnit tests and see how the ParameterizedTests results output, sometimes with additional logging messages shown.

# I want to learn more on my own time! Do you have any other resources I can browse?
Personally, no, but my friend [Victor Lee](https://github.com/victorlee0505) (YorkU CS Class of 2020) made a demo app that teaches you about the Spring Boot framework (one of the popular ways of making web applications in Java these days).

https://github.com/victorlee0505/SpringReactiveDemo/tree/develop

And I mean, [Dr. Jackie Wang's YouTube channel](https://www.youtube.com/@jackiechenweiwang/playlists) exists...

A few more things I'd suggest you look into:
- the use of Optional in Java
- the use of functional programming in Java using java.util.function.Function