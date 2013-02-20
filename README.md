#LolClient
A League of Legends client built with Java

_Copyright (C) 2013 fjxokt_

##Build Instructions/Information

The project uses Maven, and follows the Maven standards regarding layout and dependencies. If you are not familiar with Maven, please check out [this helpful guide](http://maven.apache.org/guides/getting-started/index.html).

If you are unfamiliar with Maven, please read [Maven in 5 minutes](http://maven.apache.org/guides/getting-started/maven-in-five-minutes.html), which will give you a basic understanding of how to use Maven from the command line.

Special things to note:

* The project will build a release assembly (by default a zip file) containing all dependencies needed to run the application if you enable the "release" profile. You can either do this by passing "-P release" on the command line, or by editing the POM file to have the "release" profile enabled by default. If you wish to read more about enabling the release profile, please read the [Introduction to Build Profiles](http://maven.apache.org/guides/introduction/introduction-to-profiles.html). The release assembly generated will gather it's runtime files (files the program requires to be on the path, like images and sound files) from "/src/main/runtime/resources/assets".

* Configure your IDE to point to "/src/test/runtime/resources/assets" as the running directory if you plan on using an IDE run feature. _Do not use "/src/main/runtime/resources/assets" to run your test builds from!_ Doing this may contaminate the release artifacts. If you wish to work with the "/src/main/runtime/resources/assets" directly, you must remember never to commit those changes that the test build inadvertently causes.

* There are two Jars created by the main build, a "shaded" Jar with all dependencies already included within the Jar itself and a Jar that contains only the actual contents of the project's build. The shaded JAR will be named by the property "project.shadedArtifact.finalName" whereas the un-shaded Jar will be named "lolClient-[version]" based upon the version of the program. Both are valid executable Jars, however you will have to manually place the un-shaded Jar alongside the libraries the program requires if you wish to use it instead of the more automatic shaded Jar. If your IDE gives you a warning about the project being processed through the shade plugin, it is almost definitely safe to ignore.

How to setup Maven with common IDEs/Command Line:

* [Eclipse](http://maven.apache.org/eclipse-plugin.html)

* [Netbeans](http://wiki.netbeans.org/MavenBestPractices)

* [ItelliJ](http://www.jetbrains.com/idea/webhelp/maven-2.html)

* [Maven for the command line](http://maven.apache.org/download.cgi#Installation)

