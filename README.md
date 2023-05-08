# Readme: Exchange API Application
A RESTful service which collect exchange rates data from resources and return it by endpoints.

## Requirements

The fully fledged server uses the following:

* Spring Framework
* SpringBoot
* Docker

## Dependencies
There are a number of third-party dependencies used in the project. Browse the Gradle build.gradle file for details of libraries and versions used.

## Building the project
You will need:

*	Java JDK 17
*	Gradle 8
*	Git

Clone the project and use Gradle to build the server

	$ gradle clean build
	$ ./gradlew build

Build and run Docker containers

    $ docker-compose build
    $ docker-compose up -d
	
### Browser URL
Open your browser at the following URL for Swagger UI (giving REST interface details):


http://localhost:8080/swagger-ui/index.html or just click [here](http://localhost:8080/swagger-ui/index.html)	