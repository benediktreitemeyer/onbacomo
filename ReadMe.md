# Building and installation of OnbaCoMo

## Building
1. Download the Java-JDK in Verison 9.0.4 (http://www.oracle.com/technetwork/java/javase/downloads/jdk9-downloads-3848520.html)
2. Add your JDK to the windows **path-variable**
3. Install Maven in Version 3.5.2 (https://maven.apache.org/download.cgi#)
4. Download the maven-project from GitHub (https://github.com/benediktreitemeyer/onbacomo)
5. Open the console and got to your project folder. Type `mvn clean install` to generate the plugin-JAR into your target folder

## Installation
- Copy the generated JAR-file from the target folder of your maven project into your protege plugin folder
- On the menu-bar go to Window->Tabs and enable the OnbaCoMo-tab
