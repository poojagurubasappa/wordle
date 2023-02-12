# WORDLE CLI

An interactive command line interface to play wordle.
It vaguely simulates [Wordle](https://www.nytimes.com/games/wordle/index.html).
This game is played on the command line interface implemented in JAVA.

## Dependencies
1. The project requires [Java 17+](https://www.oracle.com/java/technologies/downloads/#java17)
2. The project uses [maven](https://maven.apache.org/) for build and dependency management
3. The project's TDD uses [JUnit 5](https://junit.org/junit5/)
4. Maven dependencies are found in pom.xml


## Usage Guide
This project can be run with maven.
The following steps are helpful:
1. To compile: `mvn clean compile`
2. To run tests: `mvn test`
3. To package: `mvn clean package`
4. To run the project: `java -jar wordle.jar`

Note: Step 4 should be preceded by Step 3. The jar file generated will be under target/ folder.
`cd target` followed by Step 4. Alternately, place the wordle.jar anywhere, goto the folder and execute Step 4. 
