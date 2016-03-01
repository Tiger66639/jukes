# Introduction #

This document is how to build Jukes with Maven2, Ant, and Launch4J.


# Details #

  1. Download and install [Maven 2.2.1](http://maven.apache.org/download.html) or higher.
  1. Download and install [Apache Ant 1.8](http://ant.apache.org/) or higher (needed for Launch4J)
  1. Install JDK 1.6 or higher
  1. Create ANT\_HOME, JAVA\_HOME, M2\_HOME and MAVEN\_OPTS like the screenshot below:
  1. Unzip [Jukes Dependencies Zip](http://code.google.com/p/jukes/downloads/detail?name=jukes-dependencies.zip) to the location C:\Users\USER\.m2\repository  These are dependencies needed by Jukes not in the Maven Central Repo
  1. From the root directory of the project where the pom.xml is run the command: **_mvn clean package_**  This will build the whole project including the Windows and Unix deployments.  If you have any errors check the steps above for any missed steps.
  1. Configure Eclipse: Run this command **_mvn eclipse:configure-workspace -Declipse.workspace=jukes_**
  1. Create Eclipse Project by running **_mvn eclipse:eclipse_** which will create the .classpath and .project files for you.  Then in Eclipse do File->Import->General->Existing Project Into Workspace

![http://melloware.biz/images/envvariables.png](http://melloware.biz/images/envvariables.png)