#Pre Requirements

Download and install **git** from [here](https://git-scm.com).

Download and install **maven** from [here](https://downloads.apache.org/maven/maven-3/3.6.3/source/apache-maven-3.6.3-src.zip).

add **maven** to your envirement variables like this:

#Installation

Run **git bash**:

execute this commad in git bash:

- **git clone https://github.com/raniaderouiche/municipalite-project.git**

Access the new cloned repository **municipalite** qnd execute this command to install project dependencies:
- **mvn install**

#Running The Project

Execute to compile project:
- **mvn compile**

Execute to run project:
- **mvn exec:java -Dexec.mainClass=org.fsb.municipalite.App**
