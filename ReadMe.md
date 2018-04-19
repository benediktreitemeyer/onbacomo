# Building and installation of OnbaCoMo

## Building
1. Download the [Java-JDK Verison 9.0.4](http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html).
2. If you get your JDK from another source, add your JDK directory to the **PATH** Windows environment variable.
3. Install Maven.
    1. Make sure JDK is installed, and **JAVA_HOME** variable is added as Windows environment variable.
    2. Visit [Maven official website](https://maven.apache.org/download.cgi#), download the Maven zip file, for example : apache-maven-3.2.2-bin.zip. Unzip it to the folder where you want to install Maven.
    3. Add both **M2_HOME** and **MAVEN_HOME** variables in the Windows environment, and point it to your Maven folder.
    4. Update **PATH** variable, append Maven bin folder – **%M2_HOME%\bin**, so that you can run the Maven’s command everywhere.
    5. Done, to verify it, run `mvn –version` in the command prompt.
4. [Download the maven-project](https://github.com/benediktreitemeyer/onbacomo) from GitHub.
5. Open the console and go to your project folder. Type `mvn clean install` to generate the plugin-JAR into your target folder.
6. Download and install Protege from the [official website](https://protege.stanford.edu/products.php#desktop-protege).

## Installation
- Copy the generated JAR-file from the target folder of your maven project into your protege plugins folder.
- On the menu-bar go to Window->Tabs and enable the OnbaCoMo-tab.
